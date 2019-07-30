package br.com.ufs.orionframework.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * This class handle all empty fields comming from the response.
 * Your objective is to handle the empty fields exceptions from Gson.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see br.com.ufs.orionframework.orion.Orion
 */
public class IntTypeAdapter extends TypeAdapter<Integer> {
    @Override
    public Integer read(JsonReader reader) throws IOException {
        if(reader.peek() == JsonToken.NULL){
            reader.nextNull();
            return null;
        }
        String stringValue = reader.nextString();
        try{
            return Integer.valueOf(stringValue);
        }catch(NumberFormatException e){
            return null;
        }
    }
    @Override
    public void write(JsonWriter writer, Integer value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }
}

