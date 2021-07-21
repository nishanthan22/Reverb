package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.MediaRouteButton;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.concurrent.TimeUnit;

import static com.example.reverb.AdapterVideoList.videosList;
import static com.example.reverb.AlbumDetailsAdapter.albumFiles;
import static com.example.reverb.FolderDetailsAdapter.folder_details;
import static com.example.reverb.SongAdapter.mFiles;
import static com.example.reverb.allvideos.folder;

public class VideoPlayer extends AppCompatActivity {

    long videoId;
    //Uri uri;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private MediaSource mediaSource;
    static ArrayList<ModelVideo> videosFiles =new ArrayList();
    ImageView vol,vol_center,br_icon,br_center;
    TextView volu_center_text,br_center_text;
    ProgressBar vol_slider,br_slider;
    LinearLayout vol_layout,vol_text_layout,br_layout,br_text_layout;
    AudioManager audioManager;
    RelativeLayout root;
    private int maxVolume,currentVolume;
    //GestureDetector gestureDetector;
    private int playerWidth,playerHeight;
    private int GESTURE_FLAG;
    private boolean firstScroll = true;
    private int GESTURE_MODIFY_VOLUME,GESTURE_MODIFY_BRIGHTNESS;
    private float STEP_VOLUME ;
    float mBrightness;
    SwipeListener swipeListener;
//    private Display display;
//    private Point size;
//    private int sWidth;
//    private int sHeight;
//    private Object ControlsMode;
//    double baseX,baseY;
//    private long diffY,diffX,calculated;
//    private ContentResolver cResolver;
//    private Window window;
//    private int brightness;
//    private int mediavolume;
    //PlayerView playerView;
   // SimpleExoPlayer simpleExoPlayer;
    //int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        //videoId = getIntent().getExtras().getLong("videoId");
        int pos=getIntent().getIntExtra("position",-1);
        String sender = getIntent().getStringExtra("sender");
        if(sender != null && sender.equals("folder_files"))
        {
            videosFiles=folder_details;
        }
        else {
            videosFiles= videosList;}


         //uri = videosFiles.get(pos).getData();
         videoId = videosFiles.get(pos).getId();
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


         initializeViews();
        swipeListener = new SwipeListener(root);
//        ViewTreeObserver viewTreeObserver= root.getViewTreeObserver();
//        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                playerWidth = root.getWidth();
//                playerHeight = root.getHeight();
//            }
//        });
//         display= getWindowManager().getDefaultDisplay();
//         size = new Point();
//         sWidth = size.x;
//         sHeight = size.y;


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
        root=findViewById(R.id.root_layout);

//        root.setLongClickable(true);
//        root.setOnTouchListener((View.OnTouchListener) this);
        //slider image n progress
        vol=findViewById(R.id.volume);
        vol_slider=findViewById(R.id.volume_slider);
        //center image n text
        vol_center=findViewById(R.id.vol1);
        volu_center_text=findViewById(R.id.vol_text);
        //layouts
        vol_layout=findViewById(R.id.volume_container);
        vol_text_layout=findViewById(R.id.vol_center_text);
        //audioManager
        audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume= audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mBrightness=getWindow().getAttributes().screenBrightness;
        //br slider and icon
        br_icon=findViewById(R.id.brightness_icon);
        br_slider=findViewById(R.id.brightness_slider);
        //br center image n text
        br_center=findViewById(R.id.brightness_image);
        br_center_text=findViewById(R.id.brightness_center_text);
        //br layouts
        br_layout=findViewById(R.id.brightness_container);
        br_text_layout=findViewById(R.id.bright_center_layout);
//        gestureDetector= new GestureDetector(VideoPlayer.this,gestureListener);
//        gestureDetector.setIsLongpressEnabled(true);
        STEP_VOLUME= (float) 1.0;
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

    private class SwipeListener implements  View.OnTouchListener {
        GestureDetector gestureDetector;

        SwipeListener(View view) {
//            int threshold = 100;
//            int velocity_threshold = 100;

            GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
                @Override
             public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i("OnTouch", "OnScroll");
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();
           if (firstScroll) {
               if (mOldX > playerWidth * 3.0 / 5) {
                    vol_layout.setVisibility(View.VISIBLE);
                    vol_text_layout.setVisibility(View.VISIBLE);
                    br_layout.setVisibility(View.GONE);
                    br_text_layout.setVisibility(View.GONE);
                    GESTURE_FLAG = GESTURE_MODIFY_VOLUME;
                } else if (mOldX < playerWidth * 2.0 / 5) {
                    vol_layout.setVisibility(View.GONE);
                    vol_text_layout.setVisibility(View.GONE);
                    br_layout.setVisibility(View.VISIBLE);
                    br_text_layout.setVisibility(View.VISIBLE);
                    GESTURE_FLAG = GESTURE_MODIFY_BRIGHTNESS;                }
            }
            if (GESTURE_FLAG == GESTURE_MODIFY_VOLUME) {
                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if (Math.abs(distanceY) > Math.abs(distanceX)) {
                    if (distanceY >= DensityUtil.dip2px(VideoPlayer.this, STEP_VOLUME)) {
                        if (currentVolume < maxVolume) {
                            currentVolume++;
                        }
                        //image change
                    } else if (distanceY <= -DensityUtil.dip2px(VideoPlayer.this, STEP_VOLUME)) {
                        if (currentVolume > 0) {
                            currentVolume--;
                            if (currentVolume == 0) {
                                vol.setImageResource(R.drawable.mute);
                            }
                        }
                    }
                    int percentage = (currentVolume * 100) / maxVolume;
                    volu_center_text.setText("" + percentage);
                    vol_slider.setProgress((int) percentage);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);

                }
            } else if (GESTURE_FLAG == GESTURE_MODIFY_BRIGHTNESS) {
                if (mBrightness < 0) {
                    mBrightness = getWindow().getAttributes().screenBrightness;
                    if (mBrightness <= 0.00f)
                        mBrightness = 0.50f;
                    if (mBrightness <= 0.01f)
                        mBrightness = 0.01f;
                }
                WindowManager.LayoutParams lpa = getWindow().getAttributes();
                lpa.screenBrightness = mBrightness + (mOldY - y) / playerHeight;
                if (lpa.screenBrightness > 1.0f)
                    lpa.screenBrightness = 1.0f;
                else if (lpa.screenBrightness < 0.01f)
                    lpa.screenBrightness = 0.01f;
                getWindow().setAttributes(lpa);
                int pec_bright = (int) (lpa.screenBrightness * 100);
                br_slider.setProgress((int) pec_bright);
            }

            firstScroll=false;
            return false;
        }
            };
            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP)
       {
           GESTURE_FLAG=0;
           vol_layout.setVisibility(View.GONE);
           vol_text_layout.setVisibility(View.GONE);
           br_layout.setVisibility(View.GONE);
           br_text_layout.setVisibility(View.GONE);

       }
       Log.i("onTouch",v.toString());
       Log.i("OnTouch","OnTouch");
       return gestureDetector.onTouchEvent(event);
        }

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_UP)
//       {
//           GESTURE_FLAG=0;
//           vol_layout.setVisibility(View.GONE);
//           vol_text_layout.setVisibility(View.GONE);
//           br_layout.setVisibility(View.GONE);
//           br_text_layout.setVisibility(View.GONE);
//
//       }
//      // Log.i("onTouch",v.toString());
//       Log.i("OnTouch","OnTouch");
//       return gestureDetector.onTouchEvent(event);
//        //return super.onTouchEvent(event);
//    }
//    //    public boolean onTouch(View v, MotionEvent event)
////    {
////       if(event.getAction() == MotionEvent.ACTION_UP)
////       {
////           GESTURE_FLAG=0;
////           vol_layout.setVisibility(View.GONE);
////           vol_text_layout.setVisibility(View.GONE);
////           br_layout.setVisibility(View.GONE);
////           br_text_layout.setVisibility(View.GONE);
////
////       }
////       Log.i("onTouch",v.toString());
////       Log.i("OnTouch","OnTouch");
////       return gestureDetector.onTouchEvent(event);
////    }
//    GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            Log.i("OnTouch", "OnScroll");
//            float mOldX = e1.getX(), mOldY = e1.getY();
//            int y = (int) e2.getRawY();
//            if (firstScroll) {
//                if (mOldX > playerWidth * 3.0 / 5) {
//                    vol_layout.setVisibility(View.VISIBLE);
//                    vol_text_layout.setVisibility(View.VISIBLE);
//                    br_layout.setVisibility(View.GONE);
//                    br_text_layout.setVisibility(View.GONE);
//                    GESTURE_FLAG = GESTURE_MODIFY_VOLUME;
//                } else if (mOldX < playerWidth * 2.0 / 5) {
//                    vol_layout.setVisibility(View.GONE);
//                    vol_text_layout.setVisibility(View.GONE);
//                    br_layout.setVisibility(View.VISIBLE);
//                    br_text_layout.setVisibility(View.VISIBLE);
//                    GESTURE_FLAG = GESTURE_MODIFY_BRIGHTNESS;
//                }
//            }
//            if (GESTURE_FLAG == GESTURE_MODIFY_VOLUME) {
//                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//                if (Math.abs(distanceY) > Math.abs(distanceX)) {
//                    if (distanceY >= DensityUtil.dip2px(VideoPlayer.this, STEP_VOLUME)) {
//                        if (currentVolume < maxVolume) {
//                            currentVolume++;
//                        }
//                        //image change
//                    } else if (distanceY <= -DensityUtil.dip2px(VideoPlayer.this, STEP_VOLUME)) {
//                        if (currentVolume > 0) {
//                            currentVolume--;
//                            if (currentVolume == 0) {
//                                vol.setImageResource(R.drawable.mute);
//                            }
//                        }
//                    }
//                    int percentage = (currentVolume * 100) / maxVolume;
//                    volu_center_text.setText("" + percentage);
//                    vol_slider.setProgress((int) percentage);
//                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
//
//                }
//            } else if (GESTURE_FLAG == GESTURE_MODIFY_BRIGHTNESS) {
//                if (mBrightness < 0) {
//                    mBrightness = getWindow().getAttributes().screenBrightness;
//                    if (mBrightness <= 0.00f)
//                        mBrightness = 0.50f;
//                    if (mBrightness <= 0.01f)
//                        mBrightness = 0.01f;
//                }
//                WindowManager.LayoutParams lpa = getWindow().getAttributes();
//                lpa.screenBrightness = mBrightness + (mOldY - y) / playerHeight;
//                if (lpa.screenBrightness > 1.0f)
//                    lpa.screenBrightness = 1.0f;
//                else if (lpa.screenBrightness < 0.01f)
//                    lpa.screenBrightness = 0.01f;
//                getWindow().setAttributes(lpa);
//                int pec_bright = (int) (lpa.screenBrightness * 100);
//                br_slider.setProgress((int) pec_bright);
//            }
//
//            firstScroll=false;
//            return false;
//        }
//    };


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        boolean intLeft = false,intRight = false,intBottom,intTop;
//
//        switch (event.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//                boolean tested_ok = false;
//                if(event.getX()<(sWidth/2))
//                {
//                    intLeft=true;
//                    intRight=false;
//                }
//                else if (event.getX()>(sWidth/2))
//                {
//                    intLeft=false;
//                    intRight=true;
//                }
//                int upperLimit = (sWidth/4)+100;
//                int lowerlimit = ((sWidth/4)+3 )-150;
//                if(event.getY()<upperLimit)
//                {
//                    intBottom=false;
//                    intTop=true;
//                }
//                else if(event.getY()>lowerlimit)
//                {
//                    intBottom = true;
//                    intTop=false;
//                }
//                else
//                {
//                    intBottom=false;
//                    intTop=false;
//                }
//                 diffX = 0;
//                 calculated = 0;
//                String seekdr = String.format("%02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(diffX) -
//                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffX)),
//                        TimeUnit.MILLISECONDS.toSeconds(diffX) -
//                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diffX)));
//
//                //touch started
//                baseX = event.getX();
//                baseY = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                boolean screen_swipe_move = true;
//                Object controlsState;
//                diffX =(long) (Math.ceil(event.getX() - baseX));
//                diffY = (long) (Math.ceil(event.getY() - baseY));
//                double brightnessSpeed = 0.05;
//                tested_ok=true;
//                if(Math.abs(diffY) > Math.abs(diffX))
//                {
//                    if(intLeft)
//                    {
//                        cResolver = getContentResolver();
//                        window = getWindow();
//                        try {
//                            Settings.System.putInt(cResolver,Settings.System.SCREEN_BRIGHTNESS_MODE,Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
//                            brightness = Settings.System.getInt(cResolver,Settings.System.SCREEN_BRIGHTNESS);
//                        } catch (Settings.SettingNotFoundException e){
//                            e.printStackTrace();
//                        }
//                        int new_brightness = (int) (brightness - (diffX*brightnessSpeed));
//                        if(new_brightness>250)
//                        {
//                            new_brightness=250;
//                        } else if (new_brightness<1)
//                        {
//                            new_brightness=1;
//                        }
//                        double brightPrec = Math.ceil((((double) new_brightness/(double) 250)*(double) 100));
//                        br_layout.setVisibility(View.VISIBLE);
//                        br_text_layout.setVisibility(View.VISIBLE);
//                        br_slider.setProgress((int) brightPrec);
//                        br_center_text.setText(""+(int) brightPrec);
//                        Settings.System.putInt(cResolver,Settings.System.SCREEN_BRIGHTNESS,(new_brightness));
//                        WindowManager.LayoutParams layoutParas= window.getAttributes();
//                        layoutParas.screenBrightness = brightness/(float) 255;
//                        window.setAttributes(layoutParas);
//                    } else if(intRight){
//                        vol_text_layout.setVisibility(View.VISIBLE);
//                        vol_layout.setVisibility(View.VISIBLE);
//                        mediavolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//                        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//                        double cal = (double) diffY *((double)maxVol/(double)(sHeight*4));
//                        int newMediaVolume = mediavolume -(int) cal;
//                        if(newMediaVolume > maxVol){
//                            newMediaVolume=maxVol;
//                        }else if(newMediaVolume<1)
//                        {
//                            newMediaVolume=0;
//                        }
//                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,newMediaVolume,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
//                        double volPerc = Math.ceil((((double) newMediaVolume /(double) maxVol) * (double) 100));
//                        volu_center_text.setText(""+(int) volPerc);
//                        vol_slider.setProgress((int) volPerc);
//
//
//
//
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                screen_swipe_move = false;
//                tested_ok=false;
//                vol_layout.setVisibility(View.GONE);
//                vol_text_layout.setVisibility(View.GONE);
//                br_layout.setVisibility(View.GONE);
//                br_text_layout.setVisibility(View.GONE);
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
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