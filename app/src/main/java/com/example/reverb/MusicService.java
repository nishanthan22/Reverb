package com.example.reverb;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

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

    @Override
    public void onCreate() {
          super.onCreate();

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
        if (myposition!=-1){
            playMedia(myposition);
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
    void createMediaPlayer(int position){
        uri = Uri.parse(musicFiles.get(position).getPath());
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
        if (actionPlaying!=null){
            actionPlaying.nextSong();

        }

            createMediaPlayer(position);
            mediaPlayer.start();
            OnCompleted();


    }

}
