package com.example.reverb;

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

public class FolderDetailsAdapter extends RecyclerView.Adapter<FolderDetailsAdapter.MyViewHolder1> {
    private ArrayList<ModelVideo> folder_details = new ArrayList<>();
    Context context;
    static Uri uri;
    FolderDetailsAdapter(Context context, ArrayList<ModelVideo> folder_details){
        this.context = context;
        this.folder_details = folder_details;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video, parent, false);
        return new FolderDetailsAdapter.MyViewHolder1(itemView1);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FolderDetailsAdapter.MyViewHolder1 holder, int position) {
       // final ModelVideo itemf= folder_details.get(position);
        holder.tv_title1.setText(folder_details.get(position).getTitle());
        holder.tv_duration1.setText(folder_details.get(position).getDuration());
        //Glide.with(context).load(folder_details.get(position).getData()).into(holder.imgView_thumbnail1);

        Log.e("FOLDER"+uri,"PATH");
//        uri = folder_details.get(position).getData();
//        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//        mmr.setDataSource(uri.toString());
        //byte[] album;
            Glide.with(context)
                    .asBitmap()
                    .load(R.drawable.ic_baseline_music_note_24)
                    .into(holder.imgView_thumbnail1);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), VideoPlayer.class);
                intent1.putExtra("position", position);
                v.getContext().startActivity(intent1);
            }
        });
    }



    @Override
    public int getItemCount() {
        return folder_details.size();
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder{
        ImageView imgView_thumbnail1;
        TextView tv_title1,tv_duration1;

        public MyViewHolder1(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_title1 = itemView.findViewById(R.id.txtvidname);
            tv_duration1 = itemView.findViewById(R.id.vidduration);
            imgView_thumbnail1 = itemView.findViewById(R.id.vidimg);
        }
    }
}
