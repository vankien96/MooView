package com.vankien96.mooview.data.local;

import android.os.AsyncTask;

/**
 * Created by Admin on 21/12/2017.
 */

public class GetMovieFromDatabase extends AsyncTask<Integer, Void, Integer> {
    private MovieDatabase mMovieDatabase;

    public GetMovieFromDatabase(MovieDatabase movieDatabase) {
        mMovieDatabase = movieDatabase;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        int isFavorite;
        isFavorite = mMovieDatabase.getMovieDAO().isFavoriteMovie(integers[0]);
        return isFavorite;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }
}
