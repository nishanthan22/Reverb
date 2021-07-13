package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.reverb.SongList.musicFiles;

public class AlbumDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView albumPicture;
    String albumName;
    static Uri uri;
    ArrayList<MusicFiles> albumsongs = new ArrayList<>();
    AlbumDetailsAdapter albumDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        recyclerView= findViewById(R.id.drecyView);
        albumPicture=findViewById(R.id.albumPic);
        albumName = getIntent().getStringExtra("albumName");
        int j = 0;
        for (int i = 0;i<musicFiles.size();i++){
            if (albumName.equals(musicFiles.get(i).getAlbum())){
                albumsongs.add(j,musicFiles.get(i));
                j++;
            }
        }

        uri = Uri.parse(albumsongs.get(0).getPath());
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(uri.toString());
        byte[] album = mmr.getEmbeddedPicture();
        if (album != null) {
            Glide.with(this)
                    .asBitmap()
                    .load(album)
                    .into(albumPicture);
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.r_logo)
                    .into(albumPicture);

        }




    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!(albumsongs.size()<1)){
            albumDetailsAdapter = new AlbumDetailsAdapter(this,albumsongs);
            recyclerView.setAdapter(albumDetailsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));



        }
    }
}