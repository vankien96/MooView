package com.vankien96.mooview.screen.tabsearch;

import com.vankien96.mooview.data.model.Movie;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.screen.BasePresenter;
import com.vankien96.mooview.screen.BaseView;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface TabSearchContract {
    /**
     * View.
     */
    interface SearchView extends BaseView {

        void onListSearchMovieSuccess(List<Movie> listSearchMovie);

        void onListMoreMovieSuccess(List<Movie> results);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<SearchView> {
        void setMoviesApi(MoviesApi moviesApi);

        void getListSearchMovie(String keyWord);

        void seachMoreMovies(int currentPage, String keyWord);
    }
}
