package ru.liga.truckapp.parcel.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.liga.truckapp.parcel.entities.Truck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TruckJsonFileHandlerImpl implements TruckJsonFileHandler {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Truck.class, new TruckSerializer())
            .registerTypeAdapter(Truck.class, new TruckDeserializer())
            .create();

    @Override
    public Truck readTruck(String filename) throws IOException {

        String jsonTruck = Files.readString(Paths.get(filename));

        return gson.fromJson(jsonTruck, Truck.class);
    }

    @Override
    public void writeTrucks(String filename, List<Truck> trucks) throws IOException  {

        String jsonTrucks = gson.toJson(trucks);
        Files.writeString(Paths.get(filename), jsonTrucks);

    }





}
