package com.teesofttech.mkremit.datautils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
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
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.AlertDialogManager;
import com.teesofttech.mkremit.airtimeutils.AirtimeActivity;
import com.teesofttech.mkremit.app.AppController;
import com.teesofttech.mkremit.models.ServiceVendor;
import com.teesofttech.mkremit.models.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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
    //private ServiceVendorAdapter mAdapter;
    //private List<VendorModel> vendorModelList;
    //String URL;
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
        material_spinner_type.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });


        ACProgressFlower dialog = new ACProgressFlower.Builder(InternetDataActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait...")
                .fadeColor(Color.DKGRAY).build();


    }

    private void fetchRecipes(String item) {
        progressDialog.setMessage("Loading");
        progressDialog.show();
        cartList = new ArrayList<>();
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

                    for (int i = 0; i < content.length(); i++) {

                        JSONObject vend = (JSONObject) content
                                .get(i);

//                        ServiceVendor vendor = new ServiceVendor();
//                        vendor.setId(vend.getString("id"));
//                        vendor.setName(vend.getString("name"));
                        ServiceVendor mydic = new ServiceVendor();
                        mydic.setId(vend.getString("id"));
                        mydic.setName(vend.getString("name"));
                        mydic.setVariationAmount(vend.getString("amount"));
                        cartList.add(mydic);
                        vendorname.add(vend.getString("name"));

                        //ArrayAdapter userAdapter = new ArrayAdapter(this, R.layout.spinner, cartList.toArray());

                        material_spinner_type.setAdapter(new ArrayAdapter<ServiceVendor>(InternetDataActivity.this, android.R.layout.simple_spinner_dropdown_item, cartList));

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