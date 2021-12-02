package ua.goit.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class JSONConverter {
    private final Gson gson = new Gson();

    public <T> String convertObjectToJSON(T t){
        return gson.toJson(t);
    }

    public <T> ArrayList<T> convertJSONToList(String response, TypeToken<ArrayList<T>> typeToken){
        return gson.fromJson(response, typeToken.getType());
    }

    public <T> T convertJSONToObject(String response, TypeToken<T> typeToken){
        return gson.fromJson(response, typeToken.getType());
    }
}

