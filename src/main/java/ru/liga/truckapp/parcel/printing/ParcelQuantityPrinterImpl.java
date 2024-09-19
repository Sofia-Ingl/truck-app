package ru.liga.truckapp.parcel.printing;

import ru.liga.truckapp.parcel.entities.Truck;

import java.util.Map;

public class ParcelQuantityPrinterImpl implements ParcelQuantityPrinter {

    @Override
    public void print(Truck truck, Map<Integer, Integer> parcelQuantity) {

        System.out.println("-----------------------");
        System.out.println("TRUCK:");
        truck.print();
        System.out.println("Parcel quantity by type in a truck:");
        System.out.println(parcelQuantity);
        System.out.println("-----------------------");

    }
}
