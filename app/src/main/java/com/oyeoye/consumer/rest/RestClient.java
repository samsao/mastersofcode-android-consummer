package com.oyeoye.consumer.rest;

import com.google.gson.GsonBuilder;
import com.oyeoye.consumer.App;
import com.oyeoye.consumer.AppConstants;
import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.rest.service.UserService;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by lukasz on 01/12/14.
 */
@DaggerScope(App.class)
public class RestClient {

    private UserService userService;

    @Inject
    public RestClient() {
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(StationState.class, new StationStateDeserializer());

        OkHttpClient client = new OkHttpClient();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(AppConstants.API_URL)
                .setConverter(new GsonConverter(gsonBuilder.create()));

        RestAdapter restAdapter = builder.build();

        userService = restAdapter.create(UserService.class);
    }

    public UserService getUserService() {
        return userService;
    }
}
