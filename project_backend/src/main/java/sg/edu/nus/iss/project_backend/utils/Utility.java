package sg.edu.nus.iss.project_backend.utils;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.project_backend.models.Customer;
import sg.edu.nus.iss.project_backend.models.Services;


public class Utility {

     public static JsonObject toJsonObject(String data) {
        JsonReader reader = Json.createReader(new StringReader(data));
        return reader.readObject();
    }

    public static Customer toCustomer(JsonObject json){
        return new Customer(json.getInt("id"), json.getString("name"),
            json.getString("gender"), json.getString("phone"), json.getString("email"));
    }

    public static Services toService(JsonObject json){
        return new Services(json.getInt("id"), json.getString("name"),
            json.getInt("durationInMinutes"), json.getJsonNumber("price").doubleValue());
    }


    
}
