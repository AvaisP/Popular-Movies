package com.example.avais.projectone;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by avais on 15/4/16.
 */
public class Movie implements Parcelable{
    private final String mId;
    private final String mOriginalTitle;
    private final String mMoviePosterUrl;
    private final String mOverview;
    private final String mVoteAverage;
    private final String mReleaseDate;
    private String mBaseUrl = "http://image.tmdb.org/t/p/";
    private String mSize = "w780/";


    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mId);
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mMoviePosterUrl);
        parcel.writeString(mOverview);
        parcel.writeString(mVoteAverage);
        parcel.writeString(mReleaseDate);

    }


    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(final Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(final int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public Movie(final String id, final String originalTitle, final String moviePosterUrl,
                 final String overview, final String voteAverage, final String releaseDate) {
        mId = id;
        mOriginalTitle = originalTitle;
        mMoviePosterUrl = mBaseUrl + mSize + moviePosterUrl;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
    }


    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Movie(final Parcel in) {
        mId = in.readString();
        mOriginalTitle = in.readString();
        mMoviePosterUrl = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readString();
        mReleaseDate = in.readString();
    }

    public String getmId() {
        return mId;
    }

    public String getmOriginalTitle() {
        return mOriginalTitle;
    }

    public String getmMoviePosterUrl() {
        return mMoviePosterUrl;
    }

    public String getmOverview() {
        return mOverview;
    }

    public String getmVoteAverage() {
        return mVoteAverage;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmBaseUrl() {
        return mBaseUrl;
    }

    public String getmSize() {
        return mSize;
    }
}
