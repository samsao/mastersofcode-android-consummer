package com.oyeoye.consumer.rest.service;

import com.oyeoye.consumer.model.Deal;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by lukasz
 */
public interface DealService {

    @GET("/client/deal")
    void load(Callback<List<Deal>> callback);
}
