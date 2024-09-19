package ru.liga.truckapp.parcel.printing;

import ru.liga.truckapp.parcel.entities.Truck;

import java.util.Map;

public interface ParcelQuantityPrinter {

    void print(Truck truck, Map<Integer, Integer> parcelQuantity);

}
