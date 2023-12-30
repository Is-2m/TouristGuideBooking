package com.mbdio.touristguidebooking.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mbdio.touristguidebooking.MainActivity;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.dao.AuthCallbacks;
import com.mbdio.touristguidebooking.dao.AuthDAO;
import com.mbdio.touristguidebooking.dao.TouristCallbacks;
import com.mbdio.touristguidebooking.dao.TouristDAO;
import com.mbdio.touristguidebooking.models.Tourist;

public class RegisterTouristActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private TextInputEditText txt_email, txt_fname, txt_lname, txt_password;
    private TextView login_txt;
    private MaterialButton btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tourist);

        mAuth = FirebaseAuth.getInstance();
        btn_register = findViewById(R.id.btn_register_tourist);
        txt_fname = findViewById(R.id.txt_register_fname);
        txt_lname = findViewById(R.id.txt_register_lname);
        txt_password = findViewById(R.id.txt_register_password);
        txt_email = findViewById(R.id.txt_register_email);
        login_txt = findViewById(R.id.login_txt);
        login_txt.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterTouristActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        btn_register.setOnClickListener(view -> {
            String email = txt_email.getText().toString();
            String password = txt_password.getText().toString();
            String fname = txt_fname.getText().toString();
            String lname = txt_lname.getText().toString();
            AuthDAO.register(RegisterTouristActivity.this, email, password, new AuthCallbacks() {
                @Override
                public void onRegisterComplete(FirebaseUser user) {
                    if (user != null) {
                        Tourist tourist = new Tourist(user.getUid(), fname, lname, email);
                        TouristDAO.insertTourist(tourist, new TouristCallbacks() {
                            @Override
                            public void onTouristInserted(boolean success, String message) {
                                if(success){
                                    Intent intent = new Intent(RegisterTouristActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RegisterTouristActivity.this, "Registering failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });
    }
}