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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class viewpageradapter extends RecyclerView.Adapter<viewpageradapter.ViewHolder>{
    private Context musicContext;
    static ArrayList<MusicFiles> musicFiles;
    static Uri uri;

    viewpageradapter(Context musicContext, ArrayList<MusicFiles> musicFiles) {
        this.musicFiles = musicFiles;
        this.musicContext = musicContext;
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
        holder.seekstop.setText(formattedTime(totalDuration));

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
        TextView song_name,author,seekstart,seekstop;
        ImageView loop,rewind,forward,cover_image;
        ToggleButton like;
        View v1;

        FloatingActionButton playpausebtn;
        SeekBar seekbar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            song_name=itemView.findViewById(R.id.songname1);
            author=itemView.findViewById(R.id.author1);
            seekstart=itemView.findViewById(R.id.seekstart1);
            seekstop=itemView.findViewById(R.id.seekstop1);
            loop=itemView.findViewById(R.id.loop1);
            rewind=itemView.findViewById(R.id.rewind1);
            forward=itemView.findViewById(R.id.forward1);
            like=itemView.findViewById(R.id.like1);
            playpausebtn=itemView.findViewById(R.id.play1);
            seekbar=itemView.findViewById(R.id.seekbar1);
            cover_image=itemView.findViewById(R.id.cover_image1);
            v1=itemView.findViewById(R.id.view1);



        }
    }
}
