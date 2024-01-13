package com.mbdio.touristguidebooking.models;

public class Tourist extends User {

    private String nationality = "";

    public Tourist() {
    }

//    public Tourist(String userID, String firstName, String lastName, String email, String phone,
//                   String nationality) {
//        super(userID, firstName, lastName, email, phone, UserType.TOURIST);
//        this.nationality = nationality;
//    }

    public Tourist(String userID, String firstName, String lastName, String email, String phone, String bio, String nationality) {
        super(userID, firstName, lastName, email, phone, bio, UserType.TOURIST);
        this.nationality = nationality;
    }

    public Tourist(String userID, String firstName, String lastName, String email) {
        super(userID, firstName, lastName, email, UserType.TOURIST);
    }


    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

}

