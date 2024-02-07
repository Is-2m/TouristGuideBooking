package com.mbdio.touristguidebooking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.mbdio.touristguidebooking.R;

public class EmergencyNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_number);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void callEmergencyNumber(View view) {
        String emergencyNumber = (String) view.getTag();
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + emergencyNumber));
        startActivity(dialIntent);}
}