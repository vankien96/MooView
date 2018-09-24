package com.vankien96.mooview;

import android.app.Application;

import com.vankien96.mooview.data.local.MovieDatabase;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.data.service.config.ServiceGenerators;

/**
 * Created by Admin on 12/08/17.
 */

public class MainApplication extends Application {

    private static MoviesApi mMoviesApi;
    private static MovieDatabase mMovieDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mMoviesApi == null) {
            mMoviesApi = ServiceGenerators.createApiService(this);
        }

        mMovieDatabase = MovieDatabase.initDatabase(this);
    }

    public static MoviesApi getMoviesApi() {
        return mMoviesApi;
    }

    public static MovieDatabase getMovieDatabase() {
        return mMovieDatabase;
    }
}
