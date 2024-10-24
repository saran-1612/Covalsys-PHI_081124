package com.covalsys.phi_scanner.utils;

/**
 * Created by CBS on 09-07-2020.
 */
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class GsonBooleanTypeAdapter implements JsonDeserializer<Boolean>
{
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        try {
            int code = json.getAsInt();
            return code != 0;
        }catch (Exception ex){
            return  Boolean.valueOf(String.valueOf(json));
        }
    }
}
