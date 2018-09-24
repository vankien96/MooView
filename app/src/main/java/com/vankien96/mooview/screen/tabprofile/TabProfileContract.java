package com.vankien96.mooview.screen.tabprofile;

import com.vankien96.mooview.screen.BasePresenter;
import com.vankien96.mooview.screen.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
interface TabProfileContract {
    /**
     * View.
     */
    interface ProfileView extends BaseView {
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ProfileView> {
    }
}
