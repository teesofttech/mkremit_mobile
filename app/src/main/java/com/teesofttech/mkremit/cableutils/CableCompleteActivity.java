package com.teesofttech.mkremit.cableutils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.AlertDialogManager;

public class CableCompleteActivity extends AppCompatActivity {

    AlertDialogManager alertDialogManager;
    ImageView imageLogo;
    TextView vendingCode;
    ProgressDialog waitingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cable_complete);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.custom_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView txttitle = (TextView) viewActionBar.findViewById(R.id.txtTitle);
        txttitle.setText("VENDING COMPLETED");
        txttitle.setTextSize(16);
        if (abar != null) {
            abar.setCustomView(viewActionBar, params);
            abar.setDisplayShowCustomEnabled(true);
            abar.setDisplayShowTitleEnabled(false);
            abar.setHomeButtonEnabled(false);
        }

        TextView vendingCode = findViewById(R.id.vendingCode);
        TextView phonenumber = findViewById(R.id.phonenumber);
        TextView Amount = findViewById(R.id.Amount);
        TextView vending_status = findViewById(R.id.vending_status);
        TextView customer_name = findViewById(R.id.customer_name);
        TextView customer_address = findViewById(R.id.customer_addresss);
        TextView service = findViewById(R.id.service);
        TextView plan = findViewById(R.id.plan);
        ImageView logo = findViewById(R.id.logo);
        alertDialogManager = new AlertDialogManager();

        Intent ii = getIntent();
        vendingCode.setText(ii.getStringExtra("reference"));
        Amount.setText("N " + ii.getStringExtra("amount"));
        vending_status.setText("Completed");
        service.setText(ii.getStringExtra("name"));
        plan.setText(ii.getStringExtra("variationCode"));
        phonenumber.setText(ii.getStringExtra("phonenumber"));
        customer_address.setText(ii.getStringExtra("address"));
        customer_name.setText(ii.getStringExtra("customer_Name"));

        String ImageURL = ii.getStringExtra("logo");
        Glide
                .with(this)
                .load(ImageURL)
                .centerCrop()
                .into(logo);
    }
}