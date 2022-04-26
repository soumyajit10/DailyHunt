package com.example.dailyhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dailyhunt.Models.NewsApiResponse;
import com.example.dailyhunt.Models.NewsHeadlines;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView= findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener,"general",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading News");
        dialog.show();

        btn1= findViewById(R.id.btn1);
        btn1.setOnClickListener(this::onClick);
        btn2= findViewById(R.id.btn2);
        btn2.setOnClickListener(this::onClick);
        btn3= findViewById(R.id.btn3);
        btn3.setOnClickListener(this::onClick);
        btn4= findViewById(R.id.btn4);
        btn4.setOnClickListener(this::onClick);
        btn5= findViewById(R.id.btn5);
        btn5.setOnClickListener(this::onClick);
        btn6= findViewById(R.id.btn6);
        btn6.setOnClickListener(this::onClick);
        btn7= findViewById(R.id.btn7);
        btn7.setOnClickListener(this::onClick);



        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,"general",null);



    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if (list.isEmpty()){
                FancyToast.makeText(MainActivity.this,"No data Found",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
            }else {
                showNews(list);
                dialog.dismiss();
            }

        }

        @Override
        public void onError(String message) {
            FancyToast.makeText(MainActivity.this,"Error :)",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();

        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.Recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CustomAdapter(this,list,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this,NewsActivity.class).putExtra("data",headlines));


    }

    @Override
    public void onClick(View v) {
        Button button= (Button) v;
        String category = button.getText().toString();
        dialog.setTitle("Fetching articles of " + category);
        dialog.show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,category,null);

    }
}