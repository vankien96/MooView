package com.vankien96.mooview.screen.detailsmovie;

import android.util.Log;
import com.vankien96.mooview.data.model.Genre;
import com.vankien96.mooview.data.model.Movie;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.data.service.response.GetCreditsResponse;
import com.vankien96.mooview.data.service.response.GetListTrailerResponse;
import com.vankien96.mooview.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Listens to user actions from the UI, retrieves the data and updates
 * the UI as required.
 */
final class DetailsMoviePresenter implements DetailsMovieContract.Presenter {
    private static final String TAG = DetailsMoviePresenter.class.getName();

    private DetailsMovieContract.DetailsMovieView mDetailsMovieView;
    private MoviesApi mMoviesApi;

    DetailsMoviePresenter() {
    }

    @Override
    public void setView(DetailsMovieContract.DetailsMovieView detailsMovieView) {
        mDetailsMovieView = detailsMovieView;
    }

    @Override
    public void setMovieApi(MoviesApi moviesApi) {
        mMoviesApi = moviesApi;
    }

    @Override
    public void getDetailsMovie() {
    }

    @Override
    public void getMovieTrailers() {
    }

    @Override
    public void getCastsMovie() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
