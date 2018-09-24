package com.vankien96.mooview.screen.tabhome;

import android.util.Log;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.data.service.response.GetListMoviesResponse;
import com.vankien96.mooview.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Listens to user actions from the UI ({@link TabHomeFragment}), retrieves the data and updates
 * the UI as required.
 */
final class TabHomePresenter implements TabHomeContract.Presenter {
    public static final String TAG = TabHomePresenter.class.getName();
    private MoviesApi mMoviesApi;

    private TabHomeContract.HomeView mHomeView;

    TabHomePresenter() {
    }

    @Override
    public void setView(TabHomeContract.HomeView view) {
        mHomeView = view;
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
    public void getListNowPlayingMovie() {
        mMoviesApi.getListMovies(Constant.CATEGORY_NOW_PLAYING, Constant.API_KEY, Constant.LANGUAGE,
                Constant.DEFAULT_PAGE).enqueue(new Callback<GetListMoviesResponse>() {
            @Override
            public void onResponse(Call<GetListMoviesResponse> call,
                    Response<GetListMoviesResponse> response) {
                if (response.body() == null || response.body().getResults() == null) {
                    return;
                }
                mHomeView.onListNowPlayingMovieSuccess(response.body().getResults());
            }

            @Override
            public void onFailure(Call<GetListMoviesResponse> call, Throwable t) {
                Log.e(TAG, "onGetListNowPlayingMovie: ", t);
            }
        });
    }

    @Override
    public void getListUpComingMovie() {
        mMoviesApi.getListMovies(Constant.CATEGORY_UPCOMING, Constant.API_KEY, Constant.LANGUAGE,
                Constant.DEFAULT_PAGE).enqueue(new Callback<GetListMoviesResponse>() {
            @Override
            public void onResponse(Call<GetListMoviesResponse> call,
                    Response<GetListMoviesResponse> response) {
                if (response.body() == null || response.body().getResults() == null) {
                    return;
                }
                mHomeView.onListUpComingMovieSuccess(response.body().getResults());
            }

            @Override
            public void onFailure(Call<GetListMoviesResponse> call, Throwable t) {
                Log.e(TAG, "onGetListUpComingMovieFailure: ", t);
            }
        });
    }

    @Override
    public void getListTopRatedMovie() {
        mMoviesApi.getListMovies(Constant.CATEGORY_TOP_RATED, Constant.API_KEY, Constant.LANGUAGE,
                Constant.DEFAULT_PAGE).enqueue(new Callback<GetListMoviesResponse>() {
            @Override
            public void onResponse(Call<GetListMoviesResponse> call,
                    Response<GetListMoviesResponse> response) {
                if (response.body() == null || response.body().getResults() == null) {
                    return;
                }
                mHomeView.onListTopRatedMovieSuccess(response.body().getResults());
            }

            @Override
            public void onFailure(Call<GetListMoviesResponse> call, Throwable t) {
                Log.e(TAG, "onGetListTopRatedMovieFailure: ", t);
            }
        });
    }

    @Override
    public void getListPopularMovie() {
        mMoviesApi.getListMovies(Constant.CATEGORY_POPULAR, Constant.API_KEY, Constant.LANGUAGE,
                Constant.DEFAULT_PAGE).enqueue(new Callback<GetListMoviesResponse>() {
            @Override
            public void onResponse(Call<GetListMoviesResponse> call,
                    Response<GetListMoviesResponse> response) {
                if (response.body() == null || response.body().getResults() == null) {
                    return;
                }
                mHomeView.onListPopularMoviesSuccess(response.body().getResults());
            }

            @Override
            public void onFailure(Call<GetListMoviesResponse> call, Throwable t) {
                Log.e(TAG, "onGetListPopularMovieFailure: ", t);
            }
        });
    }
}
