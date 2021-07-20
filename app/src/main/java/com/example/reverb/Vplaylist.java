/*
package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import static com.example.reverb.AdapterVideoList.videosList;
import static com.example.reverb.FolderAdapter.videosList1;
//import static com.example.reverb.allvideos.folder_names;


public class Vplaylist extends AppCompatActivity {
    static ArrayList<String> f_files =new ArrayList();
    static ArrayList<VideoModel> v = new ArrayList<>();
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vplaylist);
//        v = videosList;
//        int slashFirstIndex = v.get(position).getData().getPath().lastIndexOf("/");
//        String subString = v.get(position).getData().getPath().substring(0,slashFirstIndex);
//        int index= subString.lastIndexOf("/");
//        String folderName = subString.substring(index+1,slashFirstIndex);
//        if(!f_files.contains(folderName))
//            f_files.add(folderName);
        //f_files= folder_names;

        RecyclerView recyclerView = findViewById(R.id.rFolder);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); //2 = column count
        FolderAdapter folderAdapter = new FolderAdapter(this, f_files);

        recyclerView.setAdapter(folderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));

    }
}*/



package com.example.reverb;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reverb.R;
import com.example.reverb.VideoModel;
import com.example.reverb.FolderAdapter;

import java.util.ArrayList;

public class Vplaylist extends AppCompatActivity {


    FolderAdapter folderAdapter;
    RecyclerView recyclerView;
    private final ArrayList<String> folderList = new ArrayList<>();
    private ArrayList<VideoModel> videoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rFolder);
        videoList = fetchAllVideos(this);
        if (folderList != null && folderList.size() > 0 && videoList != null) {
            folderAdapter = new FolderAdapter(folderList, videoList, this);
            recyclerView.setAdapter(folderAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,
                    RecyclerView.VERTICAL, false));
        } else {
            Toast.makeText(this, "can't find any videos folder", Toast.LENGTH_SHORT).show();
        }

    }


    //this is the method will fetch  all videos from internal or external storage
    private ArrayList<VideoModel> fetchAllVideos(Context context) {
        ArrayList<VideoModel> videoModels = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String orderBy = MediaStore.Video.Media.DATE_ADDED + " DESC";
        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.HEIGHT,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.RESOLUTION};

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, orderBy);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String path = cursor.getString(1);
                String title = cursor.getString(2);
                String size = cursor.getString(3);
                String resolution = cursor.getString(4);
                String duration = cursor.getString(5);
                String disName = cursor.getString(6);
                String width_height = cursor.getString(7);

                VideoModel videoFiles = new VideoModel(id, path, title, size, resolution, duration, disName, width_height);
                int slashFirstIndex = path.lastIndexOf("/");
                String subString = path.substring(0, slashFirstIndex);
                if (!folderList.contains(subString)) {
                    folderList.add(subString);
                }
                videoModels.add(videoFiles);
            }
            cursor.close();
        }
        return videoModels;

    }

}
