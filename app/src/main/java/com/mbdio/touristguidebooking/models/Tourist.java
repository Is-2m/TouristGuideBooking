package com.mbdio.touristguidebooking.models;


import java.util.ArrayList;

public class Tourist extends User {

    private String nationality = "";
    private String profilePicture = "";
    private ArrayList<String> listBookings;

    public Tourist() {
    }

    public Tourist(Tourist t) {
        this.setUserID(t.getUserID());
        this.setUserType(t.getUserType());
        this.setPhone(t.getPhone());
        this.setEmail(t.getEmail());
        this.setFirstName(t.getFirstName());
        this.setLastName(t.getLastName());
        this.setBio(t.getBio());
        this.setNationality(t.getNationality());
        this.setProfilePicture(t.getProfilePicture());
        this.setListBookings(t.listBookings);

    }

    public ArrayList<String> getListBookings() {
        return listBookings;
    }

    public void setListBookings(ArrayList<String> listBookings) {
        this.listBookings = listBookings == null ? this.listBookings : listBookings;
    }

    public Tourist(String userID, String firstName, String lastName, String email, String phone, String bio, String nationality) {
        super(userID, firstName, lastName, email, phone, bio, UserType.TOURIST);
        this.nationality = nationality;
    }

    public Tourist(String userID, String firstName, String lastName, String email, String phone, String bio, String nationality, String profile) {
        super(userID, firstName, lastName, email, phone, bio, UserType.TOURIST);
        this.nationality = nationality;
        this.profilePicture = profile;
    }

    public Tourist(String userID, String firstName, String lastName, String email) {
        super(userID, firstName, lastName, email, UserType.TOURIST);
    }


    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        String spaceless =           // Removes leading and trailing spaces EX:
                nationality.trim();  // '   Hello  World!  ' -> 'Hello  World!'

        if (!nationality.isEmpty() && !spaceless.isEmpty()) {
            this.nationality = nationality;
        }
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profile) {
        String spaceless =           // Removes leading and trailing spaces EX:
                profile.trim();  // '   Hello  World!  ' -> 'Hello  World!'

        if (!profile.isEmpty() && !spaceless.isEmpty()) {
            this.profilePicture = profile;
        }
    }

}

