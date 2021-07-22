package com.example.reverb;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import static android.content.Context.MODE_PRIVATE;
import static com.example.reverb.SongList.ARTIST_TO_FRAG;
import static com.example.reverb.SongList.PATH_TO_FRAG;
import static com.example.reverb.SongList.SHOW_MINI_PLAYER;
import static com.example.reverb.SongList.SONGNAME_TO_FRAG;
import static com.example.reverb.SongList.TOTAL_DURATION_TO_FLAG;


public class OnPlayBottomFrag extends BottomSheetDialogFragment implements ServiceConnection,MediaPlayer.OnCompletionListener {

    ImageView nextBtn,albumPic,slider;
    private RelativeLayout mBothhomsheet;
    private BottomSheetBehavior sheetBehavior;
    TextView artist,songname,fseekstart,fseekstop;
    SeekBar fseekbar;
    FloatingActionButton playFloatBtn;
    MusicService musicService;
    ActionPlaying actionPlaying1;
    MediaPlayer mediaPlayer;
    int position=-1;
    public static final String MUSIC_LAST_PLAYED = "LAST_PLAYED";
    public static final String MUSIC_FILE ="STORED_MUSIC";
    public static final String ARTIST_NAME = "ARTIST_NAME";
    public static final String SONG_NAME ="SONG_NAME";
    public static final String TOTAL_DURATION  ="TOTAL_DURATION";
    private Handler handler=new Handler();



    public OnPlayBottomFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_on_play_bottom, container, false);
        artist= view.findViewById(R.id.card_artistname);
        songname=view.findViewById(R.id.card_songname);
        nextBtn = view.findViewById(R.id.card_next);
        albumPic=view.findViewById(R.id.card_image);
        playFloatBtn=view.findViewById(R.id.card_play);
        fseekstart=view.findViewById(R.id.f_seekstart);
        fseekstop=view.findViewById(R.id.f_seekstop);
        fseekbar=view.findViewById(R.id.f_seekbar);

