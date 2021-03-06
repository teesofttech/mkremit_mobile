package com.teesofttech.mkremit.datautils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.libizo.CustomEditText;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.AlertDialogManager;
import com.teesofttech.mkremit.Utils.Constant;
import com.teesofttech.mkremit.Utils.PrefUtils;
import com.teesofttech.mkremit.airtimeutils.AirtimeActivity;
import com.teesofttech.mkremit.airtimeutils.PreviewActivity;
import com.teesofttech.mkremit.app.AppController;
import com.teesofttech.mkremit.models.ServiceVendor;
import com.teesofttech.mkremit.models.UserModel;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class InternetDataActivity extends AppCompatActivity {
    private static final String[] Airitime = {
            "Select One",
            "MTN",
            "GLO",
            "Airtel",
            "9Mobile"
    };
    AlertDialogManager alertDialogManager;
    ImageView image;


    private static final String TAG = AirtimeActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<ServiceVendor> cartList;
    private SearchView search;
    ProgressDialog progressDialog;
    String config_url;
    //AwesomeSpinner my_spinner,my_spinner2;
    String network, item2;
    UserModel model;
    ArrayList<String> vendorname;
    //ArrayList<vendModel> ServiceType;
    Spinner materialSpinner, materialSpinnerType;
    com.jaredrummler.materialspinner.MaterialSpinner material_spinner_type;
    String plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_data);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.custom_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView txttitle = (TextView) viewActionBar.findViewById(R.id.txtTitle);
        txttitle.setText("I N T E R N E T");
        txttitle.setTextSize(14);
        if (abar != null) {
            abar.setCustomView(viewActionBar, params);

            abar.setDisplayShowCustomEnabled(true);
            abar.setDisplayShowTitleEnabled(false);
            abar.setHomeButtonEnabled(false);
        }
        com.jaredrummler.materialspinner.MaterialSpinner spinner = (com.jaredrummler.materialspinner.MaterialSpinner) findViewById(R.id.material_spinner_1);
        spinner.setItems(Airitime);
        image = findViewById(R.id.image);
        progressDialog = new ProgressDialog(this);
        alertDialogManager = new AlertDialogManager();
        vendorname = new ArrayList<>();
        material_spinner_type = (com.jaredrummler.materialspinner.MaterialSpinner) findViewById(R.id.material_spinner_type);
        cartList = new ArrayList<>();
        model = PrefUtils.getCurrentUser(InternetDataActivity.this);
        com.libizo.CustomEditText amount = (com.libizo.CustomEditText) findViewById(R.id.amount);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                network = item;
                if (item.equals("MTN")) {
                    image.setImageResource(R.mipmap.mtnlogo);
                }
                if (item.equals("GLO")) {
                    image.setImageResource(R.mipmap.glologo);
                }
                if (item.equals("Airtel")) {
                    image.setImageResource(R.mipmap.airtel);
                }
                if (item.equals("9Mobile")) {
                    image.setImageResource(R.mipmap.ninemobile);
                }

                //fetch the corresponding services
                String netData = network.toLowerCase() + "-data".toLowerCase();
                fetchRecipes(netData);

            }
        });
        material_spinner_type.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                //cartList.
                if (cartList.contains(item)) {
                    ServiceVendor employee = (ServiceVendor) item;
                    amount.setText(employee.variationAmount);
                    plan = employee.variationCode;
                    //Log.d("pla", employee.variationCode);
                }
            }
        });


        ACProgressFlower dialog = new ACProgressFlower.Builder(InternetDataActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait...")
                .fadeColor(Color.DKGRAY).build();

        Button btnContinue = findViewById(R.id.btnContinue);
        CustomEditText phonenumber = findViewById(R.id.phonenumber);
        //  CustomEditText amount = findViewById(R.id.amount);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _phonenumber = phonenumber.getText().toString();
                String Amount = amount.getText().toString();

                // dialog.show();

                if (phonenumber.getText().toString().equals("") && amount.getText().toString().equals("")) {
                    alertDialogManager.showAlertDialog(InternetDataActivity.this, "Error", "Please fill up the empty field (s)", false);
                } else {
                    JSONObject param = new JSONObject();

                    try {
                        param.put("network", network);
                        param.put("phoneNumber", _phonenumber.trim());
                        param.put("amount", Amount.trim());
                        param.put("email", model.getEmail());
                        param.put("dataPlan", plan);
                        param.put("userId", model.getId());
                        Log.d("param", param.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.show();
                    // Make request for JSONObject
                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                            Request.Method.POST, Constant.VENDING_DATA, param,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("RESPONSE", response.toString());
                                    dialog.dismiss();
                                    try {
                                        String statusCode = response.getString("statusCode");
                                        Log.d("stat", statusCode);
                                        if (statusCode.equals("200")) {
                                            //alertDialogManager.showAlertDialog(RechargeVendorsActivity.this, "Success", response.getString("message"), true);
                                            Toast.makeText(InternetDataActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();

                                            JSONObject cont = response.getJSONObject("data");
                                            JSONObject content = cont.getJSONObject("transaction");

                                            JSONObject serviceProper = cont.getJSONObject("service");
                                            Intent ii = new Intent(InternetDataActivity.this, PreviewInternetActivity.class);
                                            ii.putExtra("serviceId", content.getString("serviceId"));
                                            ii.putExtra("date", content.getString("date"));
                                            ii.putExtra("reference", content.getString("reference"));
                                            ii.putExtra("productType", content.getString("productType"));
                                            ii.putExtra("variationCode", content.getString("variationCode"));
                                            ii.putExtra("amount", content.getString("amount"));
                                            ii.putExtra("paymentMethod", content.getString("paymentMethod"));
                                            ii.putExtra("phonenumber", content.getString("phonenumber"));
                                            ii.putExtra("paymentStatus", content.getString("paymentStatus"));
                                            ii.putExtra("id", content.getString("id"));
                                            ii.putExtra("totalamount", content.getString("totalamount"));

                                            //serivce
                                            ii.putExtra("Sname", serviceProper.getString("name"));
                                            ii.putExtra("Simage", serviceProper.getString("image"));
                                            ii.putExtra("SserviceId", serviceProper.getString("serviceId"));
                                            ii.putExtra("ScategoryId", serviceProper.getString("categoryId"));

                                            startActivity(ii);

                                        } else {
                                            //alertDialogManager.showAlertDialog(InternetDataActivity.this, "Failed", "Initialization failed", false);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            if (error instanceof NoConnectionError) {
                                ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo activeNetwork = null;
                                if (cm != null) {
                                    activeNetwork = cm.getActiveNetworkInfo();
                                }
                                if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                                    Toast.makeText(getApplicationContext(), "Server is not connected to internet.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Your device is not connected to internet.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
                                    || (error.getCause().getMessage() != null
                                    && error.getCause().getMessage().contains("connection"))) {
                                Toast.makeText(getApplicationContext(), "Your device is not connected to internet.",
                                        Toast.LENGTH_SHORT).show();
                            } else if (error.getCause() instanceof MalformedURLException) {
                                Toast.makeText(getApplicationContext(), "Bad Request.", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
                                    || error.getCause() instanceof JSONException
                                    || error.getCause() instanceof XmlPullParserException) {
                                Toast.makeText(getApplicationContext(), "Parse Error (because of invalid json or xml).",
                                        Toast.LENGTH_SHORT).show();
                            } else if (error.getCause() instanceof OutOfMemoryError) {
                                Toast.makeText(getApplicationContext(), "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getApplicationContext(), "server couldn't find the authenticated request.",
                                        Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
                                Toast.makeText(getApplicationContext(), "Server is not responding.", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
                                    || error.getCause() instanceof ConnectTimeoutException
                                    || error.getCause() instanceof SocketException
                                    || (error.getCause().getMessage() != null
                                    && error.getCause().getMessage().contains("Connection timed out"))) {
                                Toast.makeText(getApplicationContext(), "Connection timeout error",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "An unknown error occurred.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            VolleyLog.d("TAGGG", "Error: " + error.getMessage());
                            // As of f605da3 the following should work
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                    Log.d("error", obj.getString("message"));
                                    Toast.makeText(InternetDataActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
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

                    };

                    DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    jsonObjReq.setRetryPolicy(retryPolicy);
                    AppController.getInstance().addToRequestQueue(jsonObjReq);

                }
            }
        });

    }

    private void fetchRecipes(String item) {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        cartList.clear();
        String Url = "http://api.mkremit.com/api/Utility/get-data-plan/" + item;
        Log.d("url", Url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    // Parsing json object response
                    // response will be a json object
                    Log.d("Tagg", response.toString());
                    String name = response.getString("message");
                    String email = response.getString("statusCode");
                    JSONArray content = response.getJSONArray("data");
                    ServiceVendor de = new ServiceVendor();
                    de.setId("1");
                    de.setName("Select One");
                    de.setVariationAmount("0");
                    cartList.add(de);
                    for (int i = 0; i < content.length(); i++) {

                        JSONObject vend = (JSONObject) content
                                .get(i);

                        ServiceVendor mydic = new ServiceVendor();
                        mydic.setId(vend.getString("id"));
                        mydic.setName(vend.getString("name"));
                        mydic.setVariationAmount(vend.getString("variationAmount"));
                        mydic.setVariationCode(vend.getString("variationCode"));
                        cartList.add(mydic);
                        vendorname.add(vend.getString("name"));

                        ArrayAdapter userAdapter = new ArrayAdapter(InternetDataActivity.this, R.layout.spinner, cartList);
                        material_spinner_type.setAdapter(userAdapter);
                        ;//new ArrayAdapter<ServiceVendor>(InternetDataActivity.this, android.R.layout.simple_spinner_dropdown_item, cartList));

                        /*JSONArray services = vend.getJSONArray("serivces");

                        for (int k = 0; k < services.length(); k++) {

                            try {
                                final JSONObject obj = (JSONObject) services
                                        .get(k);

                                ServiceVendor mydic = new ServiceVendor();
                                mydic.setId(obj.getInt("id"));
                                mydic.setVendorName(obj.getString("vendorName"));
                                mydic.setName(obj.getString("name"));
                                mydic.setAmount(obj.getDouble("amount"));
                                mydic.setDescription(obj.getString("description"));
                                cartList.add(mydic);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }*/
                        //  mAdapter.notifyDataSetChanged();

                    }
                    // stop animating Shimmer and hide the layout

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InternetDataActivity.this, "Error occurred while vending", Toast.LENGTH_LONG).show();
                VolleyLog.d("TAGGG", "Error: " + error.getMessage());
                // As of f605da3 the following should work
                progressDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                        Log.d("error", obj.getString("message"));
                        Toast.makeText(InternetDataActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();

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

        };

        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(retryPolicy);
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
}