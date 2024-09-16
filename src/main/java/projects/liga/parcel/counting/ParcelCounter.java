package projects.liga.parcel.counting;

import projects.liga.parcel.entities.Truck;

import java.util.Map;

public interface ParcelCounter {

    Map<String, Integer> countParcels(Truck truck);

}
