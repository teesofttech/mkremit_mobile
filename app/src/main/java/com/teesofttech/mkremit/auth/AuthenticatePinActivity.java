package com.teesofttech.mkremit.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_pin);

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