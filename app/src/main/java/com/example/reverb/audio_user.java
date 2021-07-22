package com.example.reverb;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static android.content.Context.MODE_PRIVATE;

import static com.example.reverb.AudioPlayer.playList;
import static com.example.reverb.OnPlayBottomFrag.ARTIST_NAME;
import static com.example.reverb.OnPlayBottomFrag.MUSIC_FILE;
import static com.example.reverb.OnPlayBottomFrag.SONG_NAME;
import static com.example.reverb.SongList.ARTIST_TO_FRAG;
import static com.example.reverb.SongList.SHOW_MINI_PLAYER;
import static com.example.reverb.SongList.SONGNAME_TO_FRAG;
import static com.example.reverb.SongList.TOTAL_DURATION_TO_FLAG;


public class audio_user extends Fragment implements ServiceConnection {
    RecyclerView favrecV;
    static PlayListAdapter pladapter;
    MusicService musicService;
    TextView rp_songname,rp_author;
    ImageView rp_pic;
    public static final String MUSIC_LAST_PLAYED = "LAST_PLAYED";
    public static String PATH_TO_FRAG =null;



    public audio_user() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_audio_user, container, false);
        favrecV=view.findViewById(R.id.fav_recV);
        rp_pic=view.findViewById(R.id.rp_pic);
        rp_author=view.findViewById(R.id.rp_author);
        rp_songname=view.findViewById(R.id.rp_name);
        if (playList!=null){
            pladapter = new PlayListAdapter(getContext(),playList);
            favrecV.setAdapter(pladapter);
            favrecV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            SpacingItemDecorator itemDecorator= new SpacingItemDecorator(32);
            favrecV.addItemDecoration(itemDecorator);
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferences = getActivity().getSharedPreferences(MUSIC_LAST_PLAYED,MODE_PRIVATE);
        String path = preferences.getString(MUSIC_FILE,null);
        String artist = preferences.getString(ARTIST_NAME,null);
        String song_name = preferences.getString(SONG_NAME,null);
        if (path!= null){
            SHOW_MINI_PLAYER = true;
            PATH_TO_FRAG= path;
            ARTIST_TO_FRAG=artist;
            SONGNAME_TO_FRAG=song_name;
        }
        else {
            SHOW_MINI_PLAYER =false;
            PATH_TO_FRAG=null;
            ARTIST_TO_FRAG=null;
            SONGNAME_TO_FRAG=null;
        }
        if (SHOW_MINI_PLAYER) {
            if (PATH_TO_FRAG != null) {
                Uri uri = Uri.parse(PATH_TO_FRAG);

                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(uri.toString());
                byte[] art = mmr.getEmbeddedPicture();
                if (art != null) {
                    Glide.with(getContext()).load(art)
                            .into(rp_pic);

                } else {
                    Glide.with(getContext()).load(R.drawable.m1)
                            .into(rp_pic);
                }

                rp_songname.setText(SONGNAME_TO_FRAG);
                rp_author.setText(ARTIST_TO_FRAG);
            }
        }
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


