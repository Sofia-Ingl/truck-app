package projects.liga.truckapp.parcel.json;

import com.google.gson.*;
import projects.liga.truckapp.parcel.entities.Truck;

import java.lang.reflect.Type;

public class TruckSerializer implements JsonSerializer<Truck> {

    @Override
    public JsonElement serialize(Truck truck,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {

        JsonArray back = new JsonArray();
        for (int i = truck.getBack().length - 1; i >= 0; i--) {
            back.add(new JsonPrimitive(
                    "+" + String.valueOf(truck.getBack()[i]) + "+"
            ));
        }
        back.add("+".repeat(Math.max(0, truck.getWidth() + 2)));

        return back;
    }

}
