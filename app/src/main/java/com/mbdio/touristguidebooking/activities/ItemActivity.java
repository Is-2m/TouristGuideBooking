package com.mbdio.touristguidebooking.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.mbdio.touristguidebooking.R;

import java.util.Locale;

public class ItemActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_VILLE = "ville";
    public static final String EXTRA_Address = "address";
    public static final String EXTRA_PURL = "image";
    public static final String EXTRA_HIST = "history";
    public static final String EXTRA_LAT = "latitude";
    public static final String EXTRA_LONG = "longitude";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get data from Intent
        String name = getIntent().getStringExtra(EXTRA_NAME);
        String ville = getIntent().getStringExtra(EXTRA_VILLE);
        String address = getIntent().getStringExtra(EXTRA_Address);
        String image = getIntent().getStringExtra(EXTRA_PURL);
        String history = getIntent().getStringExtra(EXTRA_HIST);
        String longitude = getIntent().getStringExtra(EXTRA_LONG);
        String latitude = getIntent().getStringExtra(EXTRA_LAT);

        // Set data to views
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView villeTextView = findViewById(R.id.VilleTextView);
        TextView adressTextView = findViewById(R.id.textaddress);
        ImageView imageView = findViewById(R.id.imageView);
        TextView historyV= findViewById(R.id.history);

        nameTextView.setText(name);
        villeTextView.setText(ville);
        adressTextView.setText(address);
        historyV.setText(history);
        Glide.with(this).load(image).into(imageView);

        adressTextView.setOnClickListener(v -> {

            String addr = adressTextView.getText().toString();
            System.out.println("ItemActivity.onCreate1");
            System.out.println("ItemActivity.onCreate4");
            System.out.println(latitude);
            System.out.println(longitude);
//            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
            String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + name + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            ItemActivity.this.startActivity(intent);
            // Check if the addr is not empty
//            if (!addr.isEmpty()) {
//                System.out.println("ItemActivity.onCreate2");
//
//                // Intent for Google Maps app
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//
//                System.out.println("ItemActivity.onCreate4");
//                System.out.println(latitude);
//                System.out.println(longitude);
//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
//                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                ItemActivity.this.startActivity(intent);
//                // Use a browser intent as a backup
//                Uri webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(addr));
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, webUri);
//                // Check if Google Maps app is available
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    System.out.println("ItemActivity.onCreate3");
//
//                    startActivity(intent);
//                } else {
//
//
//                    // Check if a browser is available
//                    if (browserIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivity(browserIntent);
//                    } else {
//                        Toast.makeText(ItemActivity.this, "No application available to handle Maps.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            } else {
//                Toast.makeText(ItemActivity.this, "Address is empty.", Toast.LENGTH_SHORT).show();
//            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();}
        return super.onOptionsItemSelected(item);
    }
}

