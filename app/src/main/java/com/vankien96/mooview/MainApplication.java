package com.vankien96.mooview;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vankien96.mooview.data.local.MovieDatabase;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.data.service.config.ServiceGenerators;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

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
