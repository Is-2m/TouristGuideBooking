package com.mbdio.touristguidebooking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.dao.AuthCallbacks;
import com.mbdio.touristguidebooking.dao.AuthDAO;
import com.mbdio.touristguidebooking.dao.BookingCallbacks;
import com.mbdio.touristguidebooking.dao.BookingDAO;
import com.mbdio.touristguidebooking.dao.UserCallbacks;
import com.mbdio.touristguidebooking.dao.UserDAO;
import com.mbdio.touristguidebooking.models.Booking;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.User;
import com.mbdio.touristguidebooking.models.UserType;
import com.mbdio.touristguidebooking.utils.AppStateManager;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private TextInputEditText txt_email, txt_password;
    private TextView register_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
                        onStart();
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
        AuthDAO.checkIsLogged(new AuthCallbacks() {
            @Override
            public void onCheckLoggedComplete(FirebaseUser fireUser) {

                if (fireUser != null) {
                    AppStateManager.setCurrentFireUser(fireUser);
                    User currentUser = AppStateManager.getCurrentUser();
                    if (currentUser != null) {
                        if (AppStateManager.getBookingHistory().isEmpty() || AppStateManager.getBookingHistory() == null) {
                            if (currentUser.getUserType() == UserType.TOURIST) {
                                getTouristBookings((Tourist) currentUser);
                            } else {
                                getGuideBookings(currentUser.getUserID());
                            }
                        }
                        redirectToMainActivity(currentUser);
                    } else {
                        UserDAO.getUser(fireUser.getUid(), new UserCallbacks() {
                            @Override
                            public void onGetUser(User user) {
                                if (user != null) {
                                    AppStateManager.setCurrentUser(user);
                                    if (AppStateManager.getBookingHistory() == null || AppStateManager.getBookingHistory().isEmpty()) {
                                        if (user.getUserType() == UserType.TOURIST) {
                                            getTouristBookings((Tourist) user);
                                        } else {
                                            getGuideBookings(user.getUserID());
                                        }
                                    }
                                    redirectToMainActivity(user);
                                }
                            }
                        });
                    }
                }
            }
        });
    }


    //this code was duplicated so i made it into a method
    void redirectToMainActivity(User user) {

        if (user.getUserType() == UserType.TOURIST) {
            //Redirect to Tourist's home page
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (user.getUserType() == UserType.GUIDE) {

            //Redirect to Guide's home page, something like 👇👇
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    void getTouristBookings(Tourist t) {
        ArrayList<Booking> lstBooking = new ArrayList<>();
        if (t.getListBookings() != null && !t.getListBookings().isEmpty())
            for (String s : t.getListBookings()) {
                BookingDAO.getBooking(s, new BookingCallbacks() {
                    @Override
                    public void onGetBooking(Booking book) {
                        lstBooking.add(book);
                    }
                });
            }
        AppStateManager.setBookingHistory(lstBooking);
    }

    void getGuideBookings(String uid) {
        BookingDAO.getAllBookings(uid, new BookingCallbacks() {
            @Override
            public void onGetAllBookings(ArrayList<Booking> lst) {
                AppStateManager.setBookingHistory(lst);
            }
        });
    }
}