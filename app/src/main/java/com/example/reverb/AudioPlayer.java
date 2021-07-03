package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.NameUser:
                        startActivity(new Intent(getApplicationContext()
                                ,user.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                }


                return false;


            }
        });





    }


}