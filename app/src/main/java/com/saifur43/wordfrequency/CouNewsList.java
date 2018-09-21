package com.saifur43.wordfrequency;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CouNewsList extends ArrayAdapter<Posts> {

    private Activity context;

    private List<Posts> newsList;

    public CouNewsList(@NonNull Context context, List<Posts> newsList) {
        super(context,R.layout.activity_listview, newsList);
        this.context = (Activity) context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.counewslist,null,true);

        Posts posts = newsList.get(position);

        TextView text  = (TextView)listViewItem.findViewById(R.id.listcou);


        text.setText(posts.getP_name());


        return listViewItem;
    }
}
