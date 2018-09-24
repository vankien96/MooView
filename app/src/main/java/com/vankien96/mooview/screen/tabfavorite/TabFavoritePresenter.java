package com.vankien96.mooview.screen.tabfavorite;

import com.vankien96.mooview.data.local.entity.MovieEntity;
import com.vankien96.mooview.data.model.Movie;
import com.vankien96.mooview.data.service.config.MoviesApi;
import java.util.ArrayList;
import java.util.List;

/**
 * Listens to user actions from the UI ({@link TabFavoriteFragment}), retrieves the data and updates
 * the UI as required.
 */
final class TabFavoritePresenter implements TabFavoriteContract.Presenter {
    private static final String TAG = TabFavoritePresenter.class.getName();

    TabFavoriteContract.FavoriteView mFavoriteView;
    private MoviesApi mMoviesApi;
    private List<MovieEntity> mFavoriteMovieEntityList = new ArrayList<>();
    private Movie mMovie = new Movie();

    TabFavoritePresenter() {
    }

    @Override
    public void setView(TabFavoriteContract.FavoriteView view) {
        mFavoriteView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setMovieApi(MoviesApi moviesApi) {
        mMoviesApi = moviesApi;
    }
}
