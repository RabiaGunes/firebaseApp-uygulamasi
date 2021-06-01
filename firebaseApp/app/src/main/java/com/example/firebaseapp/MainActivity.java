package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.sorgu_1){
            Intent intent1 = new Intent(MainActivity.this, MainActivity1.class);
            startActivity(intent1);
        }

        else if (item.getItemId() == R.id.sorgu_2){
            Intent intent2 = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent2);
        }

        else if (item.getItemId() == R.id.sorgu_3){
            Intent intent3 = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent3);
        }

        return super.onOptionsItemSelected(item);
    }

}