package com.example.reverb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
          byte[] image = getAlbumArt(mFiles.get(position).getPath());
          if (image!=null){
               Glide.with(mContext).asBitmap()
                       .load(image)
                       .into(holder.album_art);
          }
          else {
               Glide.with(mContext)
                       .asBitmap()
                       .load(R.drawable.ic_baseline_music_note_24)
                       .into(holder.album_art);
          }


          holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    Intent a_Player = new Intent(mContext,AudioPlayer.class);
                    a_Player.putExtra("position",position);
                    mContext.startActivity(a_Player);

               }
          });

//         holder.filename.setText(mFiles.get(position).getTitle());
//         MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//         mmr.setDataSource(mFiles.get(position).getPath());
//         byte[] artbytes = mmr.getEmbeddedPicture();
//         if (artbytes!=null){
//              InputStream is =new ByteArrayInputStream(mmr.getEmbeddedPicture());
//              Bitmap bm =null;
//              bm =BitmapFactory.decodeStream(is);
//              holder.albumart.setImageBitmap(bm);
//              mmr.release();
//         }
//         else {
//              Glide.with(mContext)
//                   .load(R.drawable.ic_baseline_music_note_24)
//                     .into(holder.albumart);
//         }
//         mmr.release();


          
         //byte[] image=getAlbumArt(mFiles.get(position).getPath());


//         if (image!=null){
////              Glide.with(mContext)
////                      .asBitmap()
////                      .load(image)
////                      .into(holder.albumart);
//
//
//
//         }
//         else {
//             Glide.with(mContext)
//                     .load(R.drawable.ic_baseline_music_note_24)
//                     .into(holder.albumart);
//         }
     }

     @Override
     public int getItemCount() {
          return mFiles.size();
     }

     public class MyViewHolder extends RecyclerView.ViewHolder{
          TextView filename;
          ImageView album_art;

          public MyViewHolder(@NonNull View itemView){
               super(itemView);
               filename=itemView.findViewById(R.id.txtsongname);
               album_art=itemView.findViewById(R.id.imgsong);
          }
     }

     private byte[] getAlbumArt(String uri){
          MediaMetadataRetriever retriever=new MediaMetadataRetriever();
          retriever.setDataSource(uri);
          byte[] art =retriever.getEmbeddedPicture();
          retriever.release();
          return art;
     }
}

