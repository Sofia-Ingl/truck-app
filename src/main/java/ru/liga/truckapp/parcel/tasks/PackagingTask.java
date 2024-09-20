package ru.liga.truckapp.parcel.tasks;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.truckapp.parcel.entities.Truck;
import ru.liga.truckapp.parcel.file.truck.TruckFileHandler;
import ru.liga.truckapp.parcel.packaging.ParcelPackager;
import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.file.parcel.ParcelFileHandler;

import java.util.List;

@Slf4j
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

        log.info("Starting packaging task...");
        List<Parcel> parcels = parcelFileHandler.readAllParcels(
                inputFileName,
                truckHeight,
                truckWidth);

        log.debug("Found {} parcels", parcels.size());

        List<Truck> trucks = parcelPackager.processPackaging(truckWidth,
                truckHeight,
                truckQuantity,
                parcels);

        log.debug("Final trucks quantity is {}", trucks.size());

        truckFileHandler.writeTrucks(outputFileName, trucks);
        log.info("Packaging task finished");

    }
}
