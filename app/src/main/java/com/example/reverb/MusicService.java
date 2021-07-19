package com.example.reverb;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import static com.example.reverb.AudioPlayer.listFiles;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {

    IBinder mBinder = new MyBinder();
    MediaPlayer mediaPlayer;
    Uri uri;
    int position = -1;
    ArrayList<MusicFiles> musicFiles = new ArrayList<>();
    ActionPlaying actionPlaying;
    public static final String MUSIC_LAST_PLAYED = "LAST_PLAYED";
    public static final String MUSIC_FILE ="STORED_MUSIC";
    public static final String ARTIST_NAME = "ARTIST_NAME";
    public static final String SONG_NAME ="SONG_NAME";

    @Override
    public void onCreate() {
          super.onCreate();
          musicFiles=listFiles;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Bind","Method");
        return mBinder;
    }



    public class MyBinder extends Binder{
        MusicService getService(){
            return MusicService.this;

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int myposition = intent.getIntExtra("servicePosition",-1);
       String actionName = intent.getStringExtra("ActionName");

        if (myposition!=-1){
            playMedia(myposition);
        }
        if (actionName!= null){
            switch (actionName){
                case "playPause":
                    playPause();
                    break;
                case "next":
                    nextSong();
                    break;
                case "previous":
                   previousSong();
                    break;
            }
        }

        return START_STICKY;
    }

    private void playMedia(int StartPosition) {
        musicFiles = listFiles;
        position=StartPosition;
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            if (musicFiles!=null){
                createMediaPlayer(position);
                mediaPlayer.start();

            }
        }
        else {
            createMediaPlayer(position);
            mediaPlayer.start();
        }

    }

    void start(){
        mediaPlayer.start();
    }
    boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }
    void stop(){
        mediaPlayer.stop();
    }
    void release(){
        mediaPlayer.release();
    }
    int getDuration(){
        return mediaPlayer.getDuration();
    }
    void seekTo(int position){
        mediaPlayer.seekTo(position);
    }
    void createMediaPlayer(int positionInner){
        position = positionInner;
        uri = Uri.parse(musicFiles.get(position).getPath());
        SharedPreferences.Editor editor = getSharedPreferences(MUSIC_LAST_PLAYED,MODE_PRIVATE)
                .edit();
        editor.putString(MUSIC_FILE, uri.toString());
        editor.putString(ARTIST_NAME,musicFiles.get(position).getArtist());
        editor.putString(SONG_NAME,musicFiles.get(position).getTitle());
        editor.apply();

        mediaPlayer = MediaPlayer.create(getBaseContext(),uri);
    }

    int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();

    }

    void pause(){
        mediaPlayer.pause();

    }

    void OnCompleted(){
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (actionPlaying !=null) {
            actionPlaying.nextSong();
            if (mediaPlayer != null) {
                createMediaPlayer(position);
                mediaPlayer.start();
                OnCompleted();
            }
        }

    }

    void setCallBack(ActionPlaying actionPlaying){
        this.actionPlaying = actionPlaying;
    }

    void nextSong(){
        if (actionPlaying!=null){
            actionPlaying.nextSong();
        }

    }

    void previousSong(){
        if (actionPlaying!=null){
            actionPlaying.previousSong();
        }

    }

    void playPause(){
        if (actionPlaying!=null){
            actionPlaying.playPauseButtonClicked();
        }

    }

}
