package ru.liga.truckapp.parcel.counting;

import ru.liga.truckapp.parcel.entities.Truck;

import java.util.List;
import java.util.Map;

public interface ParcelCounter {

    Map<Integer, Integer> countParcelsInTruck(Truck truck);

    List<Map<Integer, Integer>> countParcels(List<Truck> trucks);

}
