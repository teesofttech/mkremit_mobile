package com.teesofttech.mkremit.auth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.PrefUtils;
import com.teesofttech.mkremit.dashboard.DashboardActivity;
import com.teesofttech.mkremit.loginutils.LoginActivity;
import com.teesofttech.mkremit.models.PINModel;
import com.teesofttech.mkremit.models.UserModel;

import in.arjsna.passcodeview.PassCodeView;

public class AuthenticatePinActivity extends AppCompatActivity {

    UserModel userModel;
    private PassCodeView passCodeView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_pin);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.custom_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView txttitle = (TextView) viewActionBar.findViewById(R.id.txtTitle);
        txttitle.setText("AUTHENTICATE  PIN");
        txttitle.setTextSize(14);
        if (abar != null) {
            abar.setCustomView(viewActionBar, params);

            abar.setDisplayShowCustomEnabled(true);
            abar.setDisplayShowTitleEnabled(false);
            abar.setHomeButtonEnabled(false);
        }


        passCodeView = (PassCodeView) findViewById(R.id.pass_code_view);
        TextView promptView = (TextView) findViewById(R.id.promptview);
        Typeface typeFace = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Font-Bold.ttf");
        passCodeView.setTypeFace(typeFace);
        passCodeView.setKeyTextColor(R.color.black_shade);
        passCodeView.setEmptyDrawable(R.drawable.empty_dot);
        passCodeView.setFilledDrawable(R.drawable.filled_dot);
        promptView.setTypeface(typeFace);
        bindEvents();
    }

    private void bindEvents() {
        passCodeView.setOnTextChangeListener(new PassCodeView.TextChangeListener() {
            @Override
            public void onTextChanged(String text) {
                if (text.length() == 4) {
                    PINModel pinModel = PrefUtils.getPinUser(AuthenticatePinActivity.this);
                    Log.d("INPIN", pinModel.getCode());
                    if (pinModel.getCode().equals(text)) {
                        if (PrefUtils.getCurrentUser(AuthenticatePinActivity.this) != null) {

                            userModel = PrefUtils.getCurrentUser(AuthenticatePinActivity.this);

                            Intent ii = new Intent(AuthenticatePinActivity.this, DashboardActivity.class);
                            startActivity(ii);
                        }
                    } else {
                        passCodeView.setError(true);
                    }
                }
            }
        });
    }

}