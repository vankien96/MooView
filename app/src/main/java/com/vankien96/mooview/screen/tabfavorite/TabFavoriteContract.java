package com.vankien96.mooview.screen.tabfavorite;

import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.screen.BasePresenter;
import com.vankien96.mooview.screen.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
interface TabFavoriteContract {
    /**
     * View.
     */
    interface FavoriteView extends BaseView {

    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<FavoriteView> {
        void setMovieApi(MoviesApi moviesApi);
    }
}
