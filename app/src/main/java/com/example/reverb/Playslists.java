package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import static com.example.reverb.AudioPlayer.playList;

public class Playslists extends AppCompatActivity {
    RecyclerView recyclerViewPl;
    static PlayListAdapter pladapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playslists);
        recyclerViewPl=findViewById(R.id.playlist_recyclrView);

        if (playList!=null){
            pladapter = new PlayListAdapter(getApplicationContext(),playList);
            recyclerViewPl.setAdapter(pladapter);
            recyclerViewPl.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
            SpacingItemDecorator itemDecorator= new SpacingItemDecorator(32);
            recyclerViewPl.addItemDecoration(itemDecorator);
        }
    }
}




