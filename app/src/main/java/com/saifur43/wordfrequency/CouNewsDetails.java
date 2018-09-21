package com.saifur43.wordfrequency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CouNewsDetails extends AppCompatActivity {

    TextView titleText;
    TextView detailText;
    ImageView img;
    String link;


    String tt;
    String bdy;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cou_news_details);


        Intent intent = getIntent();

        link = intent.getStringExtra(TopNews.LNK);

        getCData();

        titleText = (TextView) findViewById(R.id.tttext);
        detailText = (TextView) findViewById(R.id.coudetail);
        img = (ImageView) findViewById(R.id.imageV);

        titleText.setText(tt);
        detailText.setText(bdy);

        Picasso.get().load(url).into(img);
    }

    public void getCData(){

        try{
            Document doc = Jsoup.connect(link).get();
            Elements newsHeadlines = doc.select(".article-content h1");
            Elements body = doc.select(".entry-content p");
            Elements imga = doc.select(".featured-image img");

            tt = newsHeadlines.text();
            bdy = body.text();
            url = imga.attr("src");


            System.out.println("Image " + url);

            System.out.println(tt);
            System.out.println(bdy);

                /* String[] alphabets = bdy.split(" ");

                System.out.println(tt);
                System.out.println(bdy);
                for(int i=0;i<10;i++){
                    System.out.println(alphabets[i]);
                } */

        }
        catch (Exception e){
            e.printStackTrace();

        }

    }
}
