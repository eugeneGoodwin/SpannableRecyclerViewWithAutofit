package com.test.my.spannablerecyclerviewwithautofit.interfaces;

import com.test.my.spannablerecyclerviewwithautofit.retrofit.entries.JsonPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by Android-dev on 22.08.2016.
 */
public interface API {
    @GET("/posts/")
    Call<List<JsonPost>> getPosts();
}
