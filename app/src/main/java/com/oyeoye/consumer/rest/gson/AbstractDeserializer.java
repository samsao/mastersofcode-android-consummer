package com.oyeoye.consumer.rest.gson;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;

/**
 * Created by lukas on 6/8/14.
 */
public abstract class AbstractDeserializer<T> implements JsonDeserializer<T> {

    protected long getAsLong(JsonObject object, String name) {
        return object.has(name) ? object.getAsJsonPrimitive(name).getAsLong() : 0;
    }

    protected int getAsInt(JsonObject object, String name) {
        return object.has(name) ? object.getAsJsonPrimitive(name).getAsInt() : 0;
    }

    protected String getAsString(JsonObject object, String name) {
        return object.has(name) ? object.getAsJsonPrimitive(name).getAsString() : null;
    }

    // DOES NOT WORK WITH BOOL AS 1:0 in json
//    protected boolean getAsBoolean(JsonObject object, String name) {
//        return object.has(name) ? object.getAsJsonPrimitive(name).getAsBoolean() : false;
//    }
}
