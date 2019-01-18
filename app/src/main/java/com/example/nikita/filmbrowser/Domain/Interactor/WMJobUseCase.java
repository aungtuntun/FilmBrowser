package com.example.nikita.filmbrowser.Domain.Interactor;

import com.example.nikita.filmbrowser.Model.DB.Converters;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.Models.SearchModel;
import com.example.nikita.filmbrowser.Models.SearchResultModel;

import java.util.List;

public class WMJobUseCase extends BaseMoviesUseCase {

    public void wmJob() {
        SearchModel searchModel = api.getTrendingDay(API_KEY).blockingSingle();
        List<SearchResultModel> searchList = searchModel.getResults();
        for (int i = 0; i < searchList.size(); i++) {
            SearchResultModel resultModel = searchList.get(i);
            Movie movie = Converters.convertToMovie(resultModel);
            movie.setTrending(true);
            try {
                Movie movieFromDb = dao.getMovieById(movie.getId()).blockingGet();
                movie.setFavorites(movieFromDb.isFavorites());
                updateMovie(movie);
            }catch (Exception e){
                insertMovie(movie);
            }
        }
    }
}