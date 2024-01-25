package com.mbdio.touristguidebooking.models;

import java.util.ArrayList;
import java.util.List;

public class Guide extends User {
   private List<String> Languages ;
   private  String Location ;
   private String description;

   public Guide(String location) {
     super();
   }

   public Guide(String userID, String firstName, String lastName, String email, String phone, UserType userType, List<String> languages, String location, String description) {
      super(userID, firstName, lastName, email, phone, userType);
      Languages = languages;
      Location = location;
      this.description = description;
   }

   public Guide(String userID, String firstName, String lastName, String email, UserType userType, List<String> languages, String location, String description) {
      super(userID, firstName, lastName, email, userType);
      Languages = languages;
      Location = location;
      this.description = description;
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

}
