package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;


public class MainActivity1 extends AppCompatActivity {

    Button listele ;
    TextView tw;
    private static Map< Date , Integer > dates = new HashMap<>();
    private static Map<Date, Integer> sorted;
    private static Map<Date, Integer> sorted5;
    private static Map<Object, Object> dates5 = new HashMap<Object, Object>();

    public List<String> liste = new ArrayList<String>();
    public List<String> liste2 = new ArrayList<String>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference oku1 = database.getInstance().getReference().child("taxi");
    DatabaseReference oku2 = database.getInstance().getReference().child("zone");


    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


        listele = (Button) findViewById(R.id.button);
        tw = (TextView) findViewById(R.id.textView);


        listele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                ValueEventListener dinle = new ValueEventListener() {



                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                            int val = Integer.valueOf(postSnapshot.child("passenger_count").getValue().toString());
                            System.out.println("vaall:"+val);
                            String startDateStr = postSnapshot.child("tpep_pickup_datetime").getValue().toString();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            try {
                                Date date = (Date)formatter.parse(startDateStr);
                                //System.out.println("------------------"+date.getDate());
                                System.out.println("dates.get:"+dates.get(date));
                                dates.put( date, dates.containsKey( date) ? dates.get(date)+val : val);
                                // dates.put((int) date.getDate(), dates.containsKey((int) date.getDate()) ? dates.get((int) date.getDate())+val : val);
                                // dates.put(((int) date.getDate()),0);


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            //Log.d("uye", (postSnapshot.child("tpep_pickup_datetime").getValue().toString()));
                            //  Log.d("uye", (postSnapshot.child("passenger_count").toString()));

                            liste.add(postSnapshot.child("tpep_pickup_datetime").toString());
                            liste2.add(postSnapshot.child("passenger_count").toString());

                            // tw.setText(postSnapshot.child("tpep_pickup_datetime").toString());
                            // tw3.setText(postSnapshot.getValue().toString());

                        }
                        System.out.println(dates);

                        sorted = dates
                                .entrySet()
                                .stream()
                                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                                .collect(
                                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                                LinkedHashMap::new));

                        System.out.println("map after sorting by values in descending order: "
                                + sorted);

                        Set<Map.Entry<Date, Integer>> s = sorted.entrySet();
                        Iterator it = s.iterator();

                        for (int i=0; i<5; i++ ) {
                            Map.Entry m = (Map.Entry) it.next();
                            //int key = (Integer) m.getKey();
                            // String value = (String) m.getValue();
                            dates5.put(m.getKey(),m.getValue());

                        }


                        System.out.println("------"+dates5);


                        //tw.setText((CharSequence) liste);
                        // Log.d("uye",liste.toString());

                        //tw.setText(dates5.toString());
                        tw.setText(dates5.toString());
                      /*  User k = new User();
                        k = snapshot.getValue(User.class);
                        tw.setText( k.id+"\n"+k.borough + "\n"+ ((float) k.latitude) + "\n" + ((float) k.longitude)  + "\n"  +k.zone  ); */
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                //oku.addValueEventListener(dinle);


                // Query query1 = oku.orderByChild("latitude").limitToFirst(5);

                //tw.setText(query1.toString());

                // query1.addListenerForSingleValueEvent(dinle);
                Query query = database.getInstance().getReference().child("taxi");
                //.orderByChild("passenger_count").limitToLast(8);


                query.addListenerForSingleValueEvent(dinle);





/*
                String inputPattern = database.getInstance().getReference().child("taxi").child("0").child("tpep_pickup_datetime").toString();

                String outputPattern = "dd-MM-yyyy";

                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                Date date = null;
                String str = null;

                try {
                    date = inputFormat.parse(time);
                    str = outputFormat.format(date);

                    Log.i("mini", "Converted Date Today:" + str);
                } catch (ParseException e) {
                    e.printStackTrace();
                } */







            }
        });



    }
}