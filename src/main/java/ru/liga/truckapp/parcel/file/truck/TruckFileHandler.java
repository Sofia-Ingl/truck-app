package ru.liga.truckapp.parcel.file.truck;

import ru.liga.truckapp.parcel.entities.Truck;

import java.util.List;

public interface TruckFileHandler {

    Truck readTruck(String filename);

    List<Truck> readTrucks(String filename);

    void writeTrucks(String filename, List<Truck> trucks);

}
