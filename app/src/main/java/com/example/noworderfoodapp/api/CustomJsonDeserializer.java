package com.example.noworderfoodapp.api;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomJsonDeserializer extends StdDeserializer<Object> {
    public CustomJsonDeserializer() {
        this(null);
    }

    protected CustomJsonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        // In ra thông tin đối tượng JSON để kiểm tra
        Log.i("KMFG", "deserialize: "+node.toString());
        return null; // Trả về null vì chúng ta chỉ quan tâm đến việc in ra thông tin JSON
    }
}
