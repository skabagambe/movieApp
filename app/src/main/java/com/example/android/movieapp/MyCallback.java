package com.example.android.movieapp;

import com.example.android.movieapp.parserMovie.Movies;

public interface MyCallback {
    void updateAdapter(Movies[] movies);
}
