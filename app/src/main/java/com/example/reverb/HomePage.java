package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
    Button b1, b2,b3,b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        b1 = findViewById(R.id.AllSongs);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allsongs = new Intent(HomePage.this, SongList.class);
                startActivity(allsongs);

            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                switch (item.getItemId()) {


                    case R.id.Video:
                        startActivity(new Intent(getApplicationContext()
                                , VideoPlayer.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.musicitem:
                        startActivity(new Intent(getApplicationContext()
                                , AudioPlayer.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.NameUser:
                        startActivity(new Intent(getApplicationContext()
                                , user.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                    default:
                        bottomNavigationView.setSelectedItemId(R.id.home);


                }


                return false;


            }
        });
         b2 = findViewById(R.id.Playlists);
         b2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent playlist = new Intent(HomePage.this,Playslists.class);
        startActivity(playlist);

         }
        });

        b3 = findViewById(R.id.AllVideos);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vid = new Intent(HomePage.this,allvideos.class);
                startActivity(vid);

            }
        });

        b4 = findViewById(R.id.VPlaylists);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vplay = new Intent(HomePage.this,Vplaylist.class);
                startActivity(vplay);

            }
        });


    }



    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();

        if (R.id.home != selectedItemId) {
            setHomeItem(HomePage.this);

        } else {
           super.onBackPressed();
            //System.exit(0);

        }


    }

    public void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }


}