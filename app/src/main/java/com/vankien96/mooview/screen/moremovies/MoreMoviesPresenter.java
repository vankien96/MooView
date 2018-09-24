package com.vankien96.mooview.screen.moremovies;

import android.util.Log;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.data.service.response.GetListMoviesResponse;
import com.vankien96.mooview.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Listens to user actions from the UI ({@link MoreMoviesActivity}), retrieves the data and updates
 * the UI as required.
 */
final class MoreMoviesPresenter implements MoreMoviesContract.Presenter {
    private static final String TAG = MoreMoviesPresenter.class.getName();

    private MoreMoviesContract.MoreMoviesView mMoreMoviesView;
    private MoviesApi mMoviesApi;

    MoreMoviesPresenter() {
    }

    @Override
    public void setView(MoreMoviesContract.MoreMoviesView moreMoviesView) {
        mMoreMoviesView = moreMoviesView;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setMoviesApi(MoviesApi moviesApi) {
        mMoviesApi = moviesApi;
    }

    @Override
    public void getListMoreMovie(int currentPage) {
        String category = mMoreMoviesView.getCategoryMovie();
        mMoviesApi.getListMovies(category, Constant.API_KEY, Constant.LANGUAGE, currentPage)
                .enqueue(new Callback<GetListMoviesResponse>() {
                    @Override
                    public void onResponse(Call<GetListMoviesResponse> call,
                            Response<GetListMoviesResponse> response) {
                        if (response.body() == null || response.body().getResults() == null) {
                            return;
                        }
                        mMoreMoviesView.onListMoreMoviesSuccess(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<GetListMoviesResponse> call, Throwable t) {
                        Log.e(TAG, "onListMoreMoviesFailure: ", t);
                    }
                });
    }

    @Override
    public void getListMoviesByCategory() {
        String category = mMoreMoviesView.getCategoryMovie();
        mMoviesApi.getListMovies(category, Constant.API_KEY, Constant.LANGUAGE,
                Constant.DEFAULT_PAGE).enqueue(new Callback<GetListMoviesResponse>() {
            @Override
            public void onResponse(Call<GetListMoviesResponse> call,
                    Response<GetListMoviesResponse> response) {
                if (response.body() == null || response.body().getResults() == null) {
                    return;
                }
                mMoreMoviesView.onListMovieSuccess(response.body().getResults());
            }

            @Override
            public void onFailure(Call<GetListMoviesResponse> call, Throwable t) {
                Log.e(TAG, "onGetListMoviesByCategoryFailure: ", t);
            }
        });
    }
}
