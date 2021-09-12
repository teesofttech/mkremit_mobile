package com.teesofttech.mkremit.registerutils;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.AlertDialogManager;
import com.teesofttech.mkremit.Utils.Constant;
import com.teesofttech.mkremit.app.AppController;
import com.teesofttech.mkremit.loginutils.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    AlertDialogManager alertDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        alertDialogManager = new AlertDialogManager();
        final EditText lastname = (EditText) findViewById(R.id.editLTextName);
        final EditText FirstName = (EditText) findViewById(R.id.editTextName);
        final EditText Email = (EditText) findViewById(R.id.editTextEmail);
        final EditText UserPassword = (EditText) findViewById(R.id.editTextPassword);
        final EditText UserPhone = (EditText) findViewById(R.id.editTextMobile);

        final ProgressDialog waitingDialog = new ProgressDialog(this);
        waitingDialog.setTitle("Registration");
        waitingDialog.setMessage("Please wait while we register your account");

        Button btnRegister = (Button) findViewById(R.id.btnLog);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lastname.getText().toString().equals("") && FirstName.getText().toString().equals("") && UserPhone.getText().toString().equals("")
                        && Email.getText().toString().equals("") && UserPassword.getText().toString().equals("")) {

                    waitingDialog.dismiss();
                    alertDialogManager.showAlertDialog(RegisterActivity.this, "Error", "Please fill up the empty field (s)", false);
                    return;

                } else {
                    waitingDialog.show();
                    JSONObject params = new JSONObject();

                    try {
                        params.put("email", Email.getText().toString().trim());
                        params.put("firstName", FirstName.getText().toString().trim());
                        params.put("lastName", lastname.getText().toString().trim());
                        params.put("phoneNumber", UserPhone.getText().toString().trim());
                        params.put("password", UserPassword.getText().toString().trim());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Make request for JSONObject
                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                            Request.Method.POST, Constant.REGISTER, params,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    waitingDialog.dismiss();
                                    Log.d("TAGGG", response.toString() + " i am queen");
                                    try {

                                        String statusCode = response.getString("statusCode");
                                        Log.d("stat", statusCode);
                                        if (statusCode.equals("200")) {
                                            alertDialogManager.showAlertDialog(RegisterActivity.this, "success", "Account has been created successfully, a mail has been sent to your email address to validate your email address", true);
                                            //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        } else {
                                            alertDialogManager.showAlertDialog(RegisterActivity.this, "failed", "Registration failed", false);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("TAGGG", "Error: " + error.getMessage());
                        }
                    }) {

                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }
                    };


                    DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    jsonObjReq.setRetryPolicy(retryPolicy);
                    AppController.getInstance().addToRequestQueue(jsonObjReq);

                }
            }
        });

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}