package com.example.flixster.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;
import com.example.flixster.R;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        /*holder.binding.setMovie(movie);
        holder.binding.executePendingBindings();
         */
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //final ItemMovieBinding binding;
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView tvPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            /*
            binding = ItemMovieBinding.bind(itemView);
            tvTitle = binding.tvTitle;
            tvOverview = binding.tvOverview;
            tvPoster = binding.idPoster;
            container = binding.movieContainer;
            */
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            tvPoster = itemView.findViewById(R.id.idPoster);
            container = itemView.findViewById(R.id.movieContainer);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;

            // If the phone is in landscape then return the backdropPath
            // Else return poster path
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageURL = movie.getBackdropPath();
            }
            else{
                imageURL = movie.getPosterPath();
            }
            Glide.with(context).load(imageURL).into(tvPoster);

            // Set a click listener on the whole container
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create new intent
                    Intent i = new Intent(context, DetailActivity.class);
                    // Send the movie object the user clicks to the new intent
                    // Casting it to Parcelable so that we can pass all the information regarding
                    // the movie.
                    i.putExtra("movie", Parcels.wrap(movie));
                    // Start the activity
                    context.startActivity(i, ActivityOptions.makeSceneTransitionAnimation((Activity)context).toBundle());
                }
            });
        }
    }
}
