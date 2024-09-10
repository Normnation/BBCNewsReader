package com.example.BBCNewsReader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListviewAdapter extends BaseAdapter {
    Context context;
    ArrayList<FeedData> arrayListForFeed;


    public ListviewAdapter(Context context, ArrayList<FeedData> arrayListForFeed) {
        this.context = context;
        this.arrayListForFeed = arrayListForFeed;
    }


    @Override
    public int getCount() {


        return arrayListForFeed.size();
    }


    @Override
    public Object getItem(int position) {


        return arrayListForFeed.get(position);
    }


    @Override
    public long getItemId(int position) {


        return position;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_feed, viewGroup, false);
        }

        TextView title = view.findViewById(R.id.feedRowTitle);

        FeedData whereAreWeAt = (FeedData) getItem(i);

        String myTitle = whereAreWeAt.getTitleVar();

        title.setText(myTitle);

        return view;
    }


}