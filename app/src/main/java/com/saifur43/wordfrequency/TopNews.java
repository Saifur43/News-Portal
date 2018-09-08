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

import java.util.ArrayList;
import java.util.List;


public class TopNews extends AppCompatActivity {

    ListView view ;

    DatabaseReference myRef;
    FirebaseDatabase database;

    List<Posts> postsList;

    public static final String LNK = "links";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_news);

        view = (ListView) findViewById(R.id.listView);

        postsList = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Posts");

        crawler();

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Posts posts = postsList.get(position);

                Intent intent = new Intent(getApplicationContext(),PostDetails.class);
                intent.putExtra(LNK,posts.getLinks());

                startActivity(intent);
            }
        });


    }
    public void crawler(){

        myRef.setValue(null);

        try{

            Document doc = Jsoup.connect("https://www.priyo.com/").get();
            Elements newsHeadlines = doc.select(".order-featured-article-end a");

            for (Element headline : newsHeadlines) {

               // System.out.println(headline.text());
               // System.out.println(headline.absUrl("href"));

                String title;
                String link;
                title = headline.text();
                link = headline.absUrl("href");



                if(title!=null  && link!=null){

                    Posts post = new Posts(title,link);

                    try{
                        myRef.child(title).setValue(post);
                    }
                    catch (Exception r){

                    }


                }

            }

            Document doc2 = Jsoup.connect("https://bangla.bdnews24.com/").get();
            Elements newsHeadlines2 = doc2.select(".column-2 h6 a");



            int i = 1;
            for (Element headline2 : newsHeadlines2) {

                System.out.println("sdfdsf " +headline2.text());
                System.out.println(headline2.absUrl("href"));

                String title;
                String link;
                title = headline2.text();
                link = headline2.absUrl("href");

                if(title!=null  && link!=null){

                    Posts post = new Posts(title,link);

                    try{
                        myRef.child(title).setValue(post);
                    }
                    catch (Exception r){

                    }


                }



                if(i==8)
                    break;

                i++;


            }





        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                postsList.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    Posts post = postSnapshot.getValue(Posts.class);

                    postsList.add(post);
                }

                TopNewsList topNewsList = new TopNewsList(TopNews.this,postsList);

                view.setAdapter(topNewsList);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
