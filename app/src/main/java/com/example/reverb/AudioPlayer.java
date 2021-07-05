package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AudioPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        BottomNavigationView bottomNavigationView= findViewById(R.id.bot_navigation);
        bottomNavigationView.setSelectedItemId(R.id.musicitem);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.musicitem:
                        return true;

                    case R.id.Video:
                        startActivity(new Intent(getApplicationContext()
                                ,VideoPlayer.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,HomePage.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.NameUser:
                        startActivity(new Intent(getApplicationContext()
                                ,user.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    default:
                    bottomNavigationView.setSelectedItemId(R.id.home);

                }


                return false;


            }
        });

    }

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.home != selectedItemId) {
            setHomeItem(HomePage.class);

        } else {
            super.onBackPressed();
           // System.exit(0);

        }


    }

    public void setHomeItem(Class<HomePage> activity) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}