package projects.liga.truckapp.parcel.counting;

import projects.liga.truckapp.parcel.entities.Truck;

import java.util.Map;

public interface ParcelCounter {

    Map<Integer, Integer> countParcels(Truck truck);

}
