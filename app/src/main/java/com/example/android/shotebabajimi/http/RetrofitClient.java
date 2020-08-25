package com.example.android.shotebabajimi.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit sInstance;

    public static Retrofit getsInstance() {
        if (sInstance == null) {
            sInstance = new Retrofit.Builder()
                    .baseUrl("https://run.mocky.io/v3/b4cdeed3-327b-4591-9b06-aaf043e65497")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return sInstance;
    }

    private RetrofitClient() {

    }
}
