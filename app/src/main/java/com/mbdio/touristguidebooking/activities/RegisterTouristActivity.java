package com.mbdio.touristguidebooking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.dao.AuthCallbacks;
import com.mbdio.touristguidebooking.dao.AuthDAO;
import com.mbdio.touristguidebooking.dao.TouristCallbacks;
import com.mbdio.touristguidebooking.dao.TouristDAO;
import com.mbdio.touristguidebooking.dao.UserCallbacks;
import com.mbdio.touristguidebooking.dao.UserDAO;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.User;
import com.mbdio.touristguidebooking.models.UserType;
import com.mbdio.touristguidebooking.utils.AppStateManager;

public class RegisterTouristActivity extends AppCompatActivity {


    private TextInputEditText txt_email, txt_fname, txt_lname, txt_password;
    private TextView login_txt;
    private MaterialButton btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tourist);

        btn_register = findViewById(R.id.btn_register_guide);
        txt_fname = findViewById(R.id.register_guide_fname);
        txt_lname = findViewById(R.id.register_guide_lname);
        txt_password = findViewById(R.id.register_guide_password);
        txt_email = findViewById(R.id.register_guide_email);
        login_txt = findViewById(R.id.login_txt);
        login_txt.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterTouristActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
                                if (success) {
                                    Intent intent = new Intent(RegisterTouristActivity.this, LandingActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
////        getCurrentLocation();
//
//        AuthDAO.checkIsLogged(new AuthCallbacks() {
//            @Override
//            public void onCheckLoggedComplete(FirebaseUser fireUser) {
//                if (fireUser != null) {
//                    AppStateManager.setCurrentFireUser(fireUser);
//                    User currentUser = AppStateManager.getCurrentUser();
//                    if (currentUser != null) {
//                        if (currentUser.getUserType() == UserType.GUIDE) {
//                            LandingActivity.getGuideBookings(currentUser.getUserID());
//                        } else {
//                            LandingActivity.getTouristBookings((Tourist) currentUser);
//                        }
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            public void run() {
//                                LandingActivity.redirectToMainActivity(RegisterTouristActivity.this, currentUser);
//                            }
//                        }, LandingActivity.DELAY);
//                    } else {
//                        UserDAO.getUser(fireUser.getUid(), new UserCallbacks() {
//                            @Override
//                            public void onGetUser(User user) {
//                                if (user != null) {
//                                    AppStateManager.setCurrentUser(user);
//                                    if (user.getUserType() == UserType.GUIDE) {
//                                        LandingActivity.getGuideBookings(user.getUserID());
//                                    } else {
//                                        LandingActivity.getTouristBookings((Tourist) user);
//                                    }
//                                    Handler handler = new Handler();
//                                    handler.postDelayed(new Runnable() {
//                                        public void run() {
//                                            LandingActivity.redirectToMainActivity(RegisterTouristActivity.this, user);
//                                        }
//                                    }, LandingActivity.DELAY);
//                                }
//                            }
//                        });
//                    }
//                }
//            }
//        });
//    }
}