package com.oyeoye.consumer.rest.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.oyeoye.consumer.model.Transaction;

import java.io.IOException;

/**
 * Created by lukas on 6/17/14.
 */
public class TransactionTypeAdapterFactory implements TypeAdapterFactory {

    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {

            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            public T read(JsonReader in) throws IOException {
                if (!type.getRawType().equals(Transaction.class)) {
                    return delegate.read(in);
                }

                JsonElement jsonElement = elementAdapter.read(in);

                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonElement element = jsonObject.get("deal");

                if (element.isJsonPrimitive()) {
                    jsonObject.remove("deal");
                }

                return delegate.fromJsonTree(jsonObject);
            }
        }.nullSafe();
    }
}
