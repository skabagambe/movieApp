package com.example.android.movieapp.parserMovie;
import android.os.Parcel;
import android.os.Parcelable;


public class Movies implements Parcelable {
    private final String title;
    private final String posterPath;
    private final String overView;
    private final String voteAverage;
    private final String releaseDate;

    public Movies(String title, String posterPath, String overView, String voteAverage, String releaseDate) {

        this.title = title;
        this.posterPath = posterPath;
        this.overView = overView;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    private Movies(Parcel parcel) {

        title = parcel.readString();
        posterPath = parcel.readString();
        overView = parcel.readString();
        voteAverage = parcel.readString();
        releaseDate = parcel.readString();

    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overView;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(overView);
        parcel.writeString(voteAverage);
        parcel.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int i) {

            return new Movies[i];
        }
    };

}