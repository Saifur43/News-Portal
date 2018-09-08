package com.saifur43.wordfrequency;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchNews extends AppCompatActivity {

    EditText searchText;
    Button sb;
    TextView error;

    ListView sView;
    public static final String LNK = "links";

    List<Posts> searchList;

    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        searchText = (EditText) findViewById(R.id.editText);
        sb = (Button) findViewById(R.id.sB);
        error = (TextView) findViewById(R.id.error);

        sView = (ListView) findViewById(R.id.listViewSearch);

        searchList = new ArrayList<>();


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Posts");

        sb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        searchList.clear();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            Posts post = postSnapshot.getValue(Posts.class);

                            String title = post.getP_name();

                            String match = searchText.getText().toString();

                            if(match.isEmpty()){
                                searchText.setError("কোন তথ্য দেওয়া হই নি!");
                                searchText.requestFocus();
                                break;
                            }

                            if(title.contains(match)){
                                searchList.add(post);
                            }



                        }

                        if(searchList.isEmpty()){

                            error.setVisibility(View.VISIBLE);
                        }

                        TopNewsList topNewsList = new TopNewsList(SearchNews.this, searchList);

                        sView.setAdapter(topNewsList);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        sView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Posts posts = searchList.get(position);

                Intent intent = new Intent(getApplicationContext(),PostDetails.class);
                intent.putExtra(LNK,posts.getLinks());

                startActivity(intent);
            }
        });
    }

}
