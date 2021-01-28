package com.example.flixster.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    int movieId;
    double rating;

    // Empty constructor by the Parceler library
    public Movie(){}

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
    }

    // Create a list of movies from the JSONArray that was returned
    public static List<Movie> fromJsonArray(JSONArray moviesJsonArray) throws JSONException{
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < moviesJsonArray.length(); i++){
            movies.add(new Movie(moviesJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    // PosterPath gets a relative url so we need to get the full URL to pass to a image loading API
    // The base url is "https://image.tmdb.org/t/p" and w342 is a size available in the API
    // We then append the relative url to get the poster we are looking for
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() { return rating; }

    public int getMovieId() { return movieId; }
}
