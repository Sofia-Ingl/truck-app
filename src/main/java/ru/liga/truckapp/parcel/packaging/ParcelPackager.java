package ru.liga.truckapp.parcel.packaging;

import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.entities.Truck;

import java.util.List;

public interface ParcelPackager {

    List<Truck> processPackaging(
            int truckWidth,
            int truckHeight,
            int truckQuantity,
            List<Parcel> parcels);

}
