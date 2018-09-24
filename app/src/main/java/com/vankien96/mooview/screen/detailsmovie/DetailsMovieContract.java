package com.vankien96.mooview.screen.detailsmovie;

import com.vankien96.mooview.data.model.Cast;
import com.vankien96.mooview.data.model.Movie;
import com.vankien96.mooview.data.model.Trailer;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.screen.BasePresenter;
import com.vankien96.mooview.screen.BaseView;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface DetailsMovieContract {
    /**
     * View.
     */
    interface DetailsMovieView extends BaseView {
        Integer getMovieId();

        void onDetailsMovieSuccess(Movie body, List<String> listGenre);

        void onListTrailerSuccess(List<Trailer> listTrailer);

        void onListCastMovieSuccess(List<Cast> listCast);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<DetailsMovieView> {
        void setView(DetailsMovieContract.DetailsMovieView detailsMovieView);

        void setMovieApi(MoviesApi moviesApi);

        void getDetailsMovie();

        void getMovieTrailers();

        void getCastsMovie();
    }
}
