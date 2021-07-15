package com.example.reverb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {

    static ArrayList<String> videosList1 = new ArrayList<>();
    Context context;

    FolderAdapter(Context context, ArrayList<String> videosList){
        this.context = context;
        this.videosList1 = videosList;
    }



    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.vfolder, parent, false);
        return new FolderAdapter.MyViewHolder(itemView1);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FolderAdapter.MyViewHolder holder, int position) {
        final String item1 = videosList1.get(position);
        holder.f_title.setText(item1);
    }

    @Override
    public int getItemCount() {
        return videosList1.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView f_img;
        TextView f_title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            f_img=itemView.findViewById(R.id.folder_image);
            f_title=itemView.findViewById(R.id.folder_name);
        }
    }
}
