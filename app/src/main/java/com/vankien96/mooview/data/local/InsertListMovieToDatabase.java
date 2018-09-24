package com.vankien96.mooview.data.local;

import android.os.AsyncTask;
import com.vankien96.mooview.data.local.entity.MovieEntity;
import java.util.List;

/**
 * Created by Admin on 19/12/2017.
 */

public class InsertListMovieToDatabase extends AsyncTask<List<MovieEntity>, Void, Void> {
    private MovieDatabase mMovieDatabase;

    public InsertListMovieToDatabase(MovieDatabase movieDatabase) {
        mMovieDatabase = movieDatabase;
    }

    @Override
    protected Void doInBackground(List<MovieEntity>[] lists) {
        mMovieDatabase.getMovieDAO().insertListMovie(lists[0]);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
