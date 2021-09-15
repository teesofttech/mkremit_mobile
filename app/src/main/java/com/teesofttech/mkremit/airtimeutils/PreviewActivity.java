package com.teesofttech.mkremit.airtimeutils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.AlertDialogManager;
import com.teesofttech.mkremit.Utils.Constant;
import com.teesofttech.mkremit.Utils.PrefUtils;
import com.teesofttech.mkremit.models.UserModel;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class PreviewActivity extends AppCompatActivity {
    AlertDialogManager alertDialogManager;
    ImageView imageLogo;

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

        TextView vendingCode = findViewById(R.id.vendingCode);
        TextView customer_number = findViewById(R.id.customer_number);
        TextView Amount = findViewById(R.id.Amount);
        TextView vending_status = findViewById(R.id.vending_status);
        TextView customer_name = findViewById(R.id.customer_name);
        TextView pin = findViewById(R.id.pin);
        TextView service = findViewById(R.id.service);
        //TextView quantity_text = findViewById(R.id.quantity_text);
        ImageView logo = findViewById(R.id.logo);
        alertDialogManager = new AlertDialogManager();

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
    }
}