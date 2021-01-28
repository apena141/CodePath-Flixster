package com.example.flixster;

import android.app.Activity;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;
import com.example.flixster.adapters.MovieAdapter;
//import com.example.flixster.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    private static final String MOVIEAPIKEY = BuildConfig.MOVIEAPIKEY;
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + MOVIEAPIKEY;
    public static final String TAG = "MainActivity";
    List<Movie> movies;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        RecyclerView rvMovies = binding.recyclerViewMovies;
        movies = new ArrayList<>();
        // Create an adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        // Set a layout manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // Adds vertical lines between each item
        rvMovies.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        AsyncHttpClient client = new AsyncHttpClient();

        // Movie DB API returns Json file
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                // Create the jsonObject
                JSONObject jsonObject = json.jsonObject;
                // Json object returned a value of array that correspond to the key results
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    // Initiate our list of movies by calling fromJsonArray that returns a list of movies
                    movies.addAll(Movie.fromJsonArray(results));
                    // Set the binder
                    binding.setVariable(BR.movie, movies);
                    // Notify the adapter
                    movieAdapter.notifyDataSetChanged();
                    // Checked log cat and saw our Movie list has data
                    Log.i(TAG, "Movie size: " + movies.size());
                } catch (JSONException e) {
                    // Log the error - exception thrown
                    Log.e(TAG,"Hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}