package com.mbdio.touristguidebooking.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        TextView historyV = findViewById(R.id.history);
        LinearLayout back_arrow = findViewById(R.id.back_arrow);

        nameTextView.setText(name);
        villeTextView.setText(ville);
        adressTextView.setText(address);
        historyV.setText(history);
        Glide.with(this).load(image).into(imageView);


        back_arrow.setOnClickListener(v -> {
            goBackHome();
        });

        adressTextView.setOnClickListener(v -> {
            String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + name + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            ItemActivity.this.startActivity(intent);

        });

    }

    private void goBackHome() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

