package projects.liga.parcel;

import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.entities.Truck;

import java.util.List;
import java.util.NavigableMap;

public interface ParcelPackager {

    public List<Truck> processPackaging(
            int truckWidth,
            int truckHeight,
            NavigableMap<ParcelType, Integer> parcelQuantityByType);

    default void printTrucks(List<Truck> trucks) {
        for (Truck truck : trucks) {
            truck.print();
        }
    }

}
