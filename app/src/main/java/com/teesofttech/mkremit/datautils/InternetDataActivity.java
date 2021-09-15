package com.teesofttech.mkremit.datautils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.Utils.AlertDialogManager;
import com.teesofttech.mkremit.airtimeutils.AirtimeActivity;
import com.teesofttech.mkremit.models.UserModel;

import java.util.ArrayList;

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
    //private List<ServiceVendor> cartList;
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

        alertDialogManager = new AlertDialogManager();

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
            }
        });
        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

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
}