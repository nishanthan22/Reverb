package com.example.reverb;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;


import static com.example.reverb.SongList.musicFiles;


public class SongFragment extends Fragment {

    RecyclerView recyclerView;
    SongAdapter songAdapter;
    Toolbar toolbar;



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
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //toolbar = (Toolbar)getActivity().findViewById(R.id.acbar);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);




    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu1, menu);
        MenuItem searchViewItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchViewItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


              // if(musicFiles.contains(query)){
                   // songAdapter.filter(query);
               // }else{
                    //Toast.makeText(getContext(), "No Match found",Toast.LENGTH_LONG).show();
                //}
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                songAdapter.getFilter().filter(newText);


                return false;
            }
        });

    }
}





