package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.reverb.AlbumDetailsAdapter.albumFiles;
import static com.example.reverb.SongAdapter.mFiles;
import static com.example.reverb.SongList.loopBoolean;
import static com.example.reverb.SongList.musicFiles;

public class AudioPlayer extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    //Initializing the views

    TextView song_name,author,seekstart,seekstop;
    ImageView loop,rewind,forward,like,cover_image,blurcover;
    FloatingActionButton playpausebtn;
    SeekBar seekbar;
    int position = -1;
    static ArrayList<MusicFiles> listFiles =new ArrayList();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler=new Handler();
    String previousActivity;
    private Thread playPauseThread;
    SwipeListener swipeListener;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        BottomNavigationView bottomNavigationView= findViewById(R.id.bot_navigation);
        bottomNavigationView.setSelectedItemId(R.id.musicitem);
        initviews();
        song_name.setSelected(true);
        getIntentMethod();
        previousActivity=getIntent().getStringExtra("songlist");
        song_name.setText(listFiles.get(position).getTitle());
        author.setText(listFiles.get(position).getArtist());
        mediaPlayer.setOnCompletionListener(this);
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

        //Forward 10s button

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
                }
            }
        });

        // Rewind 10s button

        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
                }
            }
        });

        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loopBoolean){
                    loopBoolean = false;
                    loop.setImageResource(R.drawable.ic_baseline_loop_24);

                }
                else {
                    loopBoolean=true;
                    //on
                    loop.setImageResource(R.drawable.loop_on);
                }
            }
        });



    }

    @Override
    protected void onResume() {

        playPauseThread = new Thread() {
            @Override
            public void run() {
                super.run();
                playpausebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseButtonClicked();
                    }
                });
            }
        };
        playPauseThread.start();
        super.onResume();


    }

    private void playPauseButtonClicked() {
        if (mediaPlayer.isPlaying()){
            playpausebtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            mediaPlayer.pause();
            seekbar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int sb_currentposition;
                        sb_currentposition=mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(sb_currentposition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
        }
        else {
            playpausebtn.setImageResource(R.drawable.ic_baseline_pause_24);
            mediaPlayer.start();
            seekbar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int sb_currentposition;
                        sb_currentposition=mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(sb_currentposition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
        }
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
        relativeLayout = findViewById(R.id.audio_relative);
        swipeListener = new SwipeListener(relativeLayout);
        //blurcover = findViewById(R.id.blurcover);


    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position",-1);
        String sender = getIntent().getStringExtra("sender");
        if(sender != null && sender.equals("albumDetails")){
            listFiles=albumFiles;
        }
        else {
            listFiles = mFiles;
        }
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
           // Glide.with(this)
                   // .asBitmap()
                   // .load(art)
                   // .transform(new BlurTransformation(this)).into(blurcover);
        }
        else {
            Glide.with(this)
                    .asBitmap()
                    .load("#00000000")
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
        if(R.id.home != selectedItemId && position !=-1){
            startActivity(new Intent(AudioPlayer.this,SongList.class));
            finish();
        }

        else if (R.id.home != selectedItemId && position ==-1) {
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

    @Override
    public void onCompletion(MediaPlayer mp) {
        nextSong();
        if (mediaPlayer!= null){
            mediaPlayer= MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);
        }

    }
    public void nextSong(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            if (!loopBoolean){
                position=((position+1)% listFiles.size());}
            uri = Uri.parse(listFiles.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listFiles.get(position).getTitle());
            author.setText(listFiles.get(position).getArtist());
            seekbar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int sb_currentposition;
                        sb_currentposition=mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(sb_currentposition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playpausebtn.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            mediaPlayer.start();
        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (!loopBoolean){
                position=((position+1)% listFiles.size());}
            uri = Uri.parse(listFiles.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listFiles.get(position).getTitle());
            author.setText(listFiles.get(position).getArtist());
            seekbar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int sb_currentposition;
                        sb_currentposition=mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(sb_currentposition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playpausebtn.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);


        }


    }
    public void previousSong(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position-1)<0 ? (listFiles.size() - 1): (position-1));
            uri = Uri.parse(listFiles.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listFiles.get(position).getTitle());
            author.setText(listFiles.get(position).getArtist());
            seekbar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int sb_currentposition;
                        sb_currentposition=mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(sb_currentposition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playpausebtn.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            mediaPlayer.start();
        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position-1)<0 ? (listFiles.size() - 1): (position-1));
            uri = Uri.parse(listFiles.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listFiles.get(position).getTitle());
            author.setText(listFiles.get(position).getArtist());
            seekbar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        int sb_currentposition;
                        sb_currentposition=mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(sb_currentposition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playpausebtn.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);


        }


    }

    private class SwipeListener implements  View.OnTouchListener{
        GestureDetector gestureDetector;

        SwipeListener(View view){
            int threshold = 100;
            int velocity_threshold=100;

            GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onDown(MotionEvent e) {

                    return true;
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    float xDiff = e2.getX() - e1.getX();
                    float yDiff = e2.getY() - e1.getY();
                    try {
                        if(Math.abs(xDiff)>Math.abs(yDiff)){
                            if(Math.abs(xDiff)>threshold && Math.abs(velocityX)>velocity_threshold){
                                if (xDiff>0){
                                    //swiped right
                                    //switch to previous song
                                    previousSong();

                                }else {
                                    //swiped left
                                    //switch to next song
                                    nextSong();

                                }
                                return true;

                            }
                        }
                        else {
                            if (Math.abs(yDiff)>threshold && Math.abs(velocityY)>velocity_threshold){
                                if (yDiff>0){
                                    //Toast.makeText(getApplicationContext(),"Swiped Down",Toast.LENGTH_SHORT).show();
                                    //previous Song
                                    previousSong();

                                }
                                else {
                                    //Toast.makeText(getApplicationContext(),"Swiped Up",Toast.LENGTH_SHORT).show();

                                    //next song
                                    nextSong();

                                }
                                return true;
                            }
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return false;
                }
            };
            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    }
}