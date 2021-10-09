package com.teesofttech.mkremit.transactionutils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.Constant;
import com.teesofttech.mkremit.Utils.PrefUtils;
import com.teesofttech.mkremit.Utils.RecyclerTouchListener;
import com.teesofttech.mkremit.adapter.TransactionListAdapter;
import com.teesofttech.mkremit.app.AppController;
import com.teesofttech.mkremit.loginutils.LoginActivity;
import com.teesofttech.mkremit.models.TransactionModel;
import com.teesofttech.mkremit.models.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionActivity extends AppCompatActivity {
    private static final String TAG = TransactionActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ArrayList<TransactionModel> cartList;
    private TransactionListAdapter mAdapter;
    String CategoryId;
    ProgressDialog progressDialog;
    UserModel model;
    ImageView imageView;
    TextView go_to_homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView txttitle = (TextView) viewActionBar.findViewById(R.id.txtTitle);
        txttitle.setText("T R A N S A C T I O N S");
        txttitle.setTextSize(14);
        if (abar != null) {
            abar.setCustomView(viewActionBar, params);

            abar.setDisplayShowCustomEnabled(true);
            abar.setDisplayShowTitleEnabled(false);
            abar.setHomeButtonEnabled(false);
        }

        model = PrefUtils.getCurrentUser(TransactionActivity.this);
        if (model == null) {
            startActivity(new Intent(TransactionActivity.this, LoginActivity.class));
            finish();
        } else {
            progressDialog = new ProgressDialog(TransactionActivity.this);
            progressDialog.setMessage("Please wait while we load transactions.");
            progressDialog.setCancelable(false);

            recyclerView = findViewById(R.id.recycle_view);
            cartList = new ArrayList<>();
            mAdapter = new TransactionListAdapter(this, cartList);

            imageView = findViewById(R.id.imageView);
            go_to_homepage = findViewById(R.id.go_to_homepage);

            //  materialSpinner = findViewById(R.id.material_spinner_type);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            String url = Constant.GETTRANSACTIONS + "/" + model.getId();
            fetchDealer(url);
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //final ParcelListModel doc = cartList.get(position);
                    // Intent i = new Intent(ParcelListActivity.this, ParcelCurrentStatus.class);
                    //  i.putExtra("MenuId", doc.getId());
                    // startActivity(i);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

//            FloatingActionButton fab = findViewById(R.id.fab);
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(VendorsActivity.this, CreateVendorActivity.class));
//                }
//            });
        }

    }

    private void fetchDealer(String URL) {
        progressDialog.show();
        cartList.clear();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE", response.toString());
                progressDialog.dismiss();
                try {


                    JSONArray content = response.getJSONArray("data");
                    //categories
                    for (int i = 0; i < content.length(); i++) {
                        JSONObject vend = (JSONObject) content.get(i);
                        TransactionModel category = new TransactionModel();
                        category.setAmount(vend.getString("amount"));
                        category.setBillerCode(vend.getString("billerCode"));
                        category.setReference(vend.getString("reference"));
                        category.setDate(vend.getString("date"));
                        category.setDescription(vend.getString("description"));
                        category.setPaid(vend.getString("paid"));
                        category.setId(vend.getString("id"));
                        category.setSettled(vend.getString("settled"));
                        category.setLogo(vend.getString("logo"));
                        cartList.add(category);
                        mAdapter.notifyDataSetChanged();
                    }
                    // }


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
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Please check your internet connection and retry again", Toast.LENGTH_SHORT).show();
            }


        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                Log.d("TAG", "getHeaders: " + headers.toString());
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}