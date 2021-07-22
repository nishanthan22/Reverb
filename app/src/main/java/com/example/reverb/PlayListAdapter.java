package com.example.reverb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListHolder> {
    private Context pContext;
    static ArrayList<MusicFiles> pFiles;
    static Uri pl_uri;
    View view;

    public PlayListAdapter(Context applicationContext, ArrayList<MusicFiles> playList) {
        this.pContext=applicationContext;
        this.pFiles=playList;


    }

    @NonNull
    @NotNull
    @Override
    public PlayListAdapter.PlayListHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
         view = LayoutInflater.from(pContext).inflate(R.layout.playlist_item,parent,false);
        return new PlayListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlayListAdapter.PlayListHolder holder, int position) {

        holder.plSongname.setText(pFiles.get(position).getTitle());
        pl_uri=Uri.parse(pFiles.get(position).getPath());
        //String pPath = pFiles.get(position).getPath();

        MediaMetadataRetriever mmr1 = new MediaMetadataRetriever();
        mmr1.setDataSource(pl_uri.toString());
        byte[] album = mmr1.getEmbeddedPicture();
        if (album != null) {
            Glide.with(pContext)
                    .asBitmap()
                    .load(album)
                    .into(holder.plSongimg);
        } else {
            Glide.with(pContext)
                    .asBitmap()
                    .load(R.drawable.reverb_logo)
                    .into(holder.plSongimg);

        }

        //Play the song on clicking it

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pPlay = new Intent(pContext,AudioPlayer.class);
                pPlay.putExtra("position",position);
                pPlay.putExtra("sender","PlaylistFiles");
                pPlay.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                pContext.startActivity(pPlay);

            }
        });


    }

    @Override
    public int getItemCount() {
        return pFiles.size();
    }

    public class PlayListHolder extends RecyclerView.ViewHolder {
        ImageView plSongimg;
        TextView plSongname;
        public PlayListHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            plSongimg=itemView.findViewById(R.id.plistsongimg);
            plSongname=itemView.findViewById(R.id.plistsongname);
        }
    }
}
