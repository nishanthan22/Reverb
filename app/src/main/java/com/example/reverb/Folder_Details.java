package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.reverb.allvideos.videosList;

public class Folder_Details extends AppCompatActivity {
    RecyclerView recyclerView;
    String albumName;
    TextView f_name;
    static ArrayList<ModelVideo> folder_videos = new ArrayList<>();
    //static ArrayList<ModelVideo> a= new ArrayList<>();
    FolderDetailsAdapter folderDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_details);
        recyclerView= findViewById(R.id.r_folderinfo);
        f_name=findViewById(R.id.folder_namevv);

        albumName = getIntent().getStringExtra("albumName");
        f_name.setText(albumName);

        int j = 0;
        for (int i = 0;i<videosList.size();i++){
            if (albumName.equals(videosList.get(i).getAlbum())){
                folder_videos.add(j,videosList.get(i));
                j++;
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!(folder_videos.size()<1)){
            folderDetailsAdapter = new FolderDetailsAdapter(this,folder_videos);
            recyclerView.setAdapter(folderDetailsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));


        }
    }
}