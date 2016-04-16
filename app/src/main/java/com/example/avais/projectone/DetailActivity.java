package com.example.avais.projectone;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    private Movie mMovie;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.i("Avais","Detail");
        mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            final DetailActivityFragment fragment = DetailActivityFragment.newInstance(mMovie);
            getSupportFragmentManager().beginTransaction().add(R.id.detailFrame, fragment)
                    .commit();
        }
    }


