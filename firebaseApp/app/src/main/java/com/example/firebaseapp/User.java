package com.example.firebaseapp;

public class User {


    public String borough;
    public String zone;
    public int LocationID;
    public float latitude;
    public float longitude;

    public User() {

    }

    public User(String borough, String zone , int LocationID ,float latitude , float longitude) {
        this.borough = borough;
        this.zone = zone;
        this.LocationID = LocationID;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
