package projects.liga.parcel;

import projects.liga.parcel.entities.ParcelType;

import java.io.IOException;
import java.util.NavigableMap;

public interface ParcelFileHandler {


    public NavigableMap<ParcelType, Integer> getParcelQuantityByType(String filename) throws IOException;

}
