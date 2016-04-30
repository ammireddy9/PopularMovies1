package com.example.laxmi.popularmovies1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {
    ImageView imageView;
    TextView release_date,user_rating,synopsis,title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        MovieObj movieObj=(MovieObj)intent.getSerializableExtra("object");
        imageView=(ImageView)findViewById(R.id.imageView);
        release_date=(TextView)findViewById(R.id.release_date);
        user_rating=(TextView)findViewById(R.id.user_rating);
        synopsis=(TextView)findViewById(R.id.synopsis);
        title=(TextView)findViewById(R.id.movie_title);

        Picasso.with(this)
                .load(movieObj.image)
                .into(imageView);
        release_date.setText(movieObj.release_date);
        user_rating.setText(movieObj.user_rating);
        synopsis.setText(movieObj.synopsis);
        title.setText(movieObj.title);




    }

}
