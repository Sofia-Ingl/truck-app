package projects.liga.truckapp.parcel.file;

import projects.liga.truckapp.parcel.entities.Parcel;

import java.io.IOException;
import java.util.List;
import java.util.NavigableMap;

public interface ParcelFileHandler {


    NavigableMap<Parcel, Integer> getParcelQuantityByType(String filename,
                                                          int truckHeight,
                                                          int truckWidth) throws IOException;


    List<Parcel> readAllParcels(String filename,
                                int truckHeight,
                                int truckWidth);

}
