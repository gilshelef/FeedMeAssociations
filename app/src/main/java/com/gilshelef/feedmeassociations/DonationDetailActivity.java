package com.gilshelef.feedmeassociations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by gilshe on 2/22/17.
 */
public class DonationDetailActivity extends AppCompatActivity {

    public static final String type = "type";
    public static final String description = "description";
    public static final String imageUrl = "imageUrl";
    public static final String phone = "phone";
    public static final String firstName = "firstName";
    public static final String lastName = "lastName";
    public static final String defaultImage = "defaultImage";
    public static final String date = "date";
    public static final String isAvailable = "available";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail);
        TextView tv = (TextView) findViewById(R.id.donation_page_text);
        tv.setText("this page will show details about the donation");

    }


}
