package com.example.reverb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.reverb.SongList.musicFiles;


public class SongFragment extends Fragment {
    ListView listView;
    GridView gridView;
    SearchView searchView;
    ArrayList<String> list;
    RecyclerView recyclerView;
    SongAdapter songAdapter;



    public SongFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        if(!(musicFiles.size()<1)){
            songAdapter=new SongAdapter(getContext(),musicFiles);
            recyclerView.setAdapter(songAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

        }
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_song);
        recyclerView= recyclerView.findViewById(R.id.recyclerview);
        searchView= searchView.findViewById(R.id.search);

        //Built-in Adapter
        SongAdapter ad=new SongAdapter(this, android.R.layout.simple_list_item_1,list);
        recyclerView.setAdapter(ad);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(list.contains(query))
                {
                    ad.getFilter(query);
                }
                else{
                    Toast.makeText(getContext(), "could not find the contact", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void setContentView(int fragment_song) {
    }
}