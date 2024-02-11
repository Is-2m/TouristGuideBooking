package com.mbdio.touristguidebooking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.dao.AuthCallbacks;
import com.mbdio.touristguidebooking.dao.AuthDAO;
import com.mbdio.touristguidebooking.dao.GuideCallbacks;
import com.mbdio.touristguidebooking.dao.GuideDAO;
import com.mbdio.touristguidebooking.models.Guide;

public class RegisterGuideActivity extends AppCompatActivity {


    private TextInputEditText txt_email, txt_fname, txt_lname, txt_password;
    private TextView login_txt;
    private MaterialButton btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_guide);


        btn_register = findViewById(R.id.btn_register);
        txt_fname = findViewById(R.id.register_fname);
        txt_lname = findViewById(R.id.register_lname);
        txt_password = findViewById(R.id.register_password);
        txt_email = findViewById(R.id.register_email);
        login_txt = findViewById(R.id.login_txt);

        login_txt.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterGuideActivity.this, LoginActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        btn_register.setOnClickListener(view -> {
            String email = txt_email.getText().toString();
            String password = txt_password.getText().toString();
            String fname = txt_fname.getText().toString();
            String lname = txt_lname.getText().toString();

            AuthDAO.register(RegisterGuideActivity.this, email, password, new AuthCallbacks() {
                @Override
                public void onRegisterComplete(FirebaseUser user) {
                    if (user != null) {
                        Guide guide = new Guide(user.getUid(), fname, lname, email);

                        GuideDAO.insertGuide(guide, new GuideCallbacks() {

                            public void onGuideInserted(boolean success, String message) {
                                if (success) {
                                    Intent intent = new Intent(RegisterGuideActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RegisterGuideActivity.this, "Registering failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });
    }
}