package ru.liga.truckapp.parcel.json;

import ru.liga.truckapp.parcel.entities.Truck;

import java.io.IOException;
import java.util.List;

public interface TruckFileHandler {

    Truck readTruck(String filename) throws IOException ;

    List<Truck> readTrucks(String filename) throws IOException ;

    void writeTrucks(String filename, List<Truck> trucks)  throws IOException;

}
