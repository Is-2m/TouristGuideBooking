package com.mbdio.touristguidebooking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

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

public class LandingActivity extends AppCompatActivity {

    public static final int DELAY = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        AppStateManager.initialize(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

//        getCurrentLocation();

        AuthDAO.checkIsLogged(new AuthCallbacks() {
            @Override
            public void onCheckLoggedComplete(FirebaseUser fireUser) {
                if (fireUser != null) {
                    AppStateManager.setCurrentFireUser(fireUser);
                    User currentUser = AppStateManager.getCurrentUser();
                    if (currentUser != null) {
                        if (currentUser.getUserType() == UserType.GUIDE) {
                            getGuideBookings(currentUser.getUserID());
                        } else {
                            getTouristBookings((Tourist) currentUser);
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                redirectToMainActivity(LandingActivity.this, currentUser);
                            }
                        }, DELAY);
                    } else {
                        UserDAO.getUser(fireUser.getUid(), new UserCallbacks() {
                            @Override
                            public void onGetUser(User user) {
                                if (user != null) {
                                    AppStateManager.setCurrentUser(user);
                                    if (user.getUserType() == UserType.GUIDE) {
                                        getGuideBookings(user.getUserID());
                                    } else {
                                        getTouristBookings((Tourist) user);
                                    }
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            redirectToMainActivity(LandingActivity.this, user);
                                        }
                                    }, DELAY);
                                }
                            }
                        });
                    }
                } else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }, DELAY);
                }
            }
        });
    }

    public static void redirectToMainActivity(Context ctx, User user) {

        Intent intent = new Intent(ctx, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);

    }

    public Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Check for ACCESS_FINE_LOCATION permission first
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return null; // Wait for permission grant before accessing location
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Handle alternative location provider if permission denied
        if (location == null) {
            // Permission might be denied, try coarse location or network-based location
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } else {
                // Neither permission granted, handle the case (e.g., show error message)
                return null;
            }
        }


        return location;
    }

    public static void getTouristBookings(Tourist t) {
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

    public static void getGuideBookings(String uid) {
        BookingDAO.getAllBookings(uid, new BookingCallbacks() {
            @Override
            public void onGetAllBookings(ArrayList<Booking> lst) {
                AppStateManager.setBookingHistory(lst);
            }
        });
    }
}