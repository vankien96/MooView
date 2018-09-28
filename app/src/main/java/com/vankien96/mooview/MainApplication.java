package com.vankien96.mooview;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.vankien96.mooview.data.local.MovieDatabase;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.data.service.config.ServiceGenerators;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.vankien96.mooview",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static MoviesApi getMoviesApi() {
        return mMoviesApi;
    }

    public static MovieDatabase getMovieDatabase() {
        return mMovieDatabase;
    }
}
