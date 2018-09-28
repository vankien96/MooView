package com.vankien96.mooview.screen.tabprofile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.vankien96.mooview.R;
import com.vankien96.mooview.screen.BaseFragment;
import com.vankien96.mooview.screen.about.AboutActivity;
import com.vankien96.mooview.screen.login.LoginActivity;
import com.vankien96.mooview.utils.Constant;

import de.hdodenhof.circleimageview.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * TabProfile Screen.
 */
public class TabProfileFragment extends BaseFragment
        implements TabProfileContract.ProfileView, View.OnClickListener {
    private static final String TAG = "TabProfileFragment";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PICTURE = "picture";
    private static final String DATA = "data";
    private static final String URL = "url";
    private static final String COVER = "cover";
    private static final String SOURCE = "source";
    private static final String SPACE = " ";
    TabProfileContract.Presenter mPresenter;
    private Button mButtonLogin;
    private RelativeLayout mRelativeGuest;
    private ScrollView mScrollFacebook;
    private TextView mTextNameUser, mTextLogout, mTextFeedback, mTextSetting, mTextInvite, mTextAbout;
    private ImageView mImageCover;
    private CircleImageView mImageAvatar;
    private SharedPreferences mSharedPreferences;
    private JSONObject mDataUser;

    public static TabProfileFragment newInstance() {
        return new TabProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new TabProfilePresenter();
        mPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabprofile, container, false);
        initViews(view);
        isLoginWithFacebook();
        return view;
    }

    private void initViews(View view) {
        mButtonLogin = view.findViewById(R.id.button_login_profile);
        mScrollFacebook = view.findViewById(R.id.scroll_profile_facebook);
        mRelativeGuest = view.findViewById(R.id.relative_profile_guest);
        mTextNameUser = view.findViewById(R.id.text_name_user);
        mImageAvatar = view.findViewById(R.id.image_avatar);
        mImageCover = view.findViewById(R.id.image_cover);
        mTextLogout = view.findViewById(R.id.text_logout);
        mTextFeedback = view.findViewById(R.id.text_feedback);
        mTextSetting = view.findViewById(R.id.text_setting);
        mTextInvite = view.findViewById(R.id.text_invite_friend);
        mTextAbout = view.findViewById(R.id.text_about);

        mButtonLogin.setOnClickListener(this);
        mTextLogout.setOnClickListener(this);
        mTextFeedback.setOnClickListener(this);
        mTextSetting.setOnClickListener(this);
        mTextInvite.setOnClickListener(this);
        mTextAbout.setOnClickListener(this);

        mSharedPreferences =
                getActivity().getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
    }

    private void isLoginWithFacebook() {
        String dataUser = mSharedPreferences.getString(Constant.PREF_USER, Constant.DEFAULT);
        if (dataUser.equals(Constant.DEFAULT)) {
            mRelativeGuest.setVisibility(View.VISIBLE);
            mScrollFacebook.setVisibility(View.GONE);
        } else {
            mRelativeGuest.setVisibility(View.GONE);
            mScrollFacebook.setVisibility(View.VISIBLE);
            try {
                mDataUser = new JSONObject(dataUser);
                String username =
                        mDataUser.getString(LAST_NAME) + SPACE + mDataUser.getString(FIRST_NAME);
                String urlAvatar =
                        mDataUser.getJSONObject(PICTURE).getJSONObject(DATA).getString(URL);
                String urlCover = mDataUser.getJSONObject(COVER).getString(SOURCE);

                mTextNameUser.setText(username);
                Glide.with(this).load(urlCover).into(mImageCover);
                Glide.with(this).load(urlAvatar).into(mImageAvatar);
            } catch (JSONException e) {
                Log.e(TAG, "JSONException: ", e);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login_profile:
                openLoginActivity();
                break;
            case R.id.text_logout:
                LoginManager.getInstance().logOut();
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.clear();
                editor.apply();
                openLoginActivity();
                break;
            case R.id.text_feedback:
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                        getResources().getString(R.string.truyen_mail),
                        getResources().getString(R.string.thang_mail)
                });
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                        getResources().getString(R.string.subject_email));
                emailIntent.putExtra(Intent.EXTRA_TEXT,
                        getResources().getString(R.string.content_email));
                startActivity(Intent.createChooser(emailIntent,
                        getResources().getString(R.string.title_choice_app)));
                break;
            case R.id.text_setting:
                Toast.makeText(getActivity(), "Coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_invite_friend:
                Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_about:
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void openLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
