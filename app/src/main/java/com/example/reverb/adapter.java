package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

public class adapter extends FragmentStatePagerAdapter {
    int tabs;


    public adapter(@NonNull @NotNull FragmentManager fm,int NumofTbs) {
        super(fm);
        this.tabs=NumofTbs;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                audio_user a = new audio_user();
                return a;
            case 1:
                video_user v = new video_user();
                return v;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabs;
    }
}
