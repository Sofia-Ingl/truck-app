package ru.liga.truckapp.parcel.file.truck;

import com.google.gson.*;
import ru.liga.truckapp.parcel.entities.Truck;

import java.lang.reflect.Type;

public class TruckDeserializer implements JsonDeserializer<Truck> {

    @Override
    public Truck deserialize(JsonElement jsonElement,
                             Type type,
                             JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonArray jsonBack = jsonElement.getAsJsonArray();
        int height = jsonBack.size() - 1;
        int width = jsonBack.get(0).getAsString().length() - 2;
        char[][] back = new char[height][width];

        for (int i = 0; i < height; i++) {
            String line = jsonBack.get(i).getAsString();
            if (width != line.length() - 2) {
                throw new JsonParseException("Truck line has wrong length: " + width + " instead of " + (line.length() - 2));
            }
            if (line.charAt(0) != '+' || line.charAt(line.length() - 1) != '+') {
                throw new JsonParseException("Truck has wrong borders: " + line);
            }
            back[back.length - 1 - i] = line.substring(1, line.length() - 1).toCharArray();
        }

        String bottom = jsonBack.get(jsonBack.size() - 1).getAsString();
        for (int i = 0; i < bottom.length() - 1; i++) {
            if (bottom.charAt(i) != '+') {
                throw new JsonParseException("Truck has wrong bottom: " + bottom);
            }
        }

        return new Truck(width, height, back);
    }

}
