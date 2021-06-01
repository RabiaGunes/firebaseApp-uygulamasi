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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;


public class MainActivity2 extends AppCompatActivity {

    Button listele ;
    TextView tw;
    TextView tw3;
    private static Map< Date , Float > dates = new HashMap<>();
    private static Map<Date, Float> sorted;
    private static Map<Date, Float> sonhali = new HashMap<Date, Float>();
    private static Map<Date, Float> dates5 = new HashMap<Date, Float>();
    public int count = 0;
    public List<String> liste = new ArrayList<String>();
    public List<String> liste2 = new ArrayList<String>();
    List<Integer> list=new ArrayList<Integer>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference oku1 = database.getInstance().getReference().child("taxi");
    DatabaseReference oku2 = database.getInstance().getReference().child("zone");


    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


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


                            Float amount = Float.valueOf(postSnapshot.child("total_amount").getValue().toString());

                            String startDateStr = postSnapshot.child("tpep_pickup_datetime").getValue().toString();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                            try {

                                Date date = (Date)formatter.parse(startDateStr);
                                System.out.println("dates.get:"+dates.get(date));

                                if(dates.containsKey( date)){
                                    count++;
                                    dates.put(date,((dates.get(date)+amount)));

                                }
                                else{
                                    System.out.println("---count:"+count);
                                    list.add(count);
                                    dates.put(date,amount);
                                    count=0;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        System.out.println(dates);
                        System.out.println("---list:"+list);

                        Set<Map.Entry<Date, Float>> s = dates.entrySet();
                        Iterator it = s.iterator();

                        for (int i =0 ; i< dates.size(); i++) {
                            Map.Entry m = (Map.Entry) it.next();

                            dates5.put((Date) m.getKey(),((float)m.getValue()/49));

                        }


                        System.out.println("------"+dates5);

                        sorted = dates5
                                .entrySet()
                                .stream()
                                .sorted(comparingByValue())
                                .collect(
                                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                                LinkedHashMap::new));

                        System.out.println("map after sorting by values in asc order: "
                                + sorted);

                        Set<Map.Entry<Date, Float>> s1 = sorted.entrySet();
                        Iterator it1 = s1.iterator();

                        List<Date> d1 = new ArrayList<Date>();

                        for(int i=0 ; i<2; i++){
                            Map.Entry m1 = (Map.Entry) it1.next();
                            d1.add((Date) m1.getKey());
                        }

                        int ilk = d1.get(0).getDate();
                        int son = d1.get(1).getDate();
                        System.out.println("ilk:"+ilk);
                        System.out.println("son:"+son);

                        Set<Map.Entry<Date, Float>> s2 = sorted.entrySet();
                        Iterator it2 = s2.iterator();

                        for (int i =0 ; i< sorted.size(); i++) {
                            Map.Entry m = (Map.Entry) it2.next();
                            Date d = (Date) m.getKey();

                            if(d.getDate() >= son && d.getDate() <= ilk ){
                                System.out.println("key:"+m.getKey()+"value"+m.getValue());
                                sonhali.put((Date)m.getKey(),(Float)m.getValue());
                            }

                        }

                        tw.setText(sonhali.toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                Query query = database.getInstance().getReference().child("taxi");
                query.addListenerForSingleValueEvent(dinle);
            }
        });

    }
}