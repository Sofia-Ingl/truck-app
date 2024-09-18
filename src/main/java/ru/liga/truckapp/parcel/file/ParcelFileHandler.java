package ru.liga.truckapp.parcel.file;

import ru.liga.truckapp.parcel.entities.Parcel;

import java.util.List;

public interface ParcelFileHandler {


    List<Parcel> readAllParcels(String filename,
                                int truckHeight,
                                int truckWidth);

}
