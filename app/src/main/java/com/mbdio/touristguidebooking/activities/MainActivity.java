package com.mbdio.touristguidebooking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.activities.fragments.DiscoverGuidesFragment;
import com.mbdio.touristguidebooking.activities.fragments.HomeFragment;
import com.mbdio.touristguidebooking.activities.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment homeFragment;
    private Fragment discoverFragment;
    private Fragment profileFragment;
    private Fragment activeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize fragments
        homeFragment = new HomeFragment();
        discoverFragment = new DiscoverGuidesFragment();
        profileFragment = new ProfileFragment();
        // Set the initial active fragment
        activeFragment = homeFragment;

        // Load the initial fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment, homeFragment, "Home")
                .commit();


        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                switchFragment(homeFragment, "Home");
                return true;
            } else if (itemId == R.id.nav_discover) {
                switchFragment(discoverFragment, "Discover");
                return true;
            } else if (itemId == R.id.nav_profile) {
                switchFragment(profileFragment, "Profile");

                return true;
            } else {
                return false;
            }
        });
    }

    private void switchFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment, fragment, tag)
                .commit();
        activeFragment = fragment;
    }

}