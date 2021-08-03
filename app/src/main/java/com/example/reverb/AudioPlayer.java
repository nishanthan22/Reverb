package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.reverb.AlbumDetailsAdapter.albumFiles;
import static com.example.reverb.ApplicationClass.ACTION_NEXT;
import static com.example.reverb.ApplicationClass.ACTION_PLAY;
import static com.example.reverb.ApplicationClass.ACTION_PREVIOUS;
import static com.example.reverb.ApplicationClass.CHANNEL_ID_2;
import static com.example.reverb.PlayListAdapter.pFiles;
import static com.example.reverb.SongAdapter.mFiles;
import static com.example.reverb.SongList.loopBoolean;
import static com.example.reverb.SongList.musicFiles;

public class AudioPlayer extends AppCompatActivity implements ActionPlaying, ServiceConnection {

    //Initializing the views

    TextView song_name,author,seekstart,seekstop;
    ImageView loop,rewind,forward,cover_image,bgim;
    ToggleButton like;

    FloatingActionButton playpausebtn;
    SeekBar seekbar;
    int position = -1;
    static ArrayList<MusicFiles> listFiles =new ArrayList();
    static Uri uri;
   // static MediaPlayer mediaPlayer;
    private Handler handler=new Handler();
    String previousActivity;
    private Thread playPauseThread;
    SwipeListener swipeListener;
    RelativeLayout relativeLayout;
    MusicService musicService;
    MediaSessionCompat mediaSessionCompat;
    static PlayListAdapter pladapter;


    static ArrayList<MusicFiles> playList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

//        BottomNavigationView bottomNavigationView= findViewById(R.id.bot_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.musicitem);
        mediaSessionCompat = new MediaSessionCompat(getBaseContext(),"My Audio");
        initviews();
        like.setChecked(false);
        like.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_favorite_border_24));
        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context context = getApplicationContext();
                if (isChecked) {
                    like.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_favorite_24));


                    ArrayList<String> duplicatep = new ArrayList<>();
                    ArrayList<MusicFiles> tempPlayList = new ArrayList<>();
                    Uri p_uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    String[] projectionp = {
                            MediaStore.Audio.Media.ALBUM,
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media.DURATION,
                            MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.ARTIST
                    };

                    Cursor cursor1 = context.getContentResolver().query(p_uri, projectionp, null, null, null);
                    if (cursor1!= null) {
                        while (cursor1.moveToNext()) {
                            String pAlbum = cursor1.getString(0);
                            String pTitle = cursor1.getString(1);
                            String pDuration = cursor1.getString(2);
                            String pPath = cursor1.getString(3);
                            String pArtist = cursor1.getString(4);
                            MusicFiles playListFiles = new MusicFiles(pPath,pTitle,pArtist,pAlbum,pDuration);
                            Log.e("P_Path :"+pPath,"P_Album :"+pAlbum);
                            tempPlayList.add(playListFiles);

                            if (listFiles.get(position).getPath().equals(pPath)){
                                playList.add(playListFiles);
                                duplicatep.add(pTitle);
                            }
                        }

                        Toast.makeText(getApplicationContext(), "Song added to PlayList", Toast.LENGTH_SHORT)
                                .show();
                        cursor1.close();


                    }


                }

                else
                    like.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_favorite_border_24));
            }
        });
        song_name.setSelected(true);
        getIntentMethod();
        previousActivity=getIntent().getStringExtra("songlist");

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (musicService!=null && fromUser){
                    musicService.seekTo(progress*1000);

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
                if (musicService!=null){
                    int sb_currentposition;
                    sb_currentposition=musicService.getCurrentPosition()/1000;
                    seekbar.setProgress(sb_currentposition);
                    seekstart.setText(formattedTime(sb_currentposition));
                }
                handler.postDelayed(this,1000);

            }
        });

