//package com.oyeoye.consumer.rest.gson;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.TypeAdapter;
//import com.google.gson.TypeAdapterFactory;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonWriter;
//import com.oyeoye.consumer.model.Transaction;
//
//import java.io.IOException;
//import java.util.List;
//
///**
// * Created by lukas on 6/17/14.
// */
//public class TransactionsTypeAdapterFactory implements TypeAdapterFactory {
//
//    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {
//
//        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
//        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
//
//        return new TypeAdapter<T>() {
//
//            public void write(JsonWriter out, T value) throws IOException {
//                delegate.write(out, value);
//            }
//
//            public T read(JsonReader in) throws IOException {
//                if (!type.getType().equals(new TypeToken<List<Transaction>>() {
//                }.getType())) {
//                    return delegate.read(in);
//                }
//
//                JsonElement jsonElement = elementAdapter.read(in);
//                JsonArray array = jsonElement.getAsJsonArray();
//
//                for (int i = 0; i < array.size(); i++) {
//                    JsonObject object = array.get(i).getAsJsonObject();
//                    JsonElement element = object.get("merchant");
//                    if (element.isJsonPrimitive()) {
//                        object.remove("merchant");
//                    }
//                }
//
//                return delegate.fromJsonTree(jsonElement);
//            }
//        }.nullSafe();
//    }
//}
