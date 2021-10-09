package com.teesofttech.mkremit.educationutils;

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
import com.teesofttech.mkremit.Utils.PrefUtils;
import com.teesofttech.mkremit.airtimeutils.AirtimeCompletionActivity;
import com.teesofttech.mkremit.models.UserModel;

public class EducationCompleteActivity extends AppCompatActivity {
    AlertDialogManager alertDialogManager;
    ImageView imageLogo;
    UserModel model;
    ProgressDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_complete);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.custom_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView txttitle = (TextView) viewActionBar.findViewById(R.id.txtTitle);
        txttitle.setText("V E N D I N G   C O M P L E T I O N");
        txttitle.setTextSize(18);
        if (abar != null) {
            abar.setCustomView(viewActionBar, params);
            abar.setDisplayShowCustomEnabled(true);
            abar.setDisplayShowTitleEnabled(false);
            abar.setHomeButtonEnabled(false);
        }
        alertDialogManager = new AlertDialogManager();
        model = PrefUtils.getCurrentUser(EducationCompleteActivity.this);


        TextView vendingCode = findViewById(R.id.vendingCode);
        TextView network = findViewById(R.id.network);
        TextView Amount = findViewById(R.id.Amount);
        TextView service = findViewById(R.id.service);
        TextView paymentstatusBind = findViewById(R.id.paymentstatusBind);
        TextView vending_status = findViewById(R.id.vending_status);
        TextView phonenumber = findViewById(R.id.phonenumber);
        TextView dateBind = findViewById(R.id.dateBind);
        ImageView logo = findViewById(R.id.logo);

        Intent ii = getIntent();

        vendingCode.setText(ii.getStringExtra("transactionID"));
        network.setText(ii.getStringExtra("extras"));
        service.setText(ii.getStringExtra("name"));
        Amount.setText("₦" + ii.getStringExtra("totalAmount"));
        vending_status.setText(ii.getStringExtra("message"));
        phonenumber.setText(ii.getStringExtra("billerCode"));
        dateBind.setText(ii.getStringExtra("date"));

        paymentstatusBind.setText(ii.getStringExtra("paymentMethod"));

        String ImageURL = ii.getStringExtra("logo");
        Glide
                .with(this)
                .load(ImageURL)
                .centerCrop()
                .into(logo);

    }
}