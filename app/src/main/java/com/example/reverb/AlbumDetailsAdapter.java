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

public class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.MyHolder> {

    private Context mContext;
    static ArrayList<MusicFiles> albumFiles;
    View view;
    static Uri uri;

    public AlbumDetailsAdapter(Context mContext, ArrayList<MusicFiles> albumFiles) {
        this.mContext = mContext;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.song_items,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AlbumDetailsAdapter.MyHolder holder, int position) {
        holder.album_name.setText(albumFiles.get(position).getTitle());
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
                Intent iaa = new Intent(mContext,AudioPlayer.class);
                iaa.putExtra("sender","albumDetails");
                iaa.putExtra("position",position);
                mContext.startActivity(iaa);
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
            album_image=itemView.findViewById(R.id.imgsong);
            album_name=itemView.findViewById(R.id.txtsongname);
        }
    }
}
