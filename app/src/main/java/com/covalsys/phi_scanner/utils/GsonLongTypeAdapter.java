package com.covalsys.phi_scanner.utils;

/**
 * Created by CBS on 09-07-2020.
 */
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class GsonLongTypeAdapter implements JsonDeserializer<Long>
{
    public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        try {
            String val = json.getAsString();
            if(val == null || val.isEmpty() ) return 0L;
            return json.getAsLong();
        }catch (Exception ex){
            return  0L;
        }
    }


}
