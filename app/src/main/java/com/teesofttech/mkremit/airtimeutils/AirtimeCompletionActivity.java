package com.teesofttech.mkremit.airtimeutils;

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
import com.teesofttech.mkremit.models.UserModel;

public class AirtimeCompletionActivity extends AppCompatActivity {
    AlertDialogManager alertDialogManager;
    ImageView imageLogo;
    UserModel model;
    ProgressDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airtime_completion);

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
        model = PrefUtils.getCurrentUser(AirtimeCompletionActivity.this);
/*  ii.putExtra("billerCode", content.getString("billerCode"));
                                    ii.putExtra("date", content.getString("date"));
                                    ii.putExtra("reference", content.getString("transactionID"));
                                    ii.putExtra("productType", content.getString("productType"));
                                    ii.putExtra("variationCode", content.getString("variationCode"));
                                    ii.putExtra("amount", content.getString("amount"));
                                    ii.putExtra("paymentMethod", content.getString("paymentMethod"));
                                    ii.putExtra("phonenumber", content.getString("phonenumber"));
                                    ii.putExtra("paymentStatus", content.getString("paymentStatus"));
                                    ii.putExtra("id", content.getString("id"));
                                    ii.putExtra("logo", content.getString("logo"));
                                    ii.putExtra("message", content.getString("message"));
                                    ii.putExtra("name", content.getString("name"));
                                    ii.putExtra("totalAmount", content.getString("totalAmount"));
                                    ii.putExtra("transactionStatus", content.getString("transactionStatus"));*/


        TextView vendingCode = findViewById(R.id.vendingCode);
        TextView network = findViewById(R.id.network);
        TextView Amount = findViewById(R.id.Amount);
        // TextView customer_name = findViewById(R.id.customer_name);
        TextView service = findViewById(R.id.service);
        TextView paymentstatusBind = findViewById(R.id.paymentstatusBind);
        TextView vending_status = findViewById(R.id.vending_status);
        TextView phonenumber = findViewById(R.id.phonenumber);
        TextView dateBind = findViewById(R.id.dateBind);
        ImageView logo = findViewById(R.id.logo);

        Intent ii = getIntent();

        paymentstatusBind.setText("Paid");
        vendingCode.setText(ii.getStringExtra("transactionID"));
        network.setText(ii.getStringExtra("name"));
        service.setText(ii.getStringExtra("service"));
        Amount.setText("₦" + ii.getStringExtra("totalAmount"));
        vending_status.setText(ii.getStringExtra("message"));
        phonenumber.setText(ii.getStringExtra("billerCode"));
        dateBind.setText(ii.getStringExtra("date"));


        String ImageURL = ii.getStringExtra("logo");
        Glide
                .with(this)
                .load(ImageURL)
                .centerCrop()
                .into(logo);
    }
}