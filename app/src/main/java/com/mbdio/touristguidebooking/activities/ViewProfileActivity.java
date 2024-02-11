package com.mbdio.touristguidebooking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.models.Guide;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.User;
import com.mbdio.touristguidebooking.models.UserType;
import com.mbdio.touristguidebooking.utils.AppStateManager;

public class ViewProfileActivity extends AppCompatActivity {

    ImageView profile_img;
    LinearLayout guide_thingies;
    TextView profile_name_lbl, profile_bio_lbl, profile_email_lbl,
            profile_phone_lbl, profile_country_lbl,
            profile_languages_lbl, profile_city_lbl;
    Button profile_book_btn, profile_contact_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        profile_img = findViewById(R.id.profile_img);
        profile_bio_lbl = findViewById(R.id.profile_bio_lbl);
        profile_email_lbl = findViewById(R.id.profile_email_lbl);
        profile_name_lbl = findViewById(R.id.profile_name_lbl);
        profile_country_lbl = findViewById(R.id.profile_country_lbl);
        profile_phone_lbl = findViewById(R.id.profile_phone_lbl);
        guide_thingies = findViewById(R.id.guides_thingies);
        profile_languages_lbl = findViewById(R.id.profile_languages_lbl);
        profile_city_lbl = findViewById(R.id.profile_city_lbl);
        profile_contact_btn = findViewById(R.id.profile_contact_btn);
        profile_book_btn = findViewById(R.id.profile_book_btn);

        User user = AppStateManager.getClickedGuide() == null ? AppStateManager.getClickedTourist() : AppStateManager.getClickedGuide();


        String name = user.getFirstName() + " " + user.getLastName().toUpperCase();
        profile_name_lbl.setText(name);
        profile_phone_lbl.setText(user.getPhone());
        profile_bio_lbl.setText(user.getBio());
        profile_email_lbl.setText(user.getEmail());
        if (user.getUserType() == UserType.GUIDE) {
            Guide guide = (Guide) user;
            String langs = String.join(", ", guide.getLanguages().split(";"));
            profile_languages_lbl.setText(langs);
            profile_country_lbl.setText("Morocco");
            guide_thingies.setVisibility(View.VISIBLE);
            guide_thingies.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            profile_city_lbl.setText(guide.getLocation());


            if (!guide.getProfilePicture().isEmpty()) {
                Glide.with(this) // Replace 'context' with your Context
                        .load(guide.getProfilePicture())
                        .into(profile_img);
            }
        } else {
            Tourist tourist = (Tourist) user;
            guide_thingies.setVisibility(View.GONE);
            profile_book_btn.setVisibility(View.GONE);

            if (!tourist.getProfilePicture().isEmpty()) {
                Glide.with(this) // Replace 'context' with your Context
                        .load(tourist.getProfilePicture())
                        .into(profile_img);
            }

        }


        String phone = user.getPhone().replace(" ", "");
        String url = "https://api.whatsapp.com/send?phone=" + phone;

        profile_contact_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppStateManager.setClickedGuide(null);
        AppStateManager.setClickedTourist(null);

    }
}