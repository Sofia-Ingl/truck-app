package ru.liga.truckapp.parcel.printing;

import ru.liga.truckapp.parcel.entities.Truck;

import java.util.Map;

public interface TruckPrinter {

    void printParcelQuantityInTruck(Truck truck, Map<Integer, Integer> parcelQuantity);

    void printTruck(Truck truck);

}
