package com.example.nikita.filmbrowser.Domain.Interactors.Details;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.UI.App;
import com.example.nikita.filmbrowser.UI.Details.MovieDetailsModel;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DetailsInteractor {

    @Inject
    public IMovieRepository movieRepository;

    public DetailsInteractor() {
        App.getComponent().inject(this);
    }

    public Observable<MovieDetailsModel> getMovie(int id) {
        return new GetMovieUseCase(movieRepository).getMovie(id);
    }
}
