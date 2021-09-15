package com.teesofttech.mkremit.auth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.PrefUtils;
import com.teesofttech.mkremit.loginutils.LoginActivity;
import com.teesofttech.mkremit.models.PINModel;
import com.teesofttech.mkremit.models.UserModel;

import in.arjsna.passcodeview.PassCodeView;

public class SetPinActivity extends AppCompatActivity {
    UserModel userModel;
    private PassCodeView passCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.custom_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView txttitle = (TextView) viewActionBar.findViewById(R.id.txtTitle);
        txttitle.setText("S E T  P I N");
        txttitle.setTextSize(14);
        if (abar != null) {
            abar.setCustomView(viewActionBar, params);

            abar.setDisplayShowCustomEnabled(true);
            abar.setDisplayShowTitleEnabled(false);
            abar.setHomeButtonEnabled(false);
        }


        passCodeView = (PassCodeView) findViewById(R.id.pass_code_view);
        TextView promptView = (TextView) findViewById(R.id.promptview);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Font-Bold.ttf");
        passCodeView.setTypeFace(typeFace);
        passCodeView.setKeyTextColor(R.color.black_shade);
        passCodeView.setEmptyDrawable(R.drawable.empty_dot);
        passCodeView.setFilledDrawable(R.drawable.filled_dot);
        promptView.setTypeface(typeFace);
        bindEvents();


//        PasscodeView passcodeView = (PasscodeView) findViewById(R.id.passcodeView);
//        userModel = PrefUtils.getCurrentUser(SetPinActivity.this);
//        passcodeView.setListener(new PasscodeView.PasscodeViewListener() {
//
//            @Override
//            public void onFail() {
//
//            }
//
//            @Override
//            public void onSuccess(String number) {
//                // Toast.makeText(getApplication(), "finish", Toast.LENGTH_SHORT).show();
//                // onBackPressed();
//                PINModel pinModel = new PINModel();
//                pinModel.setCode(number);
//                pinModel.setDealerCode(userModel.getDealerCode());
//                PrefUtils.setPinUser(pinModel, SetPinActivity.this);
//                startActivity(new Intent(SetPinActivity.this, LoginActivity.class));
//                finish();
//            }
//        });
    }

    private void bindEvents() {
        passCodeView.setOnTextChangeListener(new PassCodeView.TextChangeListener() {
            @Override
            public void onTextChanged(String text) {
                if (text.length() == 4) {
                    PINModel pinModel = new PINModel();
                    pinModel.setCode(text);
                    pinModel.setDealerCode(userModel.getDealerCode());
                    PrefUtils.setPinUser(pinModel, SetPinActivity.this);
                    startActivity(new Intent(SetPinActivity.this, LoginActivity.class));
                    finish();
                } else {
                    passCodeView.setError(true);
                }
            }
        });
    }
}