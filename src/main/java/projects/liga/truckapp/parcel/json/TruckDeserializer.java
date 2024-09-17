package projects.liga.truckapp.parcel.json;

import com.google.gson.*;
import projects.liga.truckapp.parcel.entities.Truck;

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
                throw new JsonParseException("Truck line has wrong length!");
            }
            back[back.length - 1 - i] = line.substring(1, line.length() - 1).toCharArray();
        }

        return new Truck(width, height, back);
    }

}
