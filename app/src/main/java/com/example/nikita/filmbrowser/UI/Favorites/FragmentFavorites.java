package com.example.nikita.filmbrowser.UI.Favorites;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nikita.filmbrowser.UI.MoviesAdapter;
import com.example.nikita.filmbrowser.R;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.UI.BaseListFragment;
import com.example.nikita.filmbrowser.UI.Search.SearchViewState;

public class FragmentFavorites extends BaseListFragment {
    private FavoritesViewModel mViewModel;
    private MoviesAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        mViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        RecyclerView rw = view.findViewById(R.id.rw);
        rw.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MoviesAdapter(getActivity(), this);
        rw.setAdapter(mAdapter);
        mViewModel.stateLiveData.observe(this, this::displayState);
        return view;
    }

    private void displayState(SearchViewState searchViewState) {
        switch (searchViewState.status) {
            case SUCCESS:
                mAdapter.setFilms(searchViewState.data);
                break;
            case ERROR:
                Toast.makeText(getActivity(), searchViewState.error, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void filmSelected(int id) {
        super.filmSelected(id);
    }

    @Override
    public void addedToFav(Movie movie) {
        mViewModel.updateMovie(movie);
        mAdapter.deleteMovie(movie);
    }

    @Override
    public void deleteFromFav(Movie movie) {
        mViewModel.updateMovie(movie);
        mAdapter.notifyDataSetChanged();
    }

}
