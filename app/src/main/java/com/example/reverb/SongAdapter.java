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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> implements Filterable {
     private Context mContext;
     private ArrayList<MusicFiles> mFiles;
     private List<MusicFiles> myfiles;
     static Uri uri;

     SongAdapter(Context mContext, ArrayList<MusicFiles> mFiles) {
          this.mFiles = mFiles;
          this.mContext = mContext;
          this.myfiles = new ArrayList<>(mFiles);
     }

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

     }


     @Override
     public int getItemCount() {
          return mFiles.size();
     }

     @Override
     public Filter getFilter() {
          return filter;
     }
     Filter filter = new Filter() {
          @Override
          protected FilterResults performFiltering(CharSequence constraint) {

               List<MusicFiles> filteredList = new ArrayList<>();
               if(constraint.toString().isEmpty())
               {
                    filteredList.addAll(myfiles);

               }else
               {
                    for(MusicFiles m: myfiles)
                    {
                         if(m.getTitle().toLowerCase().contains(constraint.toString().toLowerCase()))
                         {
                            filteredList.add(m);
                         }


                    }
               }
               FilterResults filterResults = new FilterResults();
               filterResults.values=filteredList;



               return filterResults;
          }

          @Override
          protected void publishResults(CharSequence constraint, FilterResults results) {
               mFiles.clear();
               mFiles.addAll((Collection<? extends MusicFiles>) results.values);
               notifyDataSetChanged();

          }
     };


     public class MyViewHolder extends RecyclerView.ViewHolder {
          TextView filename;
          ImageView album_art;

          public MyViewHolder(@NonNull View itemView) {
               super(itemView);
               filename = itemView.findViewById(R.id.txtsongname);
               album_art = itemView.findViewById(R.id.imgsong);
          }
     }
}



