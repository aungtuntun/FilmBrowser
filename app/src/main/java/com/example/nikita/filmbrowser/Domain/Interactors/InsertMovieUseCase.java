package com.example.nikita.filmbrowser.Domain.Interactors;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.UI.MovieListModel;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InsertMovieUseCase {

    private IMovieRepository movieRepository;

    public InsertMovieUseCase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void insertMovie(Movie movie) {
        Completable.fromAction(() -> movieRepository.insertMovie(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void insertMovie(MovieListModel movie) {
        Completable.fromAction(() -> movieRepository.insertMovie(movieRepository.getMovieById(movie.getId()).blockingGet()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
