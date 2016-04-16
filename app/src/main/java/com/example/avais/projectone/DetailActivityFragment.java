package com.example.avais.projectone;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private Movie mMovie;

    private static final String ARG_MOVIE = DetailActivity.class.getSimpleName() + "movie";

    public static DetailActivityFragment newInstance(final Movie movie) {
        final DetailActivityFragment fragment = new DetailActivityFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        //Log.i("Avais",movie.getmMoviePosterUrl());
        return fragment;
    }


    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate( final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mMovie = args.getParcelable(ARG_MOVIE);
        //Log.i("Avais",mMovie.getmMoviePosterUrl());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        ((TextView) rootView.findViewById(R.id.detail_movie_title))
                .setText(mMovie.getmOriginalTitle());
        ((TextView) rootView.findViewById(R.id.detail_movie_plot)).setText(mMovie.getmOverview());
        final String userRating = mMovie.getmVoteAverage();
        ((TextView) rootView.findViewById(R.id.detail_movie_user_rating)).setText(userRating);
        final String releaseDate = mMovie.getmReleaseDate();
        ((TextView) rootView.findViewById(R.id.detail_movie_release_date)).setText(releaseDate);
        final ImageView posterImage =
                (ImageView) rootView.findViewById(R.id.detail_poster_image_view);
        Picasso.with(getActivity()).load(mMovie.getmMoviePosterUrl()).into(posterImage);
        return rootView;
    }


}
