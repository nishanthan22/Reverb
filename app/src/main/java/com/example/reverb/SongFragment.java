package com.example.reverb;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;

import static com.example.reverb.SongList.musicFiles;


public class SongFragment extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    SongAdapter songAdapter;
    private ArrayList<MusicFiles> m2files;
    //Toolbar toolbar;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;


    public SongFragment() {
        this.m2files=musicFiles;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);


        if (!(musicFiles.size() < 1)) {
            songAdapter = new SongAdapter(getContext(), musicFiles);
            recyclerView.setAdapter(songAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        toolbar = (Toolbar)getActivity().findViewById(R.id.acbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<MusicFiles> filteredModelList = filter(m2files, newText);
        songAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private ArrayList<MusicFiles> filter(ArrayList<MusicFiles> m2files, String newText) {
        newText = newText.toLowerCase();
        final ArrayList<MusicFiles> filteredModelList = new ArrayList<>();
        for(MusicFiles n : m2files){
            final String text = n.getTitle().toLowerCase();
            if (text.contains(newText)){
                filteredModelList.add(n);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // super.onCreateOptionsMenu(menu, inflater);
        //menu.clear();
        inflater.inflate(R.menu.menu1, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                songAdapter.setFilter(m2files);

                return true;
            }
        });
        // SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//
//            queryTextListener = new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    Log.i("onQueryTextChange", newText);
//
//                    return true;
//                }
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    Log.i("onQueryTextSubmit", query);
//
//                    return true;
//                }
//            };
//            searchView.setOnQueryTextListener(queryTextListener);
//        }
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.search:
//                // Not implemented here
//                return false;
//            default:
//                break;
//        }
//        searchView.setOnQueryTextListener(queryTextListener);
//        return super.onOptionsItemSelected(item);
//
//
//    }




    }
}





