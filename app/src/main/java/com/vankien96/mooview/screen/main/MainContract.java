package com.vankien96.mooview.screen.main;

import com.vankien96.mooview.screen.BasePresenter;
import com.vankien96.mooview.screen.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
interface MainContract {
    /**
     * View.
     */
    interface MainView extends BaseView {
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<MainView> {
    }
}
