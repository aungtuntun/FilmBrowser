package com.example.nikita.filmbrowser.Domain.Interactors.Trending;

import com.example.nikita.filmbrowser.Domain.Interactors.UpdateMovieDetailsUseCase;
import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.UI.App;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observable;


public class TrendingInteractor {
    @Inject
    public IMovieRepository movieRepository;

    private InitWMUseCase initWMUseCase;
    private GetTrendingDayUseCase getTrendingDayUseCase;

    public TrendingInteractor(){
        App.getComponent().inject(this);
        initWMUseCase = new InitWMUseCase(movieRepository);
        getTrendingDayUseCase = new GetTrendingDayUseCase(movieRepository);

    }
    public Observable<List<Movie>> getTrendingDaily(){
        return getTrendingDayUseCase.getTrendingDay()
                .doOnError(throwable -> {
                    startRequestFromDailyTrending();
                });
    }

    public void startRequestFromDailyTrending(){
        initWMUseCase.initWMUseCase();
    }

    public UUID getWMId(){
       return initWMUseCase.getWMId();
    }

    public void updateMovie(Movie movie){
        new UpdateMovieDetailsUseCase(movieRepository).updateMovie(movie);
    }

    public void wmJob(){
        new WMJobUseCase(movieRepository).wmJob();
    }
}
