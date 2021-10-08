package com.teesofttech.mkremit.electricityutils;

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

public class ElectricityCompletionActivity extends AppCompatActivity {

    AlertDialogManager alertDialogManager;
    ImageView imageLogo;
    TextView vendingCode;
    ProgressDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_completion);
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

/* ii.putExtra("billerCode", content.getString("billerCode"));
                                        ii.putExtra("date", content.getString("date"));
                                        ii.putExtra("desc", content.getString("description"));
                                        ii.putExtra("email", content.getString("email"));
                                        ii.putExtra("paid", content.getString("paid"));
                                        ii.putExtra("settled", content.getString("settled"));
                                        ii.putExtra("serviceId", content.getString("serviceId"));
                                        ii.putExtra("reference", content.getString("reference"));
                                        ii.putExtra("phonenumber", content.getString("phonenumber"));
                                        ii.putExtra("productType", content.getString("productType"));
                                        ii.putExtra("variationCode", content.getString("variationCode"));
                                        ii.putExtra("amount", content.getString("amount"));
                                        ii.putExtra("paymentMethod", content.getString("paymentMethod"));
                                        ii.putExtra("logo", object.getString("logo"));
                                        ii.putExtra("message", content.getString("message"));
                                        ii.putExtra("name", object.getString("name"));
                                        ii.putExtra("totalamount", content.getString("totalamount"));
                                        ii.putExtra("message", object.getString("message"));
                                        */
        TextView vendingCode = findViewById(R.id.vendingCode);
        TextView customer_number = findViewById(R.id.customer_number);
        TextView Amount = findViewById(R.id.Amount);
        TextView vending_status = findViewById(R.id.vending_status);
        TextView phonenumber = findViewById(R.id.customer_name);
        TextView customerName = findViewById(R.id.customerName_Bind);
        TextView customer_address = findViewById(R.id.customerAddress_Bind);
        TextView productType = findViewById(R.id.productType);
        TextView totalamount = findViewById(R.id.totalamount);
        TextView paymentMethod = findViewById(R.id.paymentMethod);

        TextView pin = findViewById(R.id.pin);
        TextView service = findViewById(R.id.service);
        //TextView quantity_text = findViewById(R.id.quantity_text);
        ImageView logo = findViewById(R.id.logo);
        alertDialogManager = new AlertDialogManager();

        Intent ii = getIntent();
        vendingCode.setText(ii.getStringExtra("billerCode"));
        phonenumber.setText(ii.getStringExtra("phonenumber"));
        customer_number.setText(ii.getStringExtra("reference"));
        Amount.setText("N " + ii.getStringExtra("amount"));
        service.setText(ii.getStringExtra("name"));
        customerName.setText(ii.getStringExtra("customer_Name"));
        customer_address.setText(ii.getStringExtra("address"));
        productType.setText(ii.getStringExtra("productType"));
        totalamount.setText("N " + ii.getStringExtra("totalamount"));
        paymentMethod.setText(ii.getStringExtra("paymentMethod"));

        String Paid_Settled = ii.getStringExtra("settled");
        if (Paid_Settled.equals("1")) {
            vending_status.setText("Completed");
        }
        String ImageURL = ii.getStringExtra("logo");
        Glide
                .with(this)
                .load(ImageURL)
                .centerCrop()
                .into(logo);

        UserModel model = PrefUtils.getCurrentUser(ElectricityCompletionActivity.this);
    }
}