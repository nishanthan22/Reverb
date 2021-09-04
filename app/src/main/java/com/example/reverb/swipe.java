package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static com.example.reverb.R.drawable.c;
import static com.example.reverb.SongList.musicFiles;


public class swipe extends AppCompatActivity {
    private ViewPager2 viewPager2;
    static MediaPlayer mediaplayer;
    RelativeLayout r;
    ImageView i;
    //ActionPlaying actionPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        r=findViewById(R.id.audio_relative1);
        i=findViewById(R.id.play1);
        //i.setImageResource(R.drawable.c);

        viewPager2 = findViewById(R.id.viewpager21);
        int pos = getIntent().getIntExtra("position",-1);

        if (!(musicFiles.size() < 1)) {
            viewpageradapter v2 = new viewpageradapter(this, musicFiles);

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


                    super.onPageSelected(position);
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
//                i.setImageResource(R.drawable.c);
//                i.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                        public void onClick(View v) {
//                            i.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
//                            mediaplayer.pause();
//                        }
//                    });
//
            }

        }catch (Exception e)
        {

        }
        try{
            mediaplayer = new MediaPlayer();

             Uri uri = Uri.parse(musicFiles.get(currentpos).getPath());
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
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent i = new Intent(this,SongList.class);
        startActivity(i);
    }
}
