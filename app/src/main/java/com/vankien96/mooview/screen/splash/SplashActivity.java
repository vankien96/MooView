package com.vankien96.mooview.screen.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.vankien96.mooview.R;
import com.vankien96.mooview.screen.BaseActivity;
import com.vankien96.mooview.screen.main.MainActivity;
import com.vankien96.mooview.utils.Constant;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Splash Screen.
 */
public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    private Thread mSplashThread;
    private RelativeLayout mRelativeLayout;
    private ImageView mImageView;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViews();
        startAnimations();
    }

    private void initViews() {
        mRelativeLayout = findViewById(R.id.relative_layout);
        mImageView = findViewById(R.id.image_splash);
    }

    private void startAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        mRelativeLayout.clearAnimation();
        mRelativeLayout.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        mImageView.clearAnimation();
        mImageView.startAnimation(anim);

        mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    SharedPreferences sharedPreferences =
                            SplashActivity.this.getSharedPreferences(Constant.PREF_NAME,
                                    Context.MODE_PRIVATE);
                    String dataUser =
                            sharedPreferences.getString(Constant.PREF_USER, Constant.DEFAULT);
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 2500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashActivity.this.finish();
                } catch (InterruptedException e) {
                    Log.e(TAG, "run: ", e);
                } finally {
                    SplashActivity.this.finish();
                }
            }
        };
        mSplashThread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
