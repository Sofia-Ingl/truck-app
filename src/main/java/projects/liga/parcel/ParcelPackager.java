package projects.liga.parcel;

import projects.liga.parcel.entities.ParcelType;

import java.util.NavigableMap;

public interface ParcelPackager {

    void processPackaging(NavigableMap<ParcelType, Integer> parcelQuantityByType);

}