//        slider=view.findViewById(R.id.bottom_slider);
//        mBothhomsheet=view.findViewById(R.id.bottom_sheet_layout);
//        sheetBehavior=BottomSheetBehavior.from(mBothhomsheet);
//        slider.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
//                {
//                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                }
//                else{
//                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//            }
//        });
//        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull @NotNull View bottomSheet, int newState) {
//
//            }
//
//            @Override
//            public void onSlide(@NonNull @NotNull View bottomSheet, float slideOffset) {
//                slider.setRotation(slideOffset*180);
//
//            }
//        });


        fseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (musicService!=null){
                    int sb_currentposition;
                    sb_currentposition=musicService.getCurrentPosition()/1000;
                    fseekbar.setProgress(sb_currentposition);
                    fseekstart.setText(formattedTime(sb_currentposition));
                }
                handler.postDelayed(this,1000);

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicService!=null){
                    musicService.nextSong();
                    if (getActivity()!=null) {

                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MUSIC_LAST_PLAYED,MODE_PRIVATE)
                                .edit();
                        editor.putString(MUSIC_FILE, musicService.musicFiles.get(musicService.position).getPath());
                        editor.putString(ARTIST_NAME, musicService.musicFiles.get(musicService.position).getArtist());
                        editor.putString(SONG_NAME, musicService.musicFiles.get(musicService.position).getTitle());
                        editor.putString(TOTAL_DURATION, musicService.musicFiles.get(musicService.position).getDuration());
                        editor.apply();
                        SharedPreferences preferences = getActivity().getSharedPreferences(MUSIC_LAST_PLAYED,MODE_PRIVATE);
                        String path = preferences.getString(MUSIC_FILE,null);
                        String artistName = preferences.getString(ARTIST_NAME,null);
                        String songName = preferences.getString(SONG_NAME,null);
                        String totalDuration1 = preferences.getString(TOTAL_DURATION,null);
                        if (path!= null){
                            SHOW_MINI_PLAYER = true;
                            PATH_TO_FRAG= path;
                            ARTIST_TO_FRAG=artistName;
                            SONGNAME_TO_FRAG=songName;
                            TOTAL_DURATION_TO_FLAG = totalDuration1;
                        }
                        else {
                            SHOW_MINI_PLAYER =false;
                            PATH_TO_FRAG=null;
                            ARTIST_TO_FRAG=null;
                            SONGNAME_TO_FRAG=null;
                            TOTAL_DURATION_TO_FLAG= totalDuration1;
                        }
                        if (SHOW_MINI_PLAYER){
                            if (PATH_TO_FRAG!=null){
                                byte[] art = getAlbumArt(PATH_TO_FRAG);
                                if (art != null) {
                                    Glide.with(getContext()).load(art)
                                            .into(albumPic);

                                }
                                else {
                                    Glide.with(getContext()).load(R.drawable.ic_baseline_music_note_24)
                                            .into(albumPic);
                                }
                                if (TOTAL_DURATION_TO_FLAG!=null){
                                    int totalDuration2 = Integer.parseInt(TOTAL_DURATION_TO_FLAG)/1000;
                                    fseekstop.setText(formattedTime(totalDuration2));
                               }
                                songname.setText(SONGNAME_TO_FRAG);
                                artist.setText(ARTIST_TO_FRAG);
                                fseekbar.setMax(musicService.getDuration()/1000);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (musicService!=null){
                                            int sb_currentposition;
                                            sb_currentposition=musicService.getCurrentPosition()/1000;
                                            fseekbar.setProgress(sb_currentposition);
                                        }
                                        handler.postDelayed(this,1000);
                                    }
                                });
                                musicService.OnCompleted();
                                musicService.start();
                            }
                        }
                    }
                }
            }
        });
        playFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicService!=null){
                    musicService.playPause();
                    if (musicService.isPlaying()){
                        playFloatBtn.setImageResource(R.drawable.ic_cardpause);
                        fseekbar.setMax(Integer.parseInt(musicService.musicFiles.get(musicService.position).getDuration())/1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (musicService!=null){
                                    int sb_currentposition;
                                    sb_currentposition=musicService.getCurrentPosition()/1000;
                                    fseekbar.setProgress(sb_currentposition);
                                    fseekstart.setText(formattedTime(sb_currentposition));
                                }
                                handler.postDelayed(this,1000);

                            }
                        });

                    }
                    else {
                        playFloatBtn.setImageResource(R.drawable.ic_playcard);
                        fseekbar.setMax(musicService.getDuration()/1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (musicService!=null){
                                    int sb_currentposition;
                                    sb_currentposition=musicService.getCurrentPosition()/1000;
                                    fseekbar.setProgress(sb_currentposition);
                                    fseekstart.setText(formattedTime(sb_currentposition));
                                }
                                handler.postDelayed(this,1000);

                            }
                        });
                    }

                }

            }
        });
        return view;

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


    @Override
    public void onResume() {
        super.onResume();

//        SharedPreferences preferences1 = getActivity().getSharedPreferences(MUSIC_LAST_PLAYED,MODE_PRIVATE);
//        String totalDuration1 = preferences1.getString(TOTAL_DURATION,null);


        if (SHOW_MINI_PLAYER){


            if (PATH_TO_FRAG!=null){
                byte[] art = getAlbumArt(PATH_TO_FRAG);
                if (art != null) {
                    Glide.with(getContext()).load(art)
                            .into(albumPic);

                }
                else {
                    Glide.with(getContext()).load(R.drawable.m1)
                            .into(albumPic);
                }
                if (TOTAL_DURATION_TO_FLAG!=null){
                    int totalDuration2 = Integer.parseInt(TOTAL_DURATION_TO_FLAG)/1000;
                    fseekstop.setText(formattedTime(totalDuration2));
                }

                songname.setText(SONGNAME_TO_FRAG);
                artist.setText(ARTIST_TO_FRAG);
                Intent m_intent = new Intent(getContext(),MusicService.class);
                if (getContext()!=null){
                    getContext().bindService(m_intent,this, Context.BIND_AUTO_CREATE);
                }

            }


        }
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        if (getContext()!=null){
//            getContext().unbindService(this);
//        }
//    }


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (getContext()!=null){
//            getContext().unbindService(this);
//        }
//    }musicService.musicFiles.get(musicService.position)

    private byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
//        int totalDuration = Integer.parseInt(musicService.musicFiles.get(musicService.position).getDuration());
//        fseekstop.setText(formattedTime(totalDuration));
        byte[] art = retriever.getEmbeddedPicture();
        return art;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicService.MyBinder binder = (MusicService.MyBinder) service;
        musicService=binder.getService();
        fseekbar.setMax(Integer.parseInt(musicService.musicFiles.get(musicService.position).getDuration())/1000);


    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicService=null;

    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (actionPlaying1 !=null) {
            actionPlaying1.nextSong();
            if (mediaPlayer != null) {
                musicService.createMediaPlayer(position);
                mediaPlayer.start();
                musicService.OnCompleted();
            }
        }

    }

}