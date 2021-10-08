package com.example.reverb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.android.exoplayer2.util.Log;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.example.reverb.SongList.loopBoolean;
import static com.example.reverb.swipe.mediaplayer;
import static com.example.reverb.swipe.viewPager2;

public class viewpageradapter extends RecyclerView.Adapter<viewpageradapter.ViewHolder>{
    private Context musicContext;
    static ArrayList<MusicFiles> musicFiles;
    int pos;
    static Uri uri;
     boolean play = true;
     MusicService musicService;
     private Thread playPauseT;

    viewpageradapter(Context musicContext, ArrayList<MusicFiles> musicFiles) {
        this.musicFiles = musicFiles;
        this.musicContext = musicContext;
       // this.pos=pos;
    }

    @NonNull
    @NotNull
    @Override
    public viewpageradapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(musicContext).inflate(R.layout.swipe1, parent, false);

        return new viewpageradapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull viewpageradapter.ViewHolder holder, int position) {

        holder.song_name.setText(musicFiles.get(position).getTitle());
        holder.author.setText(musicFiles.get(position).getArtist());
        int totalDuration = Integer.parseInt(musicFiles.get(position).getDuration())/1000;
        holder.seekstop1.setText(formattedTime(totalDuration));

        uri = Uri.parse(musicFiles.get(position).getPath());
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(uri.toString());
        byte[] album = mmr.getEmbeddedPicture();

        if (album != null)
        {
            holder.cover_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Bitmap bitmap = BitmapFactory.decodeByteArray(album,0,album.length);
            Bitmap blurredBitmap = BlurBuilder.blur(musicContext,bitmap);
            Drawable d = new BitmapDrawable(blurredBitmap);
            holder.v1.setBackground(d);
            Glide.with(musicContext)
                    .asBitmap()
                    .load(album)
                    .into(holder.cover_image);
        }
        else if(album==null){
            holder.cover_image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.v1.setBackgroundColor(Color.BLACK);
            Glide.with(musicContext)
                    .asBitmap()
                    .load(R.drawable.aa)
                    .into(holder.cover_image);

        }
//        holder.pausebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(play) {
//                    play = false;
//                    holder.pausebtn.setVisibility(View.INVISIBLE);
//                    holder.playbtn.setVisibility(View.VISIBLE);
//                    mediaplayer.pause();
//                }}});
        playPauseT=new Thread(){
            @Override
            public void run() {
                super.run();
                holder.pausebtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                  if(play)
                    {
                    play=false;
                    holder.pausebtn.setVisibility(View.INVISIBLE);
                    holder.playbtn.setVisibility(View.VISIBLE);

                    mediaplayer.pause();
                        Log.e("I N S I D E   T H R E A D","Read");
                    holder.seekbar1.setMax(mediaplayer.getDuration()/1000);
                    ((AppCompatActivity) musicContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mediaplayer!=null){
                                int sb_currentposition;
                                sb_currentposition=mediaplayer.getCurrentPosition()/1000;
                                holder.seekbar1.setProgress(sb_currentposition);
                            }
                            //handler.postDelayed(this,1000);
                            holder.handler1.postDelayed(this,1000);
                        }
                    });
                }
                  else {
                    play=true;
                    holder.pausebtn.setVisibility(View.VISIBLE);
                    holder.playbtn.setVisibility(View.INVISIBLE);
                    mediaplayer.start();
                      Log.e("I N S I D E   T H R E A D","Read");
                    holder.seekbar1.setMax(mediaplayer.getDuration()/1000);
                    ((AppCompatActivity) musicContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mediaplayer!=null){
                                int sb_currentposition;
                                sb_currentposition=mediaplayer.getCurrentPosition()/1000;
                                holder.seekbar1.setProgress(sb_currentposition);
                            }
                            holder.handler1.postDelayed(this,1000);
                        }
                    });
                  }


               holder.playbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                   public void onClick(View v) {
                if(play)
                {
                    play=false;
                    holder.pausebtn.setVisibility(View.INVISIBLE);
                    holder.playbtn.setVisibility(View.VISIBLE);
                    mediaplayer.pause();
                    holder.seekbar1.setMax(mediaplayer.getDuration()/1000);
                    ((AppCompatActivity) musicContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mediaplayer!=null){
                                int sb_currentposition;
                                sb_currentposition=mediaplayer.getCurrentPosition()/1000;
                                holder.seekbar1.setProgress(sb_currentposition);
                            }
                            //handler.postDelayed(this,1000);
                            holder.handler1.postDelayed(this,1000);
                        }
                    });

                }
                else {
                    play=true;
                    holder.pausebtn.setVisibility(View.VISIBLE);
                    holder.playbtn.setVisibility(View.INVISIBLE);
                    mediaplayer.start();
                    holder.seekbar1.setMax(mediaplayer.getDuration()/1000);
                    ((AppCompatActivity) musicContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mediaplayer!=null){
                                int sb_currentposition;
                                sb_currentposition=mediaplayer.getCurrentPosition()/1000;
                                holder.seekbar1.setProgress(sb_currentposition);
                            }
                            holder.handler1.postDelayed(this,1000);
                        }
                    });



                }
            }
        });

            }
        });


