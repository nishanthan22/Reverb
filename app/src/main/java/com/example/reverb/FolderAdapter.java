package com.example.reverb;

import android.content.Context;
// <<<<<<< Ria
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// =======
// import android.view.LayoutInflater;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.ImageView;
// >>>>>>> master
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
// <<<<<<< Ria


import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder>{
    private final ArrayList<String> folderName;
    private final ArrayList<ModelVideo> videoModels;
    private final Context context;

    public FolderAdapter(ArrayList<String> folderName, ArrayList<ModelVideo> videoModels, Context context) {
        this.folderName = folderName;
        this.videoModels = videoModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_video_folders, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int index = folderName.get(position).lastIndexOf("/");
        String folderNames = folderName.get(position).substring(index + 1);

        holder.name.setText(folderNames);
        holder.countVideos.setText(String.valueOf(countVideos(folderName.get(position))));
// =======

// import org.jetbrains.annotations.NotNull;

// import java.util.ArrayList;

// public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {

//     static ArrayList<String> videosList1 = new ArrayList<>();
//     Context context;

//     FolderAdapter(Context context, ArrayList<String> videosList){
//         this.context = context;
//         this.videosList1 = videosList;
//     }



//     @NonNull
//     @NotNull
//     @Override
//     public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//         View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.vfolder, parent, false);
//         return new FolderAdapter.MyViewHolder(itemView1);
//     }

//     @Override
//     public void onBindViewHolder(@NonNull @NotNull FolderAdapter.MyViewHolder holder, int position) {
//         final String item1 = videosList1.get(position);
//         holder.f_title.setText(item1);
// >>>>>>> master
    }

    @Override
    public int getItemCount() {
// <<<<<<< Ria
        return folderName.size();
    }

    int countVideos(String folders) {
        int count = 0;
        for (ModelVideo model : videoModels) {
            if (model.getPath().substring(0,
                    model.getPath().lastIndexOf("/"))
                    .endsWith(folders)) {
                count++;
            }
        }
        return count;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, countVideos;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.folderName);
            countVideos = itemView.findViewById(R.id.videosCount);
        }
    }
}

// =======
//         return videosList1.size();
//     }

//     static class MyViewHolder extends RecyclerView.ViewHolder {

//         ImageView f_img;
//         TextView f_title;

//         public MyViewHolder(@NonNull View itemView) {
//             super(itemView);
//             f_img=itemView.findViewById(R.id.folder_image);
//             f_title=itemView.findViewById(R.id.folder_name);
//         }
//     }
// }
// >>>>>>> master
