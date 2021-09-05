package com.teesofttech.mkremit.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.PrefManager;
import com.teesofttech.mkremit.loginutils.LoginActivity;

public class SplashscreenActivity extends AppCompatActivity {
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_splashscreen);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(SplashscreenActivity.this, LoginActivity.class);
                    SplashscreenActivity.this.startActivity(mainIntent);
                    SplashscreenActivity.this.finish();
                }
            }, 2000);
        }
    }

    private void launchHomeScreen() {

        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
        finish();

    }
}