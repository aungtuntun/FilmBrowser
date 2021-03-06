package com.example.nikita.filmbrowser.Model.DB;

import android.arch.persistence.room.TypeConverter;

import com.example.nikita.filmbrowser.Model.Repositories.MovieRepository;
import com.example.nikita.filmbrowser.Model.Network.Models.GetDetailsMovieModel;
import com.example.nikita.filmbrowser.UI.MovieListModel;
import com.example.nikita.filmbrowser.Model.Network.Models.SearchResultModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.nikita.filmbrowser.Model.Repositories.MovieRepository.IMAGE_PATH;

public class Converters {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static MovieDetails convertToMovieDetails(GetDetailsMovieModel model) {
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setReleaseDate(model.getReleaseDate());
        movieDetails.setId(model.getId());
        movieDetails.setOverview(model.getOverview());
        movieDetails.setPosterPath(IMAGE_PATH + model.getPosterPath());
        movieDetails.setRatingAvg(model.getVoteAverage());
        movieDetails.setRevenue(model.getRevenue());
        movieDetails.setTitle(model.getTitle());
        movieDetails.setRuntime(model.getRuntime());
        model.setStatus(model.getStatus());

        ArrayList<String> genreNames = new ArrayList<>();
        for (int i = 0; i < model.getGenres().size(); i++) {
            genreNames.add(model.getGenres().get(i).getName());
        }

        ArrayList<String> countryNames = new ArrayList<>();
        for (int i = 0; i < model.getProductionCountries().size(); i++) {
            countryNames.add(model.getProductionCountries().get(i).getName());
        }
        movieDetails.setCountries(countryNames);
        movieDetails.setGenres(genreNames);
        movieDetails.setPopularity(model.getPopularity());


        return movieDetails;
    }

    public static Movie convertToMovie(SearchResultModel resultModel) {
        Movie movie = new Movie();
        if (resultModel.getTitle() == null) {
            movie.setTitle(resultModel.getName());
        } else {
            movie.setTitle(resultModel.getTitle());
        }
        movie.setPosterPath(resultModel.getPosterPath());
        movie.setId(resultModel.getId());
        movie.setRatingAvg(resultModel.getVoteAverage());
        movie.setReleaseDate(resultModel.convertReleaseDate());
        return movie;
    }

    private static MovieListModel convertToMovieListModel(Movie movie) {
        MovieListModel movieListModel = new MovieListModel();
        movieListModel.setFavorites(movie.isFavorites());
        movieListModel.setTrending(movie.isTrending());
        movieListModel.setId(movie.getId());
        movieListModel.setPosterPath(MovieRepository.IMAGE_PATH + movie.getPosterPath());
        movieListModel.setRatingAvg(Double.toString(movie.getRatingAvg()));
        movieListModel.setTitle(movie.getTitle() + movie.getReleaseDate());
        return movieListModel;
    }

    public static List<MovieListModel> convertListToMovieListModel(List<Movie> movies) {
        ArrayList<MovieListModel> list = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            list.add(convertToMovieListModel(movies.get(i)));
        }
        return list;
    }
}
