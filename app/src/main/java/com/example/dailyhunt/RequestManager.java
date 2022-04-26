package com.example.dailyhunt;

import android.content.Context;

import com.example.dailyhunt.Models.NewsApiResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public void getNewsHeadlines(OnFetchDataListener listener, String category,String query){
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse> call = callNewsApi.callHeadlines("in",category,query,40,context.getString(R.string.Api_key));
        try {
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if (!response.isSuccessful()){
                        FancyToast.makeText(context,"Error",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    }
                    listener.onFetchData(response.body().getArticles(),response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {

                    listener.onError("Request Unsuccessful");

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public RequestManager(Context context) {
        this.context = context;
    }

    public interface  CallNewsApi{
        @GET("top-headlines")
        Call<NewsApiResponse> callHeadlines(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("pageSize") int pageSize,
                @Query("apiKey") String api_key
        );
    }
}
