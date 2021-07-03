package com.example.reverb;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;


public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder>{
     private Context mContext;
     private ArrayList<MusicFiles> mFiles;
     SongAdapter(Context mContext,ArrayList<MusicFiles> mFiles){
          this.mFiles=mFiles;
          this.mContext=mContext;
     }

     @NonNull
     @Override
     public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View view =LayoutInflater.from(mContext).inflate(R.layout.song_items ,parent,false);
          return new MyViewHolder(view);
     }

     @Override
     public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         holder.filename.setText(mFiles.get(position).getTitle());
         byte[] image=getAlbumArt(mFiles.get(position).getPath());

         if (image!=null){
              Glide.with(mContext)
                      .asBitmap()
                      .load(image)
                      .into(holder.albumart);


         }
         else {
             Glide.with(mContext)
                     .load(R.drawable.ic_baseline_music_note_24)
                     .into(holder.albumart);
         }


     }

     @Override
     public int getItemCount() {
          return mFiles.size();
     }

     public class MyViewHolder extends RecyclerView.ViewHolder{
          TextView filename;
          ImageView albumart;

          public MyViewHolder(@NonNull View itemView){
               super(itemView);
               filename=itemView.findViewById(R.id.txtsongname);
               albumart=itemView.findViewById(R.id.imgsong);
          }
     }

     private byte[] getAlbumArt(Uri uri){
          MediaMetadataRetriever retriever=new MediaMetadataRetriever();
          retriever.setDataSource(uri.toString());
          byte[] art =retriever.getEmbeddedPicture();
          retriever.release();
          return art;
     }
}

