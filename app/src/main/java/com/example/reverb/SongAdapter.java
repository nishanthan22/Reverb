package com.example.reverb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SongAdapter extends BaseAdapter {
    Context context;
    String[] items;

    public SongAdapter(Context context, String[] items) {
        this.context=context;
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View myView = layoutInflater.inflate(R.layout.song_items,parent,false);
        TextView textsong = myView.findViewById(R.id.txtsongname);
        textsong.setSelected(true);
        textsong.setText(items[position]);
        return  myView;

    }


}
