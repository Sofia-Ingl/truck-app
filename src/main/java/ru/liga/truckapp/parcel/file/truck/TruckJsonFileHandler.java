package ru.liga.truckapp.parcel.file.truck;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import ru.liga.truckapp.parcel.entities.Truck;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class TruckJsonFileHandler implements TruckFileHandler {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Truck.class, new TruckSerializer())
            .registerTypeAdapter(Truck.class, new TruckDeserializer())
            .create();

    @Override
    public Truck readTruck(String filename) {

        try {
            String jsonTruck = Files.readString(Paths.get(filename));
            return gson.fromJson(jsonTruck, Truck.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Truck> readTrucks(String filename) {
        try {
            String jsonTrucks = Files.readString(Paths.get(filename));
            Type type = new TypeToken<List<Truck>>() {
            }.getType();
            return gson.fromJson(jsonTrucks, type);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new JsonIOException(e);
        }
    }

    @Override
    public void writeTrucks(String filename, List<Truck> trucks) {

        try {
            String jsonTrucks = gson.toJson(trucks);
            Files.writeString(Paths.get(filename), jsonTrucks);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new JsonIOException(e);
        }


    }


}
