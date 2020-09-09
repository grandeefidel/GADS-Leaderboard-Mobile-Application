package com.fidelity.fidel_gads_2020_leaderboard;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private final static String BASE_URL = "https://docs.google.com/forms/d/e/";
    public static HttpClient mIstance;
    private Retrofit mRetrofit;

    private HttpClient(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized HttpClient getInstance(){
        if (mIstance == null){
            mIstance = new HttpClient();
        }
        return mIstance;
    }

    public SubmitApi getApi(){
        return mRetrofit.create(SubmitApi.class);
    }
}
