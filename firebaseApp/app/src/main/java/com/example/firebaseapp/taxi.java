package com.example.firebaseapp;

public class taxi {


    public String tpep_pickup_datetime ;
    public String tpep_dropoff_datetime ;
    public int DOLocationID;
    public int PULocationID;
    public int passenger_count;
    public float total_amount;
    public float trip_distance;

    public taxi(){

    }

    public taxi(String tpep_pickup_datetime, String tpep_dropoff_datetime, int DOLocationID, int PULocationID, int passenger_count, float total_amount, float trip_distance) {
        this.tpep_pickup_datetime = tpep_pickup_datetime;
        this.tpep_dropoff_datetime = tpep_dropoff_datetime;
        this.DOLocationID = DOLocationID;
        this.PULocationID = PULocationID;
        this.passenger_count = passenger_count;
        this.total_amount = total_amount;
        this.trip_distance = trip_distance;
    }
}
