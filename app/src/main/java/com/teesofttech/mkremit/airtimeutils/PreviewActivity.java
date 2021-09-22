package com.teesofttech.mkremit.airtimeutils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.AlertDialogManager;
import com.teesofttech.mkremit.Utils.Constant;
import com.teesofttech.mkremit.Utils.PrefUtils;
import com.teesofttech.mkremit.app.AppController;
import com.teesofttech.mkremit.auth.SetPinActivity;
import com.teesofttech.mkremit.dashboard.DashboardActivity;
import com.teesofttech.mkremit.loginutils.LoginActivity;
import com.teesofttech.mkremit.models.PINModel;
import com.teesofttech.mkremit.models.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class PreviewActivity extends AppCompatActivity {
    AlertDialogManager alertDialogManager;
    ImageView imageLogo;
    UserModel model;
    TextView vendingCode;
    ProgressDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.custom_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView txttitle = (TextView) viewActionBar.findViewById(R.id.txtTitle);
        txttitle.setText("V E N D I N G  I N F O R M A T I O N");
        txttitle.setTextSize(18);
        if (abar != null) {
            abar.setCustomView(viewActionBar, params);
            abar.setDisplayShowCustomEnabled(true);
            abar.setDisplayShowTitleEnabled(false);
            abar.setHomeButtonEnabled(false);
        }

        vendingCode = findViewById(R.id.vendingCode);
        TextView customer_number = findViewById(R.id.customer_number);
        TextView Amount = findViewById(R.id.Amount);
        TextView vending_status = findViewById(R.id.vending_status);
        TextView customer_name = findViewById(R.id.customer_name);
        TextView pin = findViewById(R.id.pin);
        TextView service = findViewById(R.id.service);
        //TextView quantity_text = findViewById(R.id.quantity_text);
        ImageView logo = findViewById(R.id.logo);
        alertDialogManager = new AlertDialogManager();
        model = PrefUtils.getCurrentUser(PreviewActivity.this);
        Intent ii = getIntent();
        vendingCode.setText(ii.getStringExtra("reference"));
        customer_number.setText(ii.getStringExtra("serviceId"));
        Amount.setText(ii.getStringExtra("amount"));
        vending_status.setText(ii.getStringExtra("paymentStatus"));
        service.setText(ii.getStringExtra("Sname"));
        customer_name.setText(ii.getStringExtra("phonenumber"));

        String ImageURL = ii.getStringExtra("Simage");
        Glide
                .with(this)
                .load(ImageURL)
                .centerCrop()
                .into(logo);

        UserModel model = PrefUtils.getCurrentUser(PreviewActivity.this);
        ACProgressFlower dialog = new ACProgressFlower.Builder(PreviewActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait...")
                .fadeColor(Color.DKGRAY).build();

        Button btnWallet = findViewById(R.id.btnViaWallet);
        Button btnCard = findViewById(R.id.btnViaCard);
        Button btnViaBitcoin = findViewById(R.id.btnViaBitcoin);

        btnWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new RaveUiManager(PreviewActivity.this).setAmount(Double.parseDouble(Amount.getText().toString()))
                        .setCurrency("NGN")
                        .setEmail(model.getEmail())
                        .setfName(model.getFirstName())
                        .setlName(model.getLastName())
                        .setNarration("Payment for Airtime")
                        .setPublicKey("FLWPUBK_TEST-b0c423ccc7b223945935894234caf5c4-X")
                        .setEncryptionKey("FLWSECK_TESTe6e7ef1d8b5e")
                        .setTxRef(GetREF())
                        .acceptAccountPayments(false)
                        .acceptCardPayments(true)
                        .acceptMpesaPayments(true)
                        .allowSaveCardFeature(true)
                        .onStagingEnv(false)
                        .withTheme(R.style.AppTheme)
                        .isPreAuth(false)
                        .shouldDisplayFee(true)
                        .showStagingLabel(true)
                        .initialize();

            }
        });

        btnViaBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        waitingDialog = new ProgressDialog(this);
        waitingDialog.setTitle("Vending...");
        waitingDialog.setMessage("Please wait while we vending your account");

    }

    public String GetREF() {
        Long max = 0L;
        Long min = 100000000000L;
        //Use the date format that best suites you
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
        }
        Long randomLong = 0L;
        for (int i = 0; i <= 10; i++) {
            Random r = new Random();
            randomLong = (r.nextLong() % (max - min)) + min;
            Date dt = new Date(randomLong);
        }
        return randomLong.toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Log.d("FLUTTER", message);
                waitingDialog.show();
                try {
                    JSONObject object = new JSONObject(message);
                    final JSONObject arr = object.getJSONObject("data");

                    final String amount = arr.getString("amount");
                    final String IP = arr.getString("IP");
                    final String appfee = arr.getString("appfee");
                    final String charged_amount = arr.getString("charged_amount");
                    final String currency = arr.getString("currency");
                    final String flwRef = arr.getString("flwRef");
                    final String narration = arr.getString("narration");
                    final String orderRef = arr.getString("orderRef");
                    final String raveRef = arr.getString("raveRef");
                    final String txRef = arr.getString("txRef");
                    final String vbRespcode = arr.getString("vbvrespcode");
                    final String vbrespmessage = arr.getString("vbvrespmessage");

                    String vendingComplete = Constant.VENDING_AIRTIME_COMPLETE + "/" + vendingCode.getText().toString();
                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                            vendingComplete, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //dialog.show();
                            Log.d("BANK", response.toString());
                            waitingDialog.dismiss();

                            try {
                                JSONObject object = response.getJSONObject("data");
                                String statusCode = response.getString("statusCode");
                                if (statusCode.equals("200")) {
                                    JSONObject content = object.getJSONObject("standardReportingModel");
                                    Intent ii = new Intent(PreviewActivity.this, AirtimeCompletionActivity.class);
                                    ii.putExtra("billerCode", content.getString("billerCode"));
                                    ii.putExtra("date", content.getString("date"));
                                    ii.putExtra("transactionID", content.getString("transactionID"));
                                    ii.putExtra("productType", content.getString("productType"));
                                    ii.putExtra("variationCode", content.getString("variationCode"));
                                    ii.putExtra("amount", content.getString("amount"));
                                    ii.putExtra("paymentMethod", content.getString("paymentMethod"));
                                    ii.putExtra("logo", content.getString("logo"));
                                    ii.putExtra("message", content.getString("message"));
                                    ii.putExtra("name", content.getString("name"));
                                    ii.putExtra("totalAmount", content.getString("totalAmount"));
                                    ii.putExtra("transactionStatus", content.getString("transactionStatus"));
                                    ii.putExtra("status", content.getBoolean("status"));
                                    ii.putExtra("service", content.getString("service"));
                                    ii.putExtra("message", content.getString("message"));
                                    startActivity(ii);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // progressDialog.dismiss();
                            waitingDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PreviewActivity.this, "Error occurred while vending", Toast.LENGTH_LONG).show();
                            VolleyLog.d("TAGGG", "Error: " + error.getMessage());
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
                                    Toast.makeText(PreviewActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();

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
                            // headers.put("Authorization", "Bearer " + model.getToken());
                            Log.d("TAG", "getHeaders: " + headers.toString());
                            return headers;
                        }
                    };
                    DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    jsonObjReq.setRetryPolicy(retryPolicy);
                    AppController.getInstance().addToRequestQueue(jsonObjReq);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}