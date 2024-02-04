package com.mbdio.touristguidebooking.activities.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.activities.EditProfileActivity;
import com.mbdio.touristguidebooking.dao.ImageManagerCallbacks;
import com.mbdio.touristguidebooking.dao.ImageManagerDAO;
import com.mbdio.touristguidebooking.dao.TouristCallbacks;
import com.mbdio.touristguidebooking.dao.TouristDAO;
import com.mbdio.touristguidebooking.models.Guide;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.User;
import com.mbdio.touristguidebooking.models.UserType;
import com.mbdio.touristguidebooking.utils.AppStateManager;

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    ImageView profile_img;
    FloatingActionButton pdp_edit_img;
    TextView profile_name_lbl, profile_bio_lbl, profile_email_lbl,
            profile_phone_lbl, profile_country_lbl;
    Button btn_edit;
    final int PICK_IMAGE_REQUEST = 197; // just a random number, and random number can do trick

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        profile_img = v.findViewById(R.id.profile_img);
        pdp_edit_img = v.findViewById(R.id.pdp_edit_img);
        profile_bio_lbl = v.findViewById(R.id.profile_bio_lbl);
        profile_email_lbl = v.findViewById(R.id.profile_email_lbl);
        profile_name_lbl = v.findViewById(R.id.profile_name_lbl);
        profile_country_lbl = v.findViewById(R.id.profile_country_lbl);
        profile_phone_lbl = v.findViewById(R.id.profile_phone_lbl);
        btn_edit = v.findViewById(R.id.profile_edit_btn);

        refreshProfile(getContext());
        btn_edit.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity().getBaseContext(), EditProfileActivity.class);
            startActivityForResult(intent, 1);
        });

        pdp_edit_img.setOnClickListener(v1 -> {
            openImageChooser();
        });
        return v;
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            refreshProfile(getContext());

        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            ImageManagerDAO.uploadImageToFirebase(getActivity(), imageUri, new ImageManagerCallbacks() {
                @Override
                public void onImageUploaded(boolean success, String message, String url) {
                    if (success) {
                        TouristDAO.uploadProfilePicture(AppStateManager.getCurrentUser().getUserID(), url, new TouristCallbacks() {
                            @Override
                            public void onProfilePictureUpdated(boolean success) {
                                if (success) {
                                        if(AppStateManager.getCurrentUser().getUserType() == UserType.TOURIST ) {
                                            Tourist t = (Tourist) AppStateManager.getCurrentUser();
                                            t.setProfilePicture(url);
                                            AppStateManager.setCurrentUser(new Tourist(t));
                                            refreshProfile(getContext());
                                        } else {
                                            Guide t = (Guide) AppStateManager.getCurrentUser();
                                            t.setProfilePicture(url);
                                            AppStateManager.setCurrentUser(new Guide(t));
                                            refreshProfile(getContext());
                                        }

                                }
                            }
                        });
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    private void refreshProfile(Context ctx) {
        User user = AppStateManager.getCurrentUser();
        if (user.getUserType() == UserType.TOURIST) {
            Tourist tourist = (Tourist) user;
            String name = tourist.getFirstName() + " " + tourist.getLastName().toUpperCase();
            if (!tourist.getProfilePicture().isEmpty()) {
                System.out.println("ProfileFragment.refreshProfile");
                System.out.println("URL : " + tourist.getProfilePicture());
                Glide.with(ctx) // Replace 'context' with your Context
                        .load(tourist.getProfilePicture())
                        .into(profile_img);
            }
            profile_name_lbl.setText(name);
            profile_phone_lbl.setText(tourist.getPhone());
            profile_bio_lbl.setText(tourist.getBio());
            profile_email_lbl.setText(tourist.getEmail());
            profile_country_lbl.setText(tourist.getNationality());
        } else {
            Guide guide = (Guide) user;
            String name = guide.getFirstName() + " " + guide.getLastName().toUpperCase();
            if (!guide.getProfilePicture().isEmpty()) {
                System.out.println("ProfileFragment.refreshProfile");
                System.out.println("URL : " + guide.getProfilePicture());
                Glide.with(ctx) // Replace 'context' with your Context
                        .load(guide.getProfilePicture())
                        .into(profile_img);
            }
            profile_name_lbl.setText(name);
            profile_phone_lbl.setText(guide.getPhone());
            profile_bio_lbl.setText(guide.getBio());
            profile_email_lbl.setText(guide.getEmail());
         //   profile_country_lbl.setText(guide.getNationality());
        }
    }
}
