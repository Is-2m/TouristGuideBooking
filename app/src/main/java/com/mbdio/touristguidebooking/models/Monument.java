package com.mbdio.touristguidebooking.models;


public class Monument {

    String name, adresse, ville,image, history,longitude,latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Monument(String name, String adresse, String ville, String image, String history, String longitude, String latitude) {
        this.name = name;
        this.adresse = adresse;
        this.ville = ville;
        this.image = image;
        this.history = history;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Monument(){
    }

    public Monument(String name, String adresse, String ville, String image, String history) {
        this.name = name;
        this.adresse = adresse;
        this.ville = ville;
        this.image = image;
        this.history = history;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}