//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId())
//                {
//                    case R.id.musicitem:
//                        return true;
//
//                    case R.id.Video:
//                        startActivity(new Intent(getApplicationContext()
//                                ,VideoPlayer.class));
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
//                    default:
//                    bottomNavigationView.setSelectedItemId(R.id.home);
//
//                }
//
//
//                return false;
//
//
//            }
//        });

        //Forward 10s button

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicService.isPlaying()){
                    musicService.seekTo(musicService.getCurrentPosition()+10000);
                }
            }
        });

        // Rewind 10s button

        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicService.isPlaying()){
                    musicService.seekTo(musicService.getCurrentPosition()-10000);
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

        Intent intent1 = new Intent(this,MusicService.class);
        bindService(intent1,this,BIND_AUTO_CREATE);

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

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    public void playPauseButtonClicked() {
        if (musicService.isPlaying()){
            playpausebtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            showNotification(R.drawable.ic_baseline_play_arrow_24);
            musicService.pause();
            seekbar.setMax(musicService.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService!=null){
                        int sb_currentposition;
                        sb_currentposition=musicService.getCurrentPosition()/1000;
                        seekbar.setProgress(sb_currentposition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
        }
        else {
            showNotification(R.drawable.ic_baseline_pause_24);
            playpausebtn.setImageResource(R.drawable.ic_baseline_pause_24);
            musicService.start();
            seekbar.setMax(musicService.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService!=null){
                        int sb_currentposition;
                        sb_currentposition=musicService.getCurrentPosition()/1000;
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



    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position",-1);
        String sender = getIntent().getStringExtra("sender");

        if(sender != null && sender.equals("albumDetails")){
            listFiles=albumFiles;
        }
        else if (sender!=null && sender.equals("PlaylistFiles")){
            listFiles=pFiles;
        }
        else {
            listFiles = mFiles;
        }
        if (listFiles!= null){
            playpausebtn.setImageResource(R.drawable.ic_baseline_pause_24);
            uri = Uri.parse(listFiles.get(position).getPath());
        }
        Intent i3 = new Intent(this,MusicService.class);
        i3.putExtra("servicePosition",position);
        startService(i3);


    }

    private void metaData(Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        Context context=  getApplicationContext();
        retriever.setDataSource(uri.toString());
        int totalDuration = Integer.parseInt(listFiles.get(position).getDuration())/1000;
        seekstop.setText(formattedTime(totalDuration));
        byte[] art = retriever.getEmbeddedPicture();

        if (art != null){
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(art)
                    .into(cover_image);



        }
        else {
            Glide.with(getApplicationContext())
                    .asBitmap()

                    .load(R.drawable.reverb_logo)
                    .into(cover_image);

        }

//        if (art != null)
//        {
//            Glide.with(context)
//                    .asBitmap()
//                    .load(art)
//                    .transform(new GlideBlurTransformation(context))
//                    .into(bgim);
//        }



    }

//    @Override
//    public void onBackPressed() {
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
//        int selectedItemId = bottomNavigationView.getSelectedItemId();
////        Context cContext = getBaseContext();
////        if(R.id.musicitem == selectedItemId && cCon){
////
////        }
//        if(R.id.home != selectedItemId && position !=-1){
//            startActivity(new Intent(AudioPlayer.this,SongList.class));
//            finish();
//        }
//
//        else if (R.id.home != selectedItemId && position ==-1) {
//            setHomeItem(HomePage.class);
//
//        } else {
//            super.onBackPressed();
//           // System.exit(0);
//
//        }
//
//
//    }

//    public void setHomeItem(Class<HomePage> activity) {
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.home);
//    }


    public void nextSong(){
        if(musicService.isPlaying()){
            musicService.stop();
            musicService.release();
            if (!loopBoolean){
                position=((position+1)% listFiles.size());}
            uri = Uri.parse(listFiles.get(position).getPath());
            musicService.createMediaPlayer(position);
            metaData(uri);
            song_name.setText(listFiles.get(position).getTitle());
            author.setText(listFiles.get(position).getArtist());
            seekbar.setMax(musicService.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService!=null){
                        int sb_currentposition;
                        sb_currentposition=musicService.getCurrentPosition()/1000;
                        seekbar.setProgress(sb_currentposition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            musicService.OnCompleted();
            showNotification(R.drawable.ic_baseline_pause_24);
            playpausebtn.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            musicService.start();
        }
        else {
            musicService.stop();
            musicService.release();
            if (!loopBoolean){
                position=((position+1)% listFiles.size());}
            uri = Uri.parse(listFiles.get(position).getPath());
            musicService.createMediaPlayer(position);
            metaData(uri);
            song_name.setText(listFiles.get(position).getTitle());
            author.setText(listFiles.get(position).getArtist());
            seekbar.setMax(musicService.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService!=null){
                        int sb_currentposition;
                        sb_currentposition=musicService.getCurrentPosition()/1000;
                        seekbar.setProgress(sb_currentposition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            musicService.OnCompleted();
            showNotification(R.drawable.ic_baseline_play_arrow_24);
            playpausebtn.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);


        }


    }
    public void previousSong(){
        if(musicService.isPlaying()){
            musicService.stop();
            musicService.release();
            position=((position-1)<0 ? (listFiles.size() - 1): (position-1));
            uri = Uri.parse(listFiles.get(position).getPath());
            musicService.createMediaPlayer(position);
            metaData(uri);
            song_name.setText(listFiles.get(position).getTitle());
            author.setText(listFiles.get(position).getArtist());
            seekbar.setMax(musicService.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService!=null){
                        int sb_currentposition;
                        sb_currentposition=musicService.getCurrentPosition()/1000;
                        seekbar.setProgress(sb_currentposition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            musicService.OnCompleted();
            showNotification(R.drawable.ic_baseline_pause_24);
            playpausebtn.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            musicService.start();
        }
        else {
            musicService.stop();
            musicService.release();
            position=((position-1)<0 ? (listFiles.size() - 1): (position-1));
            uri = Uri.parse(listFiles.get(position).getPath());
            musicService.createMediaPlayer(position);
            metaData(uri);
            song_name.setText(listFiles.get(position).getTitle());
            author.setText(listFiles.get(position).getArtist());
            seekbar.setMax(musicService.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (musicService!=null){
                        int sb_currentposition;
                        sb_currentposition=musicService.getCurrentPosition()/1000;
                        seekbar.setProgress(sb_currentposition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            musicService.OnCompleted();
            showNotification(R.drawable.ic_baseline_play_arrow_24);
            playpausebtn.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);


        }


    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicService.MyBinder myBinder = (MusicService.MyBinder)service;
        musicService=myBinder.getService();
        musicService.setCallBack(this);
        seekbar.setMax(musicService.getDuration()/1000);
        metaData(uri);
        song_name.setText(listFiles.get(position).getTitle());
        author.setText(listFiles.get(position).getArtist());
        musicService.OnCompleted();
        showNotification(R.drawable.ic_baseline_pause_24);


    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicService=null;

    }

    void  showNotification(int playPauseBtn){
        Intent not_intent = new Intent(this,AudioPlayer.class);
        not_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,not_intent,0);

        Intent prev_intent = new Intent(this,NotificationReceiver.class).setAction(ACTION_PREVIOUS);
        PendingIntent prevPending = PendingIntent.getBroadcast(this,0,prev_intent,PendingIntent.FLAG_UPDATE_CURRENT);


        Intent pause_intent = new Intent(this,NotificationReceiver.class).setAction(ACTION_PLAY);
        PendingIntent pausePending = PendingIntent.getBroadcast(this,0,pause_intent,PendingIntent.FLAG_UPDATE_CURRENT);


        Intent next_intent = new Intent(this,NotificationReceiver.class).setAction(ACTION_NEXT);
        PendingIntent nextPending = PendingIntent.getBroadcast(this,0,next_intent,PendingIntent.FLAG_UPDATE_CURRENT);

            if (listFiles!=null) {
                byte[] pict = null;
                pict = getAlbumArt(listFiles.get(position).getPath());
                Bitmap thumb = null;
                if (pict != null) {
                    thumb = BitmapFactory.decodeByteArray(pict, 0, pict.length);

                } else {
                    thumb = BitmapFactory.decodeResource(getResources(), R.drawable.r_logo);
                }
                NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID_2)
                        .setSmallIcon(playPauseBtn).setLargeIcon(thumb)
                        .setContentTitle(listFiles.get(position).getTitle())
                        .setContentText(listFiles.get(position).getArtist())
                        .addAction(R.drawable.ic_skip_previous_24, "Previous", prevPending)
                        .addAction(playPauseBtn, "Pause", pausePending)
                        .addAction(R.drawable.ic_skip_next_24, "Next", nextPending)
                        .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                .setMediaSession(mediaSessionCompat.getSessionToken()))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setOnlyAlertOnce(true)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(0,notification.build());
            }
    }

    private byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        return art;
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
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent i = new Intent(this,SongList.class);
        startActivity(i);
    }
}