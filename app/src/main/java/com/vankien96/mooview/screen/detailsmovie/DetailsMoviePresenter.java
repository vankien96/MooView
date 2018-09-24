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
        long movieId = mDetailsMovieView.getMovieId();
        mMoviesApi.getDetailsMovie(movieId, Constant.API_KEY, Constant.LANGUAGE)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.body() == null) {
                            return;
                        }
                        List<String> listGenre = new ArrayList<>();
                        for (Genre genre : response.body().getGenres()) {
                            listGenre.add(genre.getName());
                        }
                        mDetailsMovieView.onDetailsMovieSuccess(response.body(), listGenre);
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                    }
                });
    }

    @Override
    public void getMovieTrailers() {
        long movieId = mDetailsMovieView.getMovieId();
        mMoviesApi.getListTrailer(movieId, Constant.API_KEY)
                .enqueue(new Callback<GetListTrailerResponse>() {
                    @Override
                    public void onResponse(Call<GetListTrailerResponse> call,
                            Response<GetListTrailerResponse> response) {
                        if (response.body() == null) {
                            return;
                        }
                        mDetailsMovieView.onListTrailerSuccess(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<GetListTrailerResponse> call, Throwable t) {
                        Log.e(TAG, "onGetMovieTrailersFailure: ", t);
                    }
                });
    }

    @Override
    public void getCastsMovie() {
        long movieId = mDetailsMovieView.getMovieId();
        mMoviesApi.getCreditsMovie(movieId, Constant.API_KEY)
                .enqueue(new Callback<GetCreditsResponse>() {
                    @Override
                    public void onResponse(Call<GetCreditsResponse> call,
                            Response<GetCreditsResponse> response) {
                        if (response.body() == null || response.body().getCast() == null) {
                            return;
                        }
                        mDetailsMovieView.onListCastMovieSuccess(response.body().getCast());
                    }

                    @Override
                    public void onFailure(Call<GetCreditsResponse> call, Throwable t) {
                        Log.e(TAG, "onGetCastsMovieFailure: ", t);
                    }
                });
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
