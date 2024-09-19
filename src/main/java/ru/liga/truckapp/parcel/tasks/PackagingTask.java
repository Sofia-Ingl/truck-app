package ru.liga.truckapp.parcel.tasks;

import lombok.AllArgsConstructor;
import ru.liga.truckapp.parcel.entities.Truck;
import ru.liga.truckapp.parcel.json.TruckFileHandler;
import ru.liga.truckapp.parcel.packaging.ParcelPackager;
import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.file.ParcelFileHandler;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class PackagingTask implements Runnable {

    private final String inputFileName;
    private final String outputFileName;
    private final int truckWidth;
    private final int truckHeight;
    private final int truckQuantity;

    private final ParcelFileHandler parcelFileHandler;
    private final ParcelPackager parcelPackager;
    private final TruckFileHandler truckFileHandler;

    @Override
    public void run() {


        List<Parcel> parcels = parcelFileHandler.readAllParcels(
                inputFileName,
                truckHeight,
                truckWidth);

        List<Truck> trucks = parcelPackager.processPackaging(truckWidth,
                truckHeight,
                truckQuantity,
                parcels);

        truckFileHandler.writeTrucks(outputFileName, trucks);

    }
}
