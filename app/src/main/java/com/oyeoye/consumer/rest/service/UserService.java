package com.oyeoye.consumer.rest.service;

import com.oyeoye.consumer.model.User;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by lukasz
 */
public interface UserService {

    @POST("/connect/reg")
    @FormUrlEncoded
    void connect(@Field("phone") String phone, @Field("gcmId") String gcmId, Callback<User> callback);
}
