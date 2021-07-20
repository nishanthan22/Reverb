package com.example.reverb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {
     private Context mContext;
     static ArrayList<MusicFiles> mFiles;
//     static ArrayList<String> playListFiles;
//     static String plNames;
    // private List<MusicFiles> myfiles;
     static Uri uri;

     SongAdapter(Context mContext, ArrayList<MusicFiles> mFiles) {
          this.mFiles = mFiles;
          this.mContext = mContext;
         // this.myfiles = new ArrayList<>(mFiles);
     }
//      public void filterList(ArrayList<MusicFiles> filterlist){
//          mFiles = filterlist;
//          notifyDataSetChanged();
//      }

     @NonNull
     @Override
     public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View view = LayoutInflater.from(mContext).inflate(R.layout.song_items, parent, false);
          return new MyViewHolder(view);
     }

     @Override
     public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

          holder.filename.setText(mFiles.get(position).getTitle());
          uri = Uri.parse(mFiles.get(position).getPath());
          MediaMetadataRetriever mmr = new MediaMetadataRetriever();
          mmr.setDataSource(uri.toString());
          byte[] album = mmr.getEmbeddedPicture();
          if (album != null) {
               Glide.with(mContext)
                       .asBitmap()
                       .load(album)
                       .into(holder.album_art);
          } else {
               Glide.with(mContext)
                       .asBitmap()
                       .load(R.drawable.ic_baseline_music_note_24)
                       .into(holder.album_art);

          }


          holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    Intent a_Player = new Intent(mContext, AudioPlayer.class);
                    a_Player.putExtra("position", position);
                    mContext.startActivity(a_Player);
                    ((Activity) mContext).finish();


               }
          });
//          holder.plbutton.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  plNames=mFiles.get(position).getPath();
//
//                  playListFiles.add(plNames);
//                  Toast.makeText(mContext.getApplicationContext(), "S"+playListFiles,Toast.LENGTH_SHORT)
//                          .show();
//
//
//              }
//          });

     }


     @Override
     public int getItemCount() {
          return mFiles.size();
     }


     public class MyViewHolder extends RecyclerView.ViewHolder {
          TextView filename;
          ImageView album_art;

          public MyViewHolder(@NonNull View itemView) {
               super(itemView);
               filename = itemView.findViewById(R.id.txtsongname);
               album_art = itemView.findViewById(R.id.imgsong);
               //plbutton=itemView.findViewById(R.id.addPlaylistButton);
          }
     }

     void updateList(ArrayList<MusicFiles> musicFilesArrayList){
         mFiles = new ArrayList<>();
         mFiles.addAll(musicFilesArrayList);
         notifyDataSetChanged();
     }
}