//                holder.playPauseButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //playPauseButtonClicked();
//
//                        if (mediaplayer.isPlaying()){
//                            holder.playPauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
//                            //showNotification(R.drawable.ic_baseline_play_arrow_24);
//                            mediaplayer.pause();
//                            holder.seekbar1.setMax(mediaplayer.getDuration()/1000);
//                            ((AppCompatActivity) musicContext).runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (mediaplayer!=null){
//                                        int sb_currentposition;
//                                        sb_currentposition=mediaplayer.getCurrentPosition()/1000;
//                                        holder.seekbar1.setProgress(sb_currentposition);
//                                    }
//                                    //handler.postDelayed(this,1000);
//                                    holder.handler1.postDelayed(this,1000);
//                                }
//                            });
//                        }
//                        else {
//                           // showNotification(R.drawable.ic_baseline_pause_24);
//                            holder.playPauseButton.setImageResource(R.drawable.ic_baseline_pause_24);
//                            mediaplayer.start();
//                            holder.seekbar1.setMax(mediaplayer.getDuration()/1000);
//                            ((AppCompatActivity) musicContext).runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (mediaplayer!=null){
//                                        int sb_currentposition;
//                                        sb_currentposition=mediaplayer.getCurrentPosition()/1000;
//                                        holder.seekbar1.setProgress(sb_currentposition);
//                                    }
//                                    holder.handler1.postDelayed(this,1000);
//                                }
//                            });
//                        }
//                    }
//                });
            }
        };
        playPauseT.start();


        //seekbar

        holder.seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaplayer!=null && fromUser){
                    mediaplayer.seekTo(progress*1000);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ((AppCompatActivity) musicContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaplayer!=null){
                    int sbcurrentposition;
                    sbcurrentposition=mediaplayer.getCurrentPosition()/1000;
                    holder.seekbar1.setProgress(sbcurrentposition);
                    holder.seekstart1.setText(formattedTime(sbcurrentposition));
                }
                holder.handler1.postDelayed(this,1000);
                //context.refreshInbox();
            }
        });

        holder.forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaplayer.isPlaying()){
                    mediaplayer.seekTo(mediaplayer.getCurrentPosition()+10000);
                }
            }
        });

        holder.rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaplayer.isPlaying()){
                    mediaplayer.seekTo(mediaplayer.getCurrentPosition()-10000);
                }
            }
        });

        holder.loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loopBoolean){
                    loopBoolean = false;
                    holder.loop.setImageResource(R.drawable.ic_baseline_loop_24);

                }
                else {
                    loopBoolean=true;
                    //on
                    holder.loop.setImageResource(R.drawable.loop_on);
                }
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
               // holder.playPauseButton.setVisibility(View.VISIBLE);
                holder.playbtn.setVisibility(View.INVISIBLE);
                play=true;
                super.onPageSelected(position);
            }
        });
    }




    @Override
    public int getItemCount() {
        return musicFiles.size();
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView song_name,author,seekstart1,seekstop1;
         ImageView loop,rewind,forward,cover_image;
         ToggleButton like;
         View v1;
         FloatingActionButton playPauseButton;
         private Thread pThread;

         private android.os.Handler handler1=new android.os.Handler();
        ImageView playbtn,pausebtn;
         SeekBar seekbar1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            song_name=itemView.findViewById(R.id.songname1);
            author=itemView.findViewById(R.id.author1);
            seekstart1=itemView.findViewById(R.id.seekstart1);
            seekstop1=itemView.findViewById(R.id.seekstop1);
            loop=itemView.findViewById(R.id.loop1);
            rewind=itemView.findViewById(R.id.rewind1);
            forward=itemView.findViewById(R.id.forward1);
            like=itemView.findViewById(R.id.like1);
            playbtn=itemView.findViewById(R.id.play1);
            pausebtn=itemView.findViewById(R.id.pause1);
            seekbar1=itemView.findViewById(R.id.seekbar1);
            cover_image=itemView.findViewById(R.id.cover_image1);
            v1=itemView.findViewById(R.id.view1);
           // playPauseButton=itemView.findViewById(R.id.pause1);


//            pausebtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    playbtn.setVisibility(View.VISIBLE);
//                    pausebtn.setVisibility(View.INVISIBLE);
//                    mediaplayer.pause();
//                }
//            });



//            if(mediaplayer.pause())
//            {
//                playpausebtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
//                playpausebtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        playpausebtn.setImageResource(R.drawable.c);
//
//                    }
//                });
//
//            }



        }
    }
}
