package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.reverb.SongFragment.songAdapter;


public class SongList extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final int REQUEST_CODE=1;
    static ArrayList<MusicFiles> musicFiles = new ArrayList<>();
    static boolean loopBoolean = false;
    static ArrayList<MusicFiles> albums = new ArrayList<>();
    public static final String MUSIC_LAST_PLAYED = "LAST_PLAYED";
    public static final String MUSIC_FILE ="STORED_MUSIC";
    public static boolean SHOW_MINI_PLAYER = false;
    public static String PATH_TO_FRAG =null;
    public static String ARTIST_TO_FRAG =null;
    public static String SONGNAME_TO_FRAG =null;
    public static String TOTAL_DURATION_TO_FLAG =null;
    public static final String ARTIST_NAME = "ARTIST_NAME";
    public static final String SONG_NAME ="SONG_NAME";
    public static final String SEEK_START ="SEEK_START";
    public static final String SEEK_STOP ="SEEK_STOP";
    public static final String TOTAL_DURATION  ="TOTAL_DURATION";
    LinearLayout fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        fr=findViewById(R.id.bottomframe);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action);
        // getActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();

        permission();
        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnPlayBottomFrag onPlayBottomFrag= new OnPlayBottomFrag();
                onPlayBottomFrag.show(getSupportFragmentManager(),onPlayBottomFrag.getTag());
            }
        });

    }

    private void initViewPager(){
        ViewPager viewPager=findViewById(R.id.viewpager);
        TabLayout tabLayout=findViewById(R.id.tablayout);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new SongFragment(),"Songs");
        viewPagerAdapter.addFragments(new AlbumFragment(),"Albums");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }



    public static class ViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String > titles;
        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }

        void addFragments(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
    private void permission(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SongList.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} ,SongList.REQUEST_CODE);
        }
        else {
           Toast.makeText(getApplicationContext(),"Music Files",Toast.LENGTH_SHORT).show();
           if (musicFiles.size()==0) {
               musicFiles = getAllAudio(getApplicationContext());
           }
            initViewPager();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"Music Files",Toast.LENGTH_SHORT).show();
                if (musicFiles.size()==0) {
                    musicFiles = getAllAudio(this);
                }
                initViewPager();

            }
            else{
                ActivityCompat.requestPermissions(SongList.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} ,SongList.REQUEST_CODE);

            }
        }
    }

    private class REQUEST_CODE {
    }

    public static ArrayList<MusicFiles> getAllAudio(Context context){

        ArrayList<String> duplicate = new ArrayList<>();
        ArrayList<MusicFiles> tempAudioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST };

        Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);
        if (cursor!=null){
            while(cursor.moveToNext()){
                String album =cursor.getString(0);
                String title =cursor.getString(1);
                String duration =cursor.getString(2);
                String path =cursor.getString(3);
                String artist =cursor.getString(4);

                MusicFiles musicFiles=new MusicFiles(path,title,artist,album,duration);
                Log.e("PATH : "+path," ALBUM : "+album);
                tempAudioList.add(musicFiles);

                    if (!duplicate.contains(album)) {
                        albums.add(musicFiles);
                        duplicate.add(album);

                    }


            }
            cursor.close();
        }
        return tempAudioList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search your song here");
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        ArrayList<MusicFiles> myFiles = new ArrayList<>();
        for (MusicFiles song : musicFiles){
            if (song.getTitle().toLowerCase().contains(userInput)){
                myFiles.add(song);
            }
        }
        SongFragment.songAdapter.updateList(myFiles);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(MUSIC_LAST_PLAYED,MODE_PRIVATE);
        String path = preferences.getString(MUSIC_FILE,null);
        String artist = preferences.getString(ARTIST_NAME,null);
        String song_name = preferences.getString(SONG_NAME,null);
        String totalDuration1 = preferences.getString(TOTAL_DURATION,null);
        if (path!= null){
            SHOW_MINI_PLAYER = true;
            PATH_TO_FRAG= path;
            ARTIST_TO_FRAG=artist;
            SONGNAME_TO_FRAG=song_name;
            TOTAL_DURATION_TO_FLAG=totalDuration1;
        }
        else {
            SHOW_MINI_PLAYER =false;
            PATH_TO_FRAG=null;
            ARTIST_TO_FRAG=null;
            SONGNAME_TO_FRAG=null;
            TOTAL_DURATION_TO_FLAG=null;
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(this,HomePage.class);
        startActivity(i);
    }
}