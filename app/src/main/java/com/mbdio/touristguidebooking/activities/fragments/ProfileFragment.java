package com.mbdio.touristguidebooking.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.activities.EditProfileActivity;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.User;
import com.mbdio.touristguidebooking.models.UserType;
import com.mbdio.touristguidebooking.utils.AppStateManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ImageView profile_img;
    FloatingActionButton pdp_edit_img;
    TextView profile_name_lbl, profile_bio_lbl, profile_email_lbl,
            profile_phone_lbl, profile_country_lbl;
    Button btn_edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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

        User user = AppStateManager.getCurrentUser();
        if (user.getUserType() == UserType.TOURIST) {
            Tourist tourist = (Tourist) user;
            String name = tourist.getFirstName() + " " + tourist.getLastName().toUpperCase();
            profile_name_lbl.setText(name);
            profile_phone_lbl.setText(tourist.getPhone());
            profile_bio_lbl.setText(tourist.getPhone());
            profile_email_lbl.setText(tourist.getEmail());
            profile_country_lbl.setText(tourist.getNationality());
        }
        btn_edit.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity().getBaseContext(), EditProfileActivity.class);
            startActivity(intent);
        });
        return v;
    }
}