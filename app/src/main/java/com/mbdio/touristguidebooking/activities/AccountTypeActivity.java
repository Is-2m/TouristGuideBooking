package com.mbdio.touristguidebooking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.mbdio.touristguidebooking.R;

public class AccountTypeActivity extends AppCompatActivity {

    LinearLayout tourist_card, guide_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);
        tourist_card = findViewById(R.id.tourist_card_register);
        guide_card = findViewById(R.id.guide_card_register);

        tourist_card.setOnClickListener(view -> {
            Intent intent = new Intent(AccountTypeActivity.this, RegisterTouristActivity.class);
            startActivity(intent);
        });

        guide_card.setOnClickListener(view -> {

            Intent intent = new Intent(AccountTypeActivity.this, RegisterGuideActivity.class);
            startActivity(intent);
        });
    }
}