package com.teesofttech.mkremit.loginutils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.AlertDialogManager;
import com.teesofttech.mkremit.Utils.Constant;
import com.teesofttech.mkremit.Utils.PrefUtils;
import com.teesofttech.mkremit.app.AppController;
import com.teesofttech.mkremit.auth.AuthenticatePinActivity;
import com.teesofttech.mkremit.auth.SetPinActivity;
import com.teesofttech.mkremit.dashboard.DashboardActivity;
import com.teesofttech.mkremit.models.PINModel;
import com.teesofttech.mkremit.models.UserModel;
import com.teesofttech.mkremit.models.WalletModel;
import com.teesofttech.mkremit.registerutils.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    AlertDialogManager alertDialogManager;
    UserModel user;
    private ArrayList<WalletModel> cartList;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        TextView newuser = findViewById(R.id.newuser);
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        /*Button btnlog = findViewById(R.id.btnLog);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            }
        });*/

        cartList = new ArrayList<>();
        if (PrefUtils.getCurrentUser(LoginActivity.this) != null) {
            userModel = PrefUtils.getCurrentUser(LoginActivity.this);

            if (PrefUtils.getPinUser(LoginActivity.this) != null) {
                startActivity(new Intent(LoginActivity.this, AuthenticatePinActivity.class));
                finish();
            }

        } else {
        }

        final ProgressDialog waitingDialog = new ProgressDialog(this);
        waitingDialog.setTitle("Login...");
        waitingDialog.setMessage("Please wait while we validate your account");

        alertDialogManager = new AlertDialogManager();

        final EditText username = (EditText) findViewById(R.id.editTextEmail);
        final EditText password = (EditText) findViewById(R.id.editTextPassword);

        /*ImageView showpassword = (ImageView) findViewById(R.id.showpassword);
        showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getTransformationMethod().getClass().getSimpleName().equals("PasswordTransformationMethod")) {
                    password.setTransformationMethod(new SingleLineTransformationMethod());
                } else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }

                password.setSelection(password.getText().length());
            }
        });*/

        Button btnLog = (Button) findViewById(R.id.btnLog);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("") && password.getText().toString().equals("")) {

                    waitingDialog.dismiss();
                    alertDialogManager.showAlertDialog(LoginActivity.this, "Error", "Please fill up the empty field (s)", false);
                    return;
                } else {
                    waitingDialog.show();


                    JSONObject params = new JSONObject();

                    try {
                        params.put("email", username.getText().toString().trim());
                        params.put("password", password.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Make request for JSONObject
                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                            Request.Method.POST, Constant.LOGIN, params,
                            new com.android.volley.Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    waitingDialog.dismiss();
                                    Log.d("TAGGG", response.toString() + " i am queen");
                                    try {
                                        String statusCode = response.getString("statusCode");
                                        Log.d("stat", statusCode);
                                        if (statusCode.equals("200")) {
                                            //alertDialogManager.showAlertDialog(LoginActivity.this, "success", "Account has been created successfully, a mail has been sent to your email address to validate your email address", true);
                                            JSONObject content = response.getJSONObject("data");
                                            UserModel userModel = new UserModel();
                                            if (content != null) {
                                                userModel.setEmail(content.getString("email"));
                                                userModel.setFirstName(content.getString("firstName"));
                                                userModel.setLastName(content.getString("lastName"));
                                                userModel.setId(String.valueOf(content.getString("id")));
                                                userModel.setDealerCode(username.getText().toString().trim());
                                            } //set pin code first
                                            PrefUtils.setCurrentUser(userModel, LoginActivity.this);
                                            PINModel pinCode = PrefUtils.getPinUser(LoginActivity.this);
                                            if (pinCode == null) {
                                                Intent ii = new Intent(LoginActivity.this, SetPinActivity.class);
                                                startActivity(ii);
                                            } else {
                                                PrefUtils.setCurrentUser(userModel, LoginActivity.this);
                                                Intent iii = new Intent(LoginActivity.this, DashboardActivity.class);
                                                startActivity(iii);

                                            }
                                        } else {
                                            alertDialogManager.showAlertDialog(LoginActivity.this, "Failed", "Opps!!! Wrong username and password supplied", false);
                                            username.setText("");
                                            password.setText("");
                                            username.setFocusable(true);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(LoginActivity.this, "Error occurred while vending", Toast.LENGTH_LONG).show();
                            // VolleyLog.d("TAGGG", "Error: " + error.getMessage());
                            // As of f605da3 the following should work
                            waitingDialog.dismiss();
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                    Log.d("error", obj.getString("message"));
                                    Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();

                                } catch (UnsupportedEncodingException e1) {
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace();
                                } catch (JSONException e2) {
                                    // returned data is not JSONObject?
                                    e2.printStackTrace();
                                }
                            }

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

//    public void onLoginClick(View View) {
//        startActivity(new Intent(this, RegisterActivity.class));
//        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
//    }
}