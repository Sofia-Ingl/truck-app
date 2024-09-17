package projects.liga.truckapp.parcel.packaging;

import projects.liga.truckapp.parcel.entities.Parcel;
import projects.liga.truckapp.parcel.entities.Truck;

import java.util.List;
import java.util.NavigableMap;

public interface ParcelPackager {


    List<Truck> processPackaging(
            int truckWidth,
            int truckHeight,
            int truckQuantity,
            List<Parcel> parcels);

}
