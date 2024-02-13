package com.mbdio.touristguidebooking.models;

import java.util.ArrayList;
import java.util.List;

public class Guide extends User {
    private String Languages;
    private String Location;
    private String profilePicture = "";

    public Guide() {
        super();
    }

    public Guide(String location) {
        super();
    }

    public Guide(String userID, String firstName, String lastName, String email) {
        super(userID, firstName, lastName, email, UserType.GUIDE);

    }

    public Guide(String userID, String firstName, String lastName, String email, UserType userType, String languages, String location, String profilePicture) {
        super(userID, firstName, lastName, email, userType);
        Languages = languages;
        Location = location;
        this.profilePicture = profilePicture;
    }

    public Guide(Guide t) {
        this.setUserID(t.getUserID());
        this.setUserType(t.getUserType());
        this.setPhone(t.getPhone());
        this.setEmail(t.getEmail());
        this.setFirstName(t.getFirstName());
        this.setLastName(t.getLastName());
        this.setBio(t.getBio());
        this.setLocation(t.getLocation());
        this.setLanguages(t.getLanguages());
        this.setProfilePicture(t.getProfilePicture());
    }


    public String getLanguages() {
        return Languages;
    }

    public String getLocation() {
        return Location;
    }


    public void setLanguages(String languages) {
        if (languages != null) {

            String spaceless =           // Removes leading and trailing spaces EX:
                    languages.trim();  // '   Hello  World!  ' -> 'Hello  World!'

            if (!languages.isEmpty() && !spaceless.isEmpty()) {
                this.Languages = languages;
            }
        }
    }

    public void setLocation(String location) {
        if (location != null) {

            String spaceless =           // Removes leading and trailing spaces EX:
                    location.trim();  // '   Hello  World!  ' -> 'Hello  World!'

            if (!location.isEmpty() && !spaceless.isEmpty()) {
                this.Location = location;
            }
        }
    }


    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        if (profilePicture != null) {

            String spaceless =           // Removes leading and trailing spaces EX:
                    profilePicture.trim();  // '   Hello  World!  ' -> 'Hello  World!'

            if (!profilePicture.isEmpty() && !spaceless.isEmpty()) {
                this.profilePicture = profilePicture;
            }
        }
    }

    @Override
    public String toString() {
        return "Guide{" +
                "Languages='" + Languages + '\'' +
                ", Location='" + Location + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }
}
