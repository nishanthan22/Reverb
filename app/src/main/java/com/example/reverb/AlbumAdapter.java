package com.example.reverb;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
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

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<MusicFiles> albumFiles;
    View view;
    static Uri uri;

    public AlbumAdapter(Context mContext, ArrayList<MusicFiles> albumFiles) {
        this.mContext = mContext;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       view = LayoutInflater.from(mContext).inflate(R.layout.album_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AlbumAdapter.MyHolder holder, int position) {
        holder.album_name.setText(albumFiles.get(position).getAlbum());
        uri = Uri.parse(albumFiles.get(position).getPath());
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(uri.toString());
        byte[] album = mmr.getEmbeddedPicture();
        if (album != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(album)
                    .into(holder.album_image);
        } else {
            Glide.with(mContext)
                    .asBitmap()
                    .load(R.drawable.ic_baseline_music_note_24)
                    .into(holder.album_image);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,AlbumDetails.class);
                i.putExtra("albumName",albumFiles.get(position).getAlbum());
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        ImageView album_image;
        TextView album_name;
        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            album_image=itemView.findViewById(R.id.albumimage);
            album_name=itemView.findViewById(R.id.albumname);
        }
    }
}
