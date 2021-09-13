package com.teesofttech.mkremit.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;
import com.teesofttech.mkremit.R;

public class SetPinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        PasscodeView passcodeView = (PasscodeView) findViewById(R.id.passcodeView);
        passcodeView.setListener(new PasscodeView.PasscodeViewListener() {

            @Override
            public void onFail() {

            }

            @Override
            public void onSuccess(String number) {
                Toast.makeText(getApplication(), "finish", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
}