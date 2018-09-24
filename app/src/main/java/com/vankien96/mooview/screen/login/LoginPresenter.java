package com.vankien96.mooview.screen.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import java.util.Arrays;
import org.json.JSONObject;

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */
final class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = LoginPresenter.class.getName();

    private static final String PROFILE_PERMISSION = "public_profile";
    private static final String EMAIL_PERMISSION = "email";
    private static final String FIELDS = "fields";
    private static final String INFORMATION =
            "email, first_name, last_name, picture.type(large), cover.type(large)";
    private LoginContract.LoginView mLoginView;
    private CallbackManager mCallbackManager;
    private LoginManager mLoginManager;
    private Activity mActivity;

    LoginPresenter(Activity activity, CallbackManager callbackManager,
            LoginManager loginManager) {
        mActivity = activity;
        mCallbackManager = callbackManager;
        mLoginManager = loginManager;
    }

    @Override
    public void setView(LoginContract.LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void doLoginFacebook() {
        mLoginManager.logInWithReadPermissions(mActivity,
                Arrays.asList(PROFILE_PERMISSION, EMAIL_PERMISSION));
        mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest graphRequest =
                        GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,
                                            GraphResponse response) {
                                        mLoginView.onLoginFacebookSuccess(response.getJSONObject());
                                    }
                                });
                Bundle param = new Bundle();
                param.putString(FIELDS, INFORMATION);
                graphRequest.setParameters(param);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                mLoginView.onLoginFacebookCancel();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "onError: ", error);
            }
        });
    }
}
