package ru.liga.truckapp.parcel.counting;

import ru.liga.truckapp.parcel.entities.Truck;

import java.util.Map;

public interface ParcelCounter {

    Map<Integer, Integer> countParcels(Truck truck);

}
