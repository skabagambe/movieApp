package com.example.android.movieapp;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.android.movieapp.parserMovie.Movies;

import org.jetbrains.annotations.Contract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMovieTask extends AsyncTask <String,Void,Movies[]>  {

    private final  MyCallback MovieTaskCallback;

   FetchMovieTask(MyCallback MovieTaskCallback){

       this.MovieTaskCallback=MovieTaskCallback;
   }
    @Contract("null -> null")
   private Movies[] getMoviesFromJsonFormat(String MovieJsonString) throws JSONException{
        final String ORIGINNAL_TITLE="original_title";
        final String POSTER_PATH="poster_path";
        final String OVERVIEW="OVERVIEW";
        final  String VOTE_AVERAGE="vote_average";
        final String RELEASE_DATE="release_date";
    if (MovieJsonString==null || "".equals(MovieJsonString)){

        return null;
    }
       JSONObject jsonObjectMov= new JSONObject(MovieJsonString);
       JSONArray jsonArrayMov= jsonObjectMov.getJSONArray("results");

       Movies[] movies=new Movies[jsonArrayMov.length()];

       for (int i=0;i<jsonArrayMov.length();i++){

           JSONObject object = jsonArrayMov.getJSONObject(i);
           movies[i]=new Movies(object.getString(ORIGINNAL_TITLE),
           object.getString(POSTER_PATH),
                   object.getString(OVERVIEW),
                   object.getString(VOTE_AVERAGE),
                   object.getString(RELEASE_DATE));
       }
       return movies;
   }
    @Override
    protected Movies[] doInBackground(String... params) {
        if(params.length==0){

            return  null;
        }

        final String BASE_URL="https://api.themoviedb.org/3/movie/";
        final String API_KEY="api_key";

        HttpURLConnection urlConnection=null;
        String MovieJsonString=null;
        BufferedReader reader=null;

        Uri.Builder uri=Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(params[0])
                .appendQueryParameter(API_KEY,BuildConfig.THE_MOVIEDB_API_KEY);

        try {
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            MovieJsonString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            return getMoviesFromJsonFormat(MovieJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Movies[] movies) {
        MovieTaskCallback.updateAdapter(movies);
    }
}