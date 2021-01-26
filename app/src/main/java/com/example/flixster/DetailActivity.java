package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flixster.Models.Movie;

import org.parceler.Parcels;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle = findViewById(R.id.titleTextView);
        tvOverview = findViewById(R.id.overviewTextView);
        ratingBar = findViewById(R.id.ratingBar);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getRating());
    }
}