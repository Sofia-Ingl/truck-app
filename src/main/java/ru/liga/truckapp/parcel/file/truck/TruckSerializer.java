package ru.liga.truckapp.parcel.file.truck;

import com.google.gson.*;
import ru.liga.truckapp.parcel.entities.Truck;

import java.lang.reflect.Type;

public class TruckSerializer implements JsonSerializer<Truck> {

    /**
     * Функция, сериализующая грузовик в формат, принимаемый библиотекой gson
     *
     * @param truck грузовик
     * @param type тип
     * @param jsonSerializationContext контекст сериализации
     * @return сериализованный для gson грузовик
     */
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
