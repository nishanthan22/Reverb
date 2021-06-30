package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
      Button b1,b2;
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
        b2 = findViewById(R.id.MusicPlayer);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent audioplayer = new Intent(HomePage.this,AudioPlayer.class);
                startActivity(audioplayer);
                //finish();
            }
        });

    }
}