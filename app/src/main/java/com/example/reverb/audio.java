package com.example.reverb;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class audio extends PagerAdapter {

    static ArrayList<MusicFiles> listFiles = new ArrayList();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    String previousActivity;
    private Thread playPauseThread;
    TextView song_name, author, seekstart, seekstop;
    ImageView loop, rewind, forward, like, cover_image;
    FloatingActionButton playpausebtn;
    SeekBar seekbar;
    int position = -1;
    RelativeLayout relativeLayout;


    public audio() {
        song_name.setText(listFiles.get(position).getTitle());
        author.setText(listFiles.get(position).getArtist());
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == (object);
    }
}

