package com.mbdio.touristguidebooking.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mbdio.touristguidebooking.MainActivity;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.dao.AuthCallbacks;
import com.mbdio.touristguidebooking.dao.AuthDAO;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btn_login;
    private TextInputEditText txt_email, txt_password;
    private TextView register_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        btn_login = findViewById(R.id.btn_login);
        txt_password = findViewById(R.id.txt_login_password);
        txt_email = findViewById(R.id.txt_login_email);
        register_txt = findViewById(R.id.register_txt);


        btn_login.setOnClickListener(view -> {
            String email = txt_email.getText().toString();
            String password = txt_password.getText().toString();

            AuthDAO.login(LoginActivity.this, email, password, new AuthCallbacks() {
                @Override
                public void onLoginComplete(FirebaseUser user) {
                    if (user != null) {
                        Log.d(TAG, "signInWithEmail:success");
                        System.out.println("LoginActivity.onLoginComplete");
                        System.out.println(user.getUid());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });


        register_txt.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, AccountTypeActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            System.out.println("LoginActivity.onStart");
            System.out.println(currentUser.getEmail());
            System.out.println(currentUser.getUid());
            System.out.println(currentUser.getDisplayName());

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }


}