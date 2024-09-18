package ru.liga.truckapp.parcel.tasks;

import lombok.AllArgsConstructor;
import ru.liga.truckapp.config.entities.AlgorithmType;
import ru.liga.truckapp.parcel.entities.Truck;
import ru.liga.truckapp.parcel.json.TruckJsonFileHandler;
import ru.liga.truckapp.parcel.json.TruckJsonFileHandlerImpl;
import ru.liga.truckapp.parcel.packaging.OptimizedPackagingAlgorithm;
import ru.liga.truckapp.parcel.packaging.ParcelPackager;
import ru.liga.truckapp.parcel.packaging.SteadyBidirectionalPackagingAlgorithm;
import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.file.ParcelFileHandler;
import ru.liga.truckapp.parcel.file.ParcelFileHandlerImpl;
import ru.liga.truckapp.parcel.validation.ParcelValidatorImpl;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class PackagingTask implements Runnable {

    private final String inputFileName;
    private final String outputFileName;
    private final int truckWidth;
    private final int truckHeight;
    private final int truckQuantity;
    private final AlgorithmType algorithm;

    @Override
    public void run() {

        try {
            ParcelFileHandler parcelFileHandler = new ParcelFileHandlerImpl(new ParcelValidatorImpl());

            List<Parcel> parcels = parcelFileHandler.readAllParcels(
                    inputFileName,
                    truckHeight,
                    truckWidth);

            ParcelPackager parcelPackager = switch (algorithm) {
                case OPTIMIZED -> new OptimizedPackagingAlgorithm();
                case STEADY_BIDIRECTIONAL -> new SteadyBidirectionalPackagingAlgorithm();
            };

            List<Truck> trucks = parcelPackager.processPackaging(truckWidth,
                    truckHeight,
                    truckQuantity,
                    parcels);

            TruckJsonFileHandler truckJsonFileHandler = new TruckJsonFileHandlerImpl();
            truckJsonFileHandler.writeTrucks(outputFileName, trucks);

        } catch (IOException e) {
            // TODO logging
        }
    }
}
