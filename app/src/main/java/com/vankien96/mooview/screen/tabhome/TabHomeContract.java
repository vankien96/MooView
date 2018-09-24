package com.vankien96.mooview.screen.tabhome;

import com.vankien96.mooview.data.model.Movie;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.screen.BasePresenter;
import com.vankien96.mooview.screen.BaseView;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface TabHomeContract {
    /**
     * View.
     */
    interface HomeView extends BaseView {
        void onListNowPlayingMovieSuccess(List<Movie> listNowPlayingMovie);

        void onListUpComingMovieSuccess(List<Movie> listUpComingMovie);

        void onListTopRatedMovieSuccess(List<Movie> listTopRatedMovie);

        void onListPopularMoviesSuccess(List<Movie> listPopularMovie);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<HomeView> {
        void setMoviesApi(MoviesApi moviesApi);

        void getListNowPlayingMovie();

        void getListUpComingMovie();

        void getListTopRatedMovie();

        void getListPopularMovie();
    }
}
