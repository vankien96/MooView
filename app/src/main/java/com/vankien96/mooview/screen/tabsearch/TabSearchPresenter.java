package com.vankien96.mooview.screen.tabsearch;

import android.util.Log;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.data.service.response.GetListMoviesResponse;
import com.vankien96.mooview.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Listens to user actions from the UI ({@link TabSearchFragment}), retrieves the data and updates
 * the UI as required.
 */
final class TabSearchPresenter implements TabSearchContract.Presenter {
    private static final String TAG = TabSearchPresenter.class.getName();
    private MoviesApi mMoviesApi;

    TabSearchContract.SearchView mSearchView;

    TabSearchPresenter() {
    }

    @Override
    public void setView(TabSearchContract.SearchView view) {
        mSearchView = view;
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
    public void getListSearchMovie(String keyWord) {
        mMoviesApi.searchMovies(keyWord, Constant.API_KEY, Constant.LANGUAGE, Constant.DEFAULT_PAGE)
                .enqueue(new Callback<GetListMoviesResponse>() {
                    @Override
                    public void onResponse(Call<GetListMoviesResponse> call,
                            Response<GetListMoviesResponse> response) {
                        if (response.body() == null || response.body().getResults() == null) {
                            return;
                        }
                        mSearchView.onListSearchMovieSuccess(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<GetListMoviesResponse> call, Throwable t) {
                        Log.e(TAG, "onGetListSearchMovieFailure: ", t);
                    }
                });
    }

    @Override
    public void seachMoreMovies(int currentPage, String keyWord) {
        mMoviesApi.searchMovies(keyWord, Constant.API_KEY, Constant.LANGUAGE, currentPage)
                .enqueue(new Callback<GetListMoviesResponse>() {

                    @Override
                    public void onResponse(Call<GetListMoviesResponse> call,
                            Response<GetListMoviesResponse> response) {
                        if (response.body() == null || response.body().getResults() == null) {
                            return;
                        }
                        mSearchView.onListMoreMovieSuccess(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<GetListMoviesResponse> call, Throwable t) {
                        Log.e(TAG, "onSeachMoreMoviesFailure: ", t);
                    }
                });
    }
}
