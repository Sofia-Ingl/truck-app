package projects.liga.parcel.packaging;

import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.entities.Truck;

import java.util.List;
import java.util.NavigableMap;

public interface ParcelPackager {

    List<Truck> processPackaging(
            int truckWidth,
            int truckHeight,
            int truckQuantity,
            NavigableMap<ParcelType, Integer> parcelQuantityByType);

    default void printTrucks(List<Truck> trucks) {
        for (Truck truck : trucks) {
            truck.print();
        }
    }

}
