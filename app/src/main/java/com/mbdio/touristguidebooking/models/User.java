package com.mbdio.touristguidebooking.models;

public abstract class User {
    private String userID = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String phone = "";
    private String bio = "";
    private UserType userType;

    public User() {
    }

    public User(String userID, String firstName, String lastName, String email, String phone, UserType userType) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
    }

    public User(String userID, String firstName, String lastName, String email, String phone, String bio, UserType userType) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.bio = bio;
        this.userType = userType;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User(String userID, String firstName, String lastName, String email, UserType userType) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    // Getter Methods

    public String getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

//    public String getPassword() {
//        return password;
//    }

    // Setter Methods

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public void setPassword( String password ) {
//        this.password = password;
//    }
}