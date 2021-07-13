package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.sql.DataSource;

public class VideoPlayer extends AppCompatActivity {
    PlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        position=getIntent().getIntExtra("position",-1);

        String path = ModelVideo.getPath();
        if(path!=null)
        {
            Uri uri = Uri.parse(path);
            simpleExoPlayer=new SimpleExoPlayer.Builder(this).build();

             //factory = new DefaultDataSourceFactory(this, Util.getUserAgent(this,
                    //"My App Name"));

        }


        BottomNavigationView bottomNavigationView= findViewById(R.id.bot_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Video);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.Video:
                        return true;

                    case R.id.musicitem:
                        startActivity(new Intent(getApplicationContext()
                                ,AudioPlayer.class));
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
            //super.onBackPressed();
            System.exit(0);

        }


    }

    public void setHomeItem(Class<HomePage> activity) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}