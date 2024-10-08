package projects.liga.parcel.file_handling;

import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.exceptions.ValidationException;

import java.io.IOException;
import java.util.NavigableMap;

public interface ParcelFileHandler {


    public NavigableMap<ParcelType, Integer> getParcelQuantityByType(String filename,
                                                                     int truckHeight,
                                                                     int truckWidth) throws IOException, ValidationException;

}
