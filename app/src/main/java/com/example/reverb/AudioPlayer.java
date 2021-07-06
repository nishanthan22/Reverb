package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.reverb.SongList.musicFiles;

public class AudioPlayer extends AppCompatActivity {

    //Initializing the views

    TextView song_name,author,seekstart,seekstop;
    ImageView loop,rewind,forward,like,cover_image;
    FloatingActionButton playpausebtn;
    SeekBar seekbar;
    int position = -1;
    static ArrayList<MusicFiles> listFiles =new ArrayList();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler=new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        BottomNavigationView bottomNavigationView= findViewById(R.id.bot_navigation);
        bottomNavigationView.setSelectedItemId(R.id.musicitem);
        initviews();
        getIntentMethod();
        song_name.setText(listFiles.get(position).getTitle());
        author.setText(listFiles.get(position).getArtist());
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress*1000);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        AudioPlayer.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer!=null){
                    int sb_currentposition;
                    sb_currentposition=mediaPlayer.getCurrentPosition()/1000;
                    seekbar.setProgress(sb_currentposition);
                    seekstart.setText(formattedTime(sb_currentposition));
                }
                handler.postDelayed(this,1000);

            }
        });

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

    private String formattedTime(int sb_currentposition) {
        String totalnew = "";
        String total = "";
        String seconds = String.valueOf(sb_currentposition%60);
        String minutes = String.valueOf(sb_currentposition/60);
        total=minutes+":"+seconds;
        totalnew=minutes+":"+"0"+seconds;
        if (seconds.length()==1){
            return totalnew;
        }
        else {
            return total;
        }
    }

    private void initviews() {
        //Defining

        song_name=findViewById(R.id.songname);
        author=findViewById(R.id.author);
        seekstart=findViewById(R.id.seekstart);
        seekstop=findViewById(R.id.seekstop);
        loop=findViewById(R.id.loop);
        rewind=findViewById(R.id.rewind);
        forward=findViewById(R.id.forward);
        like=findViewById(R.id.like);
        playpausebtn=findViewById(R.id.play);
        seekbar=findViewById(R.id.seekbar);
        cover_image=findViewById(R.id.cover_image);


    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position",-1);
        listFiles=musicFiles;
        if (listFiles!= null){
            playpausebtn.setImageResource(R.drawable.ic_baseline_pause_24);
            uri = Uri.parse(listFiles.get(position).getPath());
        }
        if(mediaPlayer!= null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        }
        else {
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        }
        seekbar.setMax(mediaPlayer.getDuration()/1000);
        metaData(uri);

    }

    private void metaData(Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int totalDuration = Integer.parseInt(listFiles.get(position).getDuration())/1000;
        seekstop.setText(formattedTime(totalDuration));
        byte[] art = retriever.getEmbeddedPicture();
        if (art != null){
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(cover_image);
        }
        else {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.logocircled)
                    .into(cover_image);

        }

    }

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
//        Context cContext = getBaseContext();
//        if(R.id.musicitem == selectedItemId && cCon){
//
//        }
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