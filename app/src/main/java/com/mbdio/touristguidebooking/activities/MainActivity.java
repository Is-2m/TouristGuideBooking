package com.mbdio.touristguidebooking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.dao.AuthDAO;
import com.mbdio.touristguidebooking.utils.AppStateManager;

public class MainActivity extends AppCompatActivity {
    Button btn_logout;
    TextView txt_hello;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_hello = findViewById(R.id.hello_world);
        String hello="Hello Mr, Ms " + AppStateManager.getCurrentUser().getUserType().name()
                + " " + AppStateManager.getCurrentUser().getLastName().toUpperCase();
        txt_hello.setText(hello);

        btn_logout = findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(view -> {
            AuthDAO.logout();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

}