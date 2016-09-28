package com.test.my.spannablerecyclerviewwithautofit.model;

import com.test.my.spannablerecyclerviewwithautofit.interfaces.API;
import com.test.my.spannablerecyclerviewwithautofit.interfaces.Callback;
import com.test.my.spannablerecyclerviewwithautofit.retrofit.entries.JsonPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android-dev on 22.08.2016.
 */
public class NetModel {
    private static NetModel netModel;
    public static final String SERVER_ENDPOINT = "https://jsonplaceholder.typicode.com/";

    public static NetModel instance() {
        if (netModel == null) {
            netModel = new NetModel();
        }
        return netModel;
    }

    private static API api;

    public static API getApi() {
        return api;
    }

    public NetModel(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }

    public void getPosts(final Callback<List<JsonPost>> callback, final Callback<String> callbackError){
        getApi().getPosts().enqueue(new retrofit2.Callback<List<JsonPost>>() {
            @Override
            public void onResponse(Call<List<JsonPost>> call, Response<List<JsonPost>> response) {
                callback.T(response.body());
            }

            @Override
            public void onFailure(Call<List<JsonPost>> call, Throwable t) {
                callbackError.T(t.getMessage());
            }
        });
    }
}
