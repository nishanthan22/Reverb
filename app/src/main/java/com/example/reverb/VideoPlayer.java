package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.WindowManager;

//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
//import com.google.android.exoplayer2.extractor.ExtractorsFactory;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.source.ProgressiveMediaSource;
//import com.google.android.exoplayer2.ui.PlayerView;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.example.reverb.AdapterVideoList.videosList;

public class VideoPlayer extends AppCompatActivity {

    long videoId;
    //Uri uri;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private MediaSource mediaSource;
    static ArrayList<ModelVideo> videosFiles =new ArrayList();
    //PlayerView playerView;
   // SimpleExoPlayer simpleExoPlayer;
    //int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        //videoId = getIntent().getExtras().getLong("videoId");
        videosFiles= videosList;

        int pos=getIntent().getIntExtra("position",-1);
         //uri = videosFiles.get(pos).getData();
         videoId = videosFiles.get(pos).getId();
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        initializeViews();


        //Uri uri = ModelVideo.getData();
       // position=getIntent().getIntExtra("position",-1);

//        String path = ModelVideo.getPath();
//        if(path!=null)
//        {
//            Uri uri = Uri.parse(path);
//            simpleExoPlayer=new SimpleExoPlayer.Builder(this).build();
//            DataSource.Factory factory = new DefaultDataSourceFactory(this, Util.getUserAgent(this,
//                    "Reverb"));
//            ExtractorsFactory extractorsFactory= new DefaultExtractorsFactory();
//            ProgressiveMediaSource mediaSource = new ProgressiveMediaSource().Factory(factory, extractorsFactory).createMediaSource(uri);
//            playerView.setPlayer(simpleExoPlayer);
//            playerView.setKeepScreenOn(true);
//            simpleExoPlayer.prepare(mediaSource);
//            simpleExoPlayer.setPlayWhenReady(true);
//
//        }



//        BottomNavigationView bottomNavigationView= findViewById(R.id.bot_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.Video);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId())
//                {
//                    case R.id.Video:
//                        return true;
//
//                    case R.id.musicitem:
//                        startActivity(new Intent(getApplicationContext()
//                                ,AudioPlayer.class));
//                        finish();
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext()
//                                ,HomePage.class));
//                        finish();
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.NameUser:
//                        startActivity(new Intent(getApplicationContext()
//                                ,user.class));
//                        finish();
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    default:
//                        bottomNavigationView.setSelectedItemId(R.id.home);
//                }
//
//
//                return false;
//
//
//            }
//        });
    }

    private void initializeViews() {
        playerView = findViewById(R.id.playerView);
    }

    private void initializePlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        Uri videoUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,videoId);
        MediaSource mediaSource = buildMediaSource(videoUri);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, getString(R.string.app_name));
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    private void releasePlayer(){
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        if(Util.SDK_INT<24){
            releasePlayer();
        }
        super.onPause();

    }

    @Override
    protected void onStop() {
        if(Util.SDK_INT>=24){
            releasePlayer();
        }
        super.onStop();
    }



//    @Override
//    public void onBackPressed() {
////        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
////        int selectedItemId = bottomNavigationView.getSelectedItemId();
////        if (R.id.home != selectedItemId) {
////            setHomeItem(HomePage.class);
////
////        } else {
////            //super.onBackPressed();
////            System.exit(0);
//
//        }




//    public void setHomeItem(Class<HomePage> activity) {
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.home);
//    }
}