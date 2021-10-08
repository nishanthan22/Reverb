package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

//import static com.example.reverb.R.drawable.ic_baseline_play_circle_filled_24;
//import static com.example.reverb.R.drawable.playbtnn;
//import static com.example.reverb.viewpageradapter.ViewHolder.playbtn;
//import static com.example.reverb.viewpageradapter.ViewHolder.pausebtn;
//import static com.example.reverb.viewpageradapter.play;
import java.util.ArrayList;

import static com.example.reverb.SongAdapter.mFiles;
import static com.example.reverb.SongList.musicFiles;
import static com.example.reverb.AlbumDetailsAdapter.albumFiles;


public class swipe extends AppCompatActivity {
    static ViewPager2 viewPager2;
    static MediaPlayer mediaplayer;
    RelativeLayout r;
    ImageView i;

    private Thread playPauseTh;
    static ArrayList<MusicFiles> mfile =new ArrayList();
  //  boolean play = true;
    //Animation Fade;
    //ActionPlaying actionPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        //r=findViewById(R.id.audio_relative1);
        //i=findViewById(R.id.play1);
        //i.setImageResource(R.drawable.c);

        viewPager2 = findViewById(R.id.viewpager21);
       //viewPager2.setLayoutTransition(android.transition.Fade);
        int pos = getIntent().getIntExtra("position",-1);
        String sender = getIntent().getStringExtra("sender");
        if(sender != null && sender.equals("albumDetails")){
            mfile=albumFiles;}
        else {
            mfile=musicFiles;}




        if (!(mfile.size() < 1)) {
           // int i = viewPager2.getCurrentItem();
            viewpageradapter v2 = new viewpageradapter(this, mfile);


            viewPager2.setAdapter(v2);
           viewPager2.setCurrentItem(pos);
//        if(mediaplayer!=null){
//            mediaplayer.stop();
//            mediaplayer.release();
//            init(pos);}
            init(pos);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {


                @Override
                // This method is triggered when there is any scrolling activity for the current page
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                }

                // triggered when you select a new page
                @Override
                public void onPageSelected(int position) {

//                    pausebtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            playbtn.setVisibility(View.VISIBLE);
//                            pausebtn.setVisibility(View.INVISIBLE);
//                            mediaplayer.pause();
//                        }
//                    })
                    super.onPageSelected(position);
//                    if(mediaplayer.isPlaying())
//                    {
//                        pausebtn.setVisibility(View.VISIBLE);
//                        playbtn.setVisibility(View.INVISIBLE);
//                    }
//                    pausebtn.setVisibility(View.VISIBLE);
//                    playbtn.setVisibility(View.INVISIBLE);
//                    play=true;
//
//                    pausebtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if(play)
//                            {
//                                play=false;
//                                pausebtn.setVisibility(View.INVISIBLE);
//                                playbtn.setVisibility(View.VISIBLE);
//                                mediaplayer.pause();
//                            }
//                            else {
//                                play=true;
//                                pausebtn.setVisibility(View.VISIBLE);
//                                playbtn.setVisibility(View.INVISIBLE);
//                                mediaplayer.start();
//                            }
//                        }
//                    });
//
//                    playbtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if(play)
//                            {
//                                play=false;
//                                pausebtn.setVisibility(View.INVISIBLE);
//                               playbtn.setVisibility(View.VISIBLE);
//                                mediaplayer.pause();
//                            }
//                            else {
//                                play=true;
//                                pausebtn.setVisibility(View.VISIBLE);
//                                playbtn.setVisibility(View.INVISIBLE);
//                                mediaplayer.start();
//                            }
//                        }
//                    });



//                    playpausebtn.setImageResource(R.drawable.c);
//                    playpausebtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            playpausebtn.setImageResource(ic_baseline_play_circle_filled_24);
//                            mediaplayer.pause();
//                        }
//                    });
                    init(viewPager2.getCurrentItem());

                }

                // triggered when there is
                // scroll state will be changed
                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });


        }

//        position = viewPager2.getCurrentItem()
//        Intent i = new Intent(this,MusicService.class);
//        i.putExtra("servicePosition",position);
//        startService(i);
    }



    public void init(int currentpos)
    {
        try {
            if(mediaplayer.isPlaying())
            {
                mediaplayer.stop();
                mediaplayer.reset();

//                playpausebtn.setImageResource(R.drawable.c);
//                playpausebtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        playpausebtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
//                        mediaplayer.pause();
//                    }
//                });
//
            }

        }catch (Exception e)
        {

        }



        try{
            mediaplayer = new MediaPlayer();

             Uri uri = Uri.parse(mfile.get(currentpos).getPath());
             mediaplayer.setDataSource(this,uri);
             mediaplayer.prepareAsync();
             mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                 @Override
                 public void onPrepared(MediaPlayer mp) {
                     mediaplayer.start();
                 }
             });

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                viewPager2.setCurrentItem(currentpos+1);
                init(viewPager2.getCurrentItem());
            }
        });
    }
//    public void playpausebutton(View v)
//    {
//        if(play)
//        {
//            play=false;
//            pausebtn.setVisibility(View.INVISIBLE);
//            playbtn.setVisibility(View.VISIBLE);
//            mediaplayer.pause();
//        }
//        else {
//            play=true;
//            pausebtn.setVisibility(View.VISIBLE);
//            playbtn.setVisibility(View.INVISIBLE);
//            mediaplayer.start();
//        }
//
//    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent i = new Intent(this,SongList.class);
        startActivity(i);
    }
}
