package com.example.dailyhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dailyhunt.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    NewsHeadlines headlines;
    TextView text_news_title,text_news_author,text_news_time,text_news_news,text_news_content;
    ImageView img_news;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        text_news_title = findViewById(R.id.text_news_title);
        text_news_author = findViewById(R.id.text_news_author);
        text_news_time= findViewById(R.id.text_news_time);
        text_news_news = findViewById(R.id.text_news_news);
        text_news_content = findViewById(R.id.text_news_content);
        img_news = findViewById(R.id.img_news);
        btn= findViewById(R.id.btn_news);


        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");

        text_news_title.setText(headlines.getTitle());
        text_news_author.setText(headlines.getAuthor());
        text_news_time.setText(headlines.getPublishedAt());
        text_news_news.setText(headlines.getDescription());
        text_news_content.setText(headlines.getContent());
        Picasso.get().load(headlines.getUrlToImage()).into(img_news);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(headlines.getUrl());
                Intent t = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(t);

            }
        });


    }
}