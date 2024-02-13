package com.mbdio.touristguidebooking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.dao.AuthCallbacks;
import com.mbdio.touristguidebooking.dao.AuthDAO;
import com.mbdio.touristguidebooking.dao.GuideCallbacks;
import com.mbdio.touristguidebooking.dao.GuideDAO;
import com.mbdio.touristguidebooking.dao.TouristCallbacks;
import com.mbdio.touristguidebooking.dao.TouristDAO;
import com.mbdio.touristguidebooking.models.Guide;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.User;
import com.mbdio.touristguidebooking.models.UserType;
import com.mbdio.touristguidebooking.utils.AppStateManager;

public class EditProfileActivity extends AppCompatActivity {

    TextInputEditText txt_Fname, txt_Lname, txt_bio, txt_country, txt_languages,txt_city;
    TextView btn_resetPass, btn_verifyEmail, lbl_phone, lbl_email, lbl_email1, lbl_country;
    LinearLayout guide_container, nationality_container;
    Button btn_save;
    CountryCodePicker ccp;
    EditText txt_phone;
    User user = AppStateManager.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        txt_Fname = findViewById(R.id.edit_profile_fname_txt);
        txt_Lname = findViewById(R.id.edit_profile_lname_txt);
        txt_bio = findViewById(R.id.edit_profile_bio_txt);
        txt_phone = findViewById(R.id.edit_profile_phone_txt);
        txt_country = findViewById(R.id.edit_profile_country_txt);
        lbl_country = findViewById(R.id.edit_profile_country_lbl);
        lbl_email = findViewById(R.id.edit_profile_email_lbl);
        lbl_phone = findViewById(R.id.edit_profile_phone);
        lbl_email1 = findViewById(R.id.edit_profile_email2_lbl);
        btn_resetPass = findViewById(R.id.edit_profile_rest_pass_lbl);
        btn_verifyEmail = findViewById(R.id.edit_profile_verify_email_lbl);
        btn_save = findViewById(R.id.edit_profile_save_button);
        txt_languages = findViewById(R.id.edit_profile_languages);
        guide_container = findViewById(R.id.guide_container);
        nationality_container = findViewById(R.id.nationality_container);
        txt_city=findViewById(R.id.edit_profile_city);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(txt_phone);


        if (AppStateManager.getCurrentUser().getUserType() == UserType.TOURIST) {

            lbl_country.setText(((Tourist) user).getNationality());
            guide_container.setVisibility(View.INVISIBLE);
            guide_container.getLayoutParams().height = 0;

            nationality_container.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            nationality_container.setVisibility(View.VISIBLE);

        } else {
            txt_languages.setHint(((Guide) user).getLanguages());
            nationality_container.getLayoutParams().height = 0;
            nationality_container.setVisibility(View.INVISIBLE);

            guide_container.setVisibility(View.VISIBLE);
            guide_container.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

        }


        lbl_email.setText(user.getEmail());
        lbl_email1.setText(user.getEmail());
        lbl_phone.setText(user.getPhone());


        if (AppStateManager.getCurrentFireUser().isEmailVerified()) {
            btn_verifyEmail.setFocusable(false);
            btn_verifyEmail.setClickable(false);
            btn_verifyEmail.setEnabled(false);
            btn_verifyEmail.setText("Email Verified");
            btn_verifyEmail.setFocusable(false);
            int color = ContextCompat.getColor(getBaseContext(), R.color.grayed_out);
            btn_verifyEmail.setTextColor(color);
        } else {
            btn_verifyEmail.setOnClickListener(v -> {
                AuthDAO.confirmEmail(EditProfileActivity.this, new AuthCallbacks() {
                    @Override
                    public void onEmailVerificationComplete() {
                        Toast.makeText(EditProfileActivity.this,
                                "A verification email was sent to " + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
        btn_resetPass.setOnClickListener(v -> {
            AuthDAO.resetPassword(EditProfileActivity.this, new AuthCallbacks() {
                @Override
                public void onPasswordResetComplete() {
                    Toast.makeText(EditProfileActivity.this,
                            "An email containing your password reset link was sent to " + user.getEmail(),
                            Toast.LENGTH_LONG).show();
                }
            });
        });
        btn_save.setOnClickListener(v -> {


            String phone = "+" + ccp.getSelectedCountryCode() + txt_phone.getEditableText().toString();
            if(txt_phone.getEditableText().toString().isEmpty()){
                phone="";
            }else{
                phone = phone.replace(" ", "");
                phone = phone.replace("-", "");
            }


            String country = txt_country.getEditableText().toString();
            String fname = txt_Fname.getEditableText().toString();
            String lname = txt_Lname.getEditableText().toString();
            String bio = txt_bio.getEditableText().toString();
            String langs = txt_languages.getEditableText().toString();
            String city = txt_city.getEditableText().toString();


            if (user.getUserType() == UserType.TOURIST) {
                Tourist t = new Tourist(((Tourist) user));
                t.setPhone(phone);
                t.setBio(bio);
                t.setFirstName(fname);
                t.setLastName(lname);
                t.setNationality(country);
                TouristDAO.update(t, new TouristCallbacks() {
                    @Override
                    public void onTouristUpdated(boolean success, String message) {
                        if (success) {
                            AppStateManager.setCurrentUser(t);
                            Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        setResult(RESULT_OK);
                        finish();

                    }
                });
            } else {
                Guide t = new Guide(((Guide) user));
                t.setPhone(phone);
                t.setBio(bio);
                t.setFirstName(fname);
                t.setLastName(lname);
                t.setLanguages(langs);
                t.setLocation(city);
                System.out.println("EditProfileActivity.onCreate");
                System.out.println(t);
                GuideDAO.update(t, new GuideCallbacks() {
                    @Override
                    public void onGuideUpdated(boolean success, String message) {
                        if (success) {
                            AppStateManager.setCurrentUser(t);
                            Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        setResult(RESULT_OK);
                        finish();

                    }
                });
            }


        });

    }
}