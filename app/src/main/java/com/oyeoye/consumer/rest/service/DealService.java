package com.oyeoye.consumer.rest.service;

import com.oyeoye.consumer.model.Deal;
import com.oyeoye.consumer.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by lukasz
 */
public interface DealService {

    @GET("/deal")
    void load(Callback<List<Deal>> callback);
}
