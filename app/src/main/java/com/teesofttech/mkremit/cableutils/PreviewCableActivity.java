package com.teesofttech.mkremit.cableutils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.AlertDialogManager;
import com.teesofttech.mkremit.Utils.PrefUtils;
import com.teesofttech.mkremit.electricityutils.PreviewElectricityActivity;
import com.teesofttech.mkremit.models.UserModel;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class PreviewCableActivity extends AppCompatActivity {
    AlertDialogManager alertDialogManager;
    ImageView imageLogo;
    TextView vendingCode;
    ProgressDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_cable);
        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.custom_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView txttitle = (TextView) viewActionBar.findViewById(R.id.txtTitle);
        txttitle.setText("V E N D I N G  I N F O R M A T I O N");
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
        vending_status.setText(ii.getStringExtra("paymentStatus"));
        service.setText(ii.getStringExtra("Sname"));
        plan.setText(ii.getStringExtra("variationCode"));
        phonenumber.setText(ii.getStringExtra("phonenumber"));
        customer_address.setText(ii.getStringExtra("address"));
        customer_name.setText(ii.getStringExtra("customer_Name"));

        String ImageURL = ii.getStringExtra("Simage");
        Glide
                .with(this)
                .load(ImageURL)
                .centerCrop()
                .into(logo);

        UserModel model = PrefUtils.getCurrentUser(PreviewCableActivity.this);
        ACProgressFlower dialog = new ACProgressFlower.Builder(PreviewCableActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait...")
                .fadeColor(Color.DKGRAY).build();


        Button btnWallet = findViewById(R.id.btnViaWallet);
        Button btnCard = findViewById(R.id.btnViaCard);
        Button btnViaBitcoin = findViewById(R.id.btnViaBitcoin);
    }
}