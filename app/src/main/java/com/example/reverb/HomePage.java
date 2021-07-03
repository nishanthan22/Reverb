package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
      Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        b1 = findViewById(R.id.AllSongs);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allsongs = new Intent(HomePage.this,SongList.class);
                startActivity(allsongs);
                //finish();
            }
        });
        BottomNavigationView bottomNavigationView= findViewById(R.id.bot_navigation);
         bottomNavigationView.setSelectedItemId(R.id.home);

         bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                 switch (item.getItemId())
                 {


                     case R.id.Video:
                         startActivity(new Intent(getApplicationContext()
                                 ,VideoPlayer.class));
                         overridePendingTransition(0,0);
                         return true;

                     case R.id.home:
                         return true;

                     case R.id.musicitem:
                         startActivity(new Intent(getApplicationContext()
                                 ,AudioPlayer.class));
                         overridePendingTransition(0,0);
                         return true;

                     case R.id.NameUser:
                         startActivity(new Intent(getApplicationContext()
                                 ,user.class));
                         overridePendingTransition(0,0);
                         return true;
                 }


                 return false;


             }
         });

    }
}