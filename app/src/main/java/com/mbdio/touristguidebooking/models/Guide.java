package com.mbdio.touristguidebooking.models;

import java.util.ArrayList;
import java.util.List;

public class Guide extends User {
   private List<String> Languages ;
   private  String Location ;
   private String description;
   private String profilePicture = "";
   public Guide() {
      super();
   }

   public Guide(String location) {
     super();
   }

   public Guide(String userID, String firstName, String lastName, String email ) {
      super(userID, firstName, lastName, email, UserType.GUIDE);

   }

   public Guide(String userID, String firstName, String lastName, String email, UserType userType, List<String> languages, String location, String description , String profilePicture) {
      super(userID, firstName, lastName, email, userType);
      Languages = languages;
      Location = location;
      this.description = description;
      this.profilePicture = profilePicture ;
   }
   public Guide(Guide t) {
      this.setUserID(t.getUserID());
      this.setUserType(t.getUserType());
      this.setPhone(t.getPhone());
      this.setEmail(t.getEmail());
      this.setFirstName(t.getFirstName());
      this.setLastName(t.getLastName());
      this.setBio(t.getBio());
     // this.setNationality(t.getNationality());
      this.setProfilePicture(t.getProfilePicture());
   }

   public List<String> getLanguages() {
      return Languages;
   }

   public String getLocation() {
      return Location;
   }

   public String getDescription() {
      return description;
   }

   public void setLanguages(List<String> languages) {
      Languages = languages;
   }

   public void setLocation(String location) {
      Location = location;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getProfilePicture() {
      return profilePicture;
   }

   public void setProfilePicture(String profilePicture) {
      this.profilePicture = profilePicture;
   }
}
