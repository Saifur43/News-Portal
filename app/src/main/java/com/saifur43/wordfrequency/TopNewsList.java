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

public class TopNewsList extends ArrayAdapter<Posts> {

    private Activity context;

    private List<Posts> newsList;

    public TopNewsList(@NonNull Context context, List<Posts> newsList) {
        super(context,R.layout.activity_listview, newsList);
        this.context = (Activity) context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_listview,null,true);

        Posts posts = newsList.get(position);

        TextView text  = (TextView)listViewItem.findViewById(R.id.textView);
        TextView text2 = (TextView)listViewItem.findViewById(R.id.base);


        text.setText(posts.getP_name());
        String bases = posts.getLinks();

        String[] linkss = bases.split("\\.");

        String baseurl;
        baseurl = linkss[1] + ".com";

        text2.setText(baseurl);




        return listViewItem;
    }
}
