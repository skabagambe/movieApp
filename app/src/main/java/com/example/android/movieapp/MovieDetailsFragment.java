package com.example.android.movieapp;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import com.example.android.movieapp.parserMovie.Movies;

public class MovieDetailsFragment extends Fragment {

    private final static String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private final static String LOGO_SIZE = "w500";
    private static final String KEY_MOVIE = "MOVIE";

    private ImageView detail_image;
    private TextView title;
    private TextView year_of_released;
    private TextView ratings;
    private TextView moviesDescription;


    public MovieDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_movie_dets,container,false);

        Movies movies=getActivity().getIntent().getParcelableExtra(KEY_MOVIE);

        title =  view.findViewById(R.id.title);
        detail_image =  view.findViewById(R.id.logo_image_view);
        year_of_released = view.findViewById(R.id.year);
        ratings =  view.findViewById(R.id.rating);
        moviesDescription =  view.findViewById(R.id.description);

        if (movies != null) {
            System.out.println(movies);
            title.setText(movies.getTitle());
            loadImage(movies.getPosterPath());
            year_of_released.setText(String.format("%.4s", movies.getReleaseDate()));
            ratings.setText(String.format("%s/10", movies.getVoteAverage()));
            moviesDescription.setText(movies.getOverview());
        }
        return view;

    }


    @TargetApi(Build.VERSION_CODES.M)
    private void loadImage(String path) {

        String urlBuilder = new StringBuilder()
                .append(BASE_POSTER_URL)
                .append(LOGO_SIZE)
                .append(path).toString();

        Picasso.with(getContext())
                .load(urlBuilder)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher)
                .into(detail_image);
    }
}


