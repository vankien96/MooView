package com.vankien96.mooview.screen.moremovies;

import com.vankien96.mooview.data.model.Film;
import com.vankien96.mooview.data.model.Movie;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.screen.BasePresenter;
import com.vankien96.mooview.screen.BaseView;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface MoreMoviesContract {
    /**
     * View.
     */
    interface MoreMoviesView extends BaseView {
        String getCategoryMovie();

        void onListMoreMoviesSuccess(List<Movie> results);

        void onListMovieSuccess(List<Film> results);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<MoreMoviesView> {
        void setMoviesApi(MoviesApi moviesApi);

        void getListMoreMovie(int currentPage);

        void getListMoviesByCategory();
    }
}
