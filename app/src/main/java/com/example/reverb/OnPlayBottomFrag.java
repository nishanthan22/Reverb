package com.example.reverb;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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


public class OnPlayBottomFrag extends BottomSheetDialogFragment implements ServiceConnection {

    ImageView nextBtn,albumPic;  //slider;
    //private RelativeLayout mBothhomsheet;
    //private BottomSheetBehavior sheetBehavior;
    TextView artist,songname;
    FloatingActionButton playFloatBtn;
    MusicService musicService;
    public static final String MUSIC_LAST_PLAYED = "LAST_PLAYED";
    public static final String MUSIC_FILE ="STORED_MUSIC";
    public static final String ARTIST_NAME = "ARTIST_NAME";
    public static final String SONG_NAME ="SONG_NAME";



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
                        editor.apply();
                        SharedPreferences preferences = getActivity().getSharedPreferences(MUSIC_LAST_PLAYED,MODE_PRIVATE);
                        String path = preferences.getString(MUSIC_FILE,null);
                        String artistName = preferences.getString(ARTIST_NAME,null);
                        String songName = preferences.getString(SONG_NAME,null);
                        if (path!= null){
                            SHOW_MINI_PLAYER = true;
                            PATH_TO_FRAG= path;
                            ARTIST_TO_FRAG=artistName;
                            SONGNAME_TO_FRAG=songName;
                        }
                        else {
                            SHOW_MINI_PLAYER =false;
                            PATH_TO_FRAG=null;
                            ARTIST_TO_FRAG=null;
                            SONGNAME_TO_FRAG=null;
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

                                songname.setText(SONGNAME_TO_FRAG);
                                artist.setText(ARTIST_TO_FRAG);

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
                    }
                    else {
                        playFloatBtn.setImageResource(R.drawable.ic_playcard);
                    }

                }

            }
        });





        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
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
//    }

    private byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        return art;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicService.MyBinder binder = (MusicService.MyBinder) service;
        musicService=binder.getService();

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicService=null;

    }
}