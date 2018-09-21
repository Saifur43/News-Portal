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

public class PostDetails extends AppCompatActivity {

    TextView titleText;
    TextView detailText;
    ImageView img;
    String link;
    String tit;

    String tt;
    String bdy;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Intent intent = getIntent();

        link = intent.getStringExtra(TopNews.LNK);
        tit = intent.getStringExtra(TopNews.TT);


        getData();

        titleText = (TextView) findViewById(R.id.titleText);
        detailText = (TextView) findViewById(R.id.detailText);
        img = (ImageView) findViewById(R.id.imageView);


        titleText.setText(tt);
        detailText.setText(bdy);

        try{
            Picasso.get().load(url).into(img);
        }
       catch (Exception e){

       }


    }

    public void getData(){
        String baseurl;

        try{

            String[] linkss = link.split("\\.");

            baseurl = linkss[1];

            System.out.println("safasdf..."+ baseurl);

        }
        catch (RuntimeException e){
            baseurl = "bdnews24";

        }





        if(baseurl.equals("priyo")){

            try{
                Document doc = Jsoup.connect(link).get();
                Elements newsHeadlines = doc.select(".mb-2 h1");
                Elements body = doc.select(".pr-4 p");
                Elements imga = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");

                tt = newsHeadlines.text();
                bdy = body.text();

                int i = 1;

                for(Element image: imga ){


                    if(i==4){
                        url = image.attr("src");
                        break;

                    }

                    i++;

                }

                System.out.println("Image " + url);


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

        else if(baseurl.equals("bdnews24")){

            try{
                Document doc = Jsoup.connect(link).get();
                Elements body = doc.select(".wrappingContent p");
                Elements imga = doc.select(".media img");


                tt = tit;
                bdy = body.text();

                System.out.println(tt);
                System.out.println(bdy);
                System.out.println(imga.attr("src"));
                url = imga.attr("src");



                System.out.println("Image " + url);



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

        else{

            tt = "Details News Not Found";



        }


    }


}
