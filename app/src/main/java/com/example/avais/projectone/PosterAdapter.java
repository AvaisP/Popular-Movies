package com.example.avais.projectone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avais on 15/4/16.
 */
public class PosterAdapter extends ArrayAdapter<Movie> {

    Context c;
    String urls[];
    ArrayList<Movie> mMovies;
    int id;
    public PosterAdapter(final Context context, final int resource, final ArrayList<Movie> movies) // using the ArrayAdapter(Context context, int resource, List<T> objects) constructor
    {
        super (context, resource, movies);
        c = context;
        id = resource;
        mMovies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(id, parent, false);
        }
        Movie current = mMovies.get(position);
        String url = current.getmMoviePosterUrl();
        ImageView iv = (ImageView) convertView.findViewById(R.id.movie_poster);
        Picasso.with(c).load(url).into(iv);
        return convertView;
    }
}
