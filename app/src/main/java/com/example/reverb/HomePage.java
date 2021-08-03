package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePage extends AppCompatActivity {
    Button  b2, b3, b4;
    ImageView all_songs,playlist,all_videos,video_folders;
    TextView song,play;
    CircleImageView c;
    long backpress = 0;
    boolean back= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        all_videos = findViewById(R.id.all_videos);
        video_folders=findViewById(R.id.v_folders);
        c=findViewById(R.id.circle_profile);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent us = new Intent(HomePage.this,user.class);
                startActivity(us);
                finish();
            }
        });
        //b1 = findViewById(R.id.AllSongs);
        all_songs = findViewById(R.id.all_son);
        //Intent intent = getIntent();
        // String n = intent.getStringExtra("Username");
        all_songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allsongs = new Intent(HomePage.this, SongList.class);
                startActivity(allsongs);
                finish();

            }
        });
        song=findViewById(R.id.alls);
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allsong = new Intent(HomePage.this, SongList.class);
                startActivity(allsong);
                finish();

            }
        });
        play=findViewById(R.id.playl);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allso = new Intent(HomePage.this, Playslists.class);
                startActivity(allso);
                finish();

            }
        });

//        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.home);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
//
//                switch (item.getItemId()) {
//
//
//                    case R.id.Video:
//                        startActivity(new Intent(getApplicationContext()
//                                , allvideos.class));
//                        finish();
//                        overridePendingTransition(0, 0);
//                        return true;
//
//                    case R.id.home:
//                        return true;
//
//                    case R.id.musicitem:
//                        startActivity(new Intent(getApplicationContext()
//                                , SongList.class));
//                        finish();
//                        overridePendingTransition(0, 0);
//                        return true;
//
//                    case R.id.NameUser:
//                        Intent i = new Intent(getApplicationContext()
//                                , user.class);
//                        //i.putExtra("u_name",n);
//
//                        startActivity(i);
//
//                        overridePendingTransition(0, 0);
//                        return true;
//                    default:
//                        bottomNavigationView.setSelectedItemId(R.id.home);
//
//
//                }
//
//
//                return false;
//
//
//            }
//        });
        playlist = findViewById(R.id.Playlists);
        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playlist = new Intent(HomePage.this, Playslists.class);
                startActivity(playlist);
                finish();

            }
        });


        all_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vid = new Intent(HomePage.this, allvideos.class);
                startActivity(vid);
                finish();

            }
        });


        video_folders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vplay = new Intent(HomePage.this, Vplaylist.class);
                startActivity(vplay);
                finish();

            }
        });


    }


   @Override
  public void onBackPressed() {
//        if(back)
//        {
//            super.onBackPressed();
//            return;
//        }
//        this.back = true;
//        Toast.makeText(this,"Press back again to exist",Toast.LENGTH_SHORT).show();
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                back=false;
//            }
//        },2000);


//       backpress = (backpress + 1);
//       if (backpress == 1) {
//           Toast.makeText(getApplicationContext(), "Press Back Again to Exist ", Toast.LENGTH_SHORT).show();
//
//       }
//      // backpress+=1;
//       if (backpress > 1) {
           super.onBackPressed();

       //}

   }



//        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
//        int selectedItemId = bottomNavigationView.getSelectedItemId();
//
//        if (R.id.home != selectedItemId) {
//            setHomeItem(HomePage.this);
//
//        } else {


//

//
//
//    }
//
//    public void setHomeItem(Activity activity) {
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.home);
//    }
//
//
//}
}