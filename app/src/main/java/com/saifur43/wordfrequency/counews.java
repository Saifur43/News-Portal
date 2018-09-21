package com.saifur43.wordfrequency;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class counews extends AppCompatActivity {

    DatabaseReference myRef;
    FirebaseDatabase database;

    ListView coulist;
    List<Posts> postsList;
    public static final String LNK = "links";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counews);
        database = FirebaseDatabase.getInstance();

        coulist = (ListView) findViewById(R.id.coulist);
        postsList = new ArrayList<>();
        myRef = database.getReference("COU");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        newscollect();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                postsList.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    Posts post = postSnapshot.getValue(Posts.class);

                    postsList.add(post);
                }

                CouNewsList couns = new CouNewsList(counews.this,postsList);

                coulist.setAdapter(couns);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        News s = new News();
        s.hideProgressDialog();

        coulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Posts posts = postsList.get(position);

                Intent intent = new Intent(getApplicationContext(),CouNewsDetails.class);
                intent.putExtra(LNK,posts.getLinks());

                startActivity(intent);
            }
        });



    }

    public void newscollect() {

        try {

            Document doc = Jsoup.connect("https://comillarbarta.com/?cat=23").get();
            Elements newsHeadlines = doc.select(".article-content h1 a");

            for (Element headline : newsHeadlines) {

                // System.out.println(headline.text());
                // System.out.println(headline.absUrl("href"));

                String title;
                String link;
                title = headline.text();
                link = headline.absUrl("href");

                System.out.println(title);
                System.out.println(link);


                if (title != null && link != null) {

                    Posts post = new Posts(title, link);

                    try {
                        myRef.child(title).setValue(post);

                    } catch (Exception r) {

                    }


                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}