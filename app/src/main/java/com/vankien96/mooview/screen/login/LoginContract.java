package com.vankien96.mooview.screen.login;

import com.vankien96.mooview.screen.BasePresenter;
import com.vankien96.mooview.screen.BaseView;
import org.json.JSONObject;

/**
 * This specifies the contract between the view and the presenter.
 */
interface LoginContract {
    /**
     * View.
     */
    interface LoginView extends BaseView {
        void onLoginFacebookSuccess(JSONObject jsonObject);

        void onLoginFacebookCancel();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<LoginView> {
        void doLoginFacebook();
    }
}
