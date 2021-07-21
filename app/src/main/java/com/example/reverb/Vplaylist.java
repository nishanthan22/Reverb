package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

//import static com.example.reverb.AdapterVideoList.videosList;
//import static com.example.reverb.FolderAdapter.videosList1;
import static com.example.reverb.allvideos.folder;


public class Vplaylist extends AppCompatActivity {
    //static ArrayList<String> f_files =new ArrayList();
    static ArrayList<ModelVideo> v = new ArrayList<>();
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vplaylist);
        v=folder;
        for(int i=0;i<v.size();i++)
        {
            if(v.get(i).getTitle()=="")
            {
                v.remove(i);
            }
        }

//        int slashFirstIndex = v.get(position).getData().getPath().lastIndexOf("/");
//        String subString = v.get(position).getData().getPath().substring(0,slashFirstIndex);
//        int index= subString.lastIndexOf("/");
//        String folderName = subString.substring(index+1,slashFirstIndex);
//        if(!f_files.contains(folderName))
//            f_files.add(folderName);
        //f_files= folder_names;

        RecyclerView recyclerView = findViewById(R.id.rFolder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); //2 = column count
        FolderAdapter folderAdapter = new FolderAdapter(this, v);

        recyclerView.setAdapter(folderAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));

    }
}