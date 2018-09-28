package com.vankien96.mooview.screen.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.vankien96.mooview.R;
import com.vankien96.mooview.screen.BaseActivity;
import com.vankien96.mooview.screen.main.MainActivity;
import com.vankien96.mooview.utils.Constant;
import org.json.JSONObject;

/**
 * Login Screen.
 */
public class LoginActivity extends BaseActivity
        implements LoginContract.LoginView, View.OnClickListener {

    LoginContract.Presenter mPresenter;
    private CallbackManager mCallbackManager;
    private LoginManager mLoginManager;
    private Button mButtonLoginFacebook, mButtonLoginGuest;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();

        mPresenter = new LoginPresenter(this, mCallbackManager, mLoginManager);
        mPresenter.setView(this);

        initViews();
    }

    private void initViews() {
        mButtonLoginFacebook = findViewById(R.id.button_login_facebook);
        mButtonLoginGuest = findViewById(R.id.button_login_guest);

        mButtonLoginFacebook.setOnClickListener(this);
        mButtonLoginGuest.setOnClickListener(this);
        mSharedPreferences = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login_facebook:
                mPresenter.doLoginFacebook();
                break;
            case R.id.button_login_guest:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoginFacebookSuccess(JSONObject data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constant.PREF_USER, data.toString());
        editor.apply();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFacebookCancel() {
        Toast.makeText(this, getResources().getString(R.string.login_failed), Toast.LENGTH_LONG)
                .show();
    }
}
