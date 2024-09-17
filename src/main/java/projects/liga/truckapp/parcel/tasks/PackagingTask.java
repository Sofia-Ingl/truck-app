package projects.liga.truckapp.parcel.tasks;

import lombok.AllArgsConstructor;
import projects.liga.truckapp.config.entities.AlgorithmType;
import projects.liga.truckapp.parcel.entities.Truck;
import projects.liga.truckapp.parcel.json.TruckJsonFileHandler;
import projects.liga.truckapp.parcel.json.TruckJsonFileHandlerImpl;
import projects.liga.truckapp.parcel.packaging.OptimizedPackagingAlgorithm;
import projects.liga.truckapp.parcel.packaging.ParcelPackager;
import projects.liga.truckapp.parcel.packaging.SteadyPackagingAlgorithm;
import projects.liga.truckapp.parcel.entities.Parcel;
import projects.liga.truckapp.parcel.file.ParcelFileHandler;
import projects.liga.truckapp.parcel.file.ParcelFileHandlerImpl;

import java.io.IOException;
import java.util.List;
import java.util.NavigableMap;

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
            ParcelFileHandler parcelFileHandler = new ParcelFileHandlerImpl();
//            NavigableMap<Parcel, Integer> parcelTypesMap = parcelFileHandler.getParcelQuantityByType(
//                    inputFileName,
//                    truckHeight,
//                    truckWidth);

            List<Parcel> parcels = parcelFileHandler.readAllParcels(
                    inputFileName,
                    truckHeight,
                    truckWidth);

            ParcelPackager parcelPackager;
            if (algorithm == AlgorithmType.OPTIMIZED) {
                parcelPackager = new OptimizedPackagingAlgorithm();
            } else {
                parcelPackager = new SteadyPackagingAlgorithm();
            }

            // TODO add quantity
//            List<Truck> trucks = parcelPackager.processPackaging(truckWidth,
//                    truckHeight,
//                    truckQuantity,
//                    parcelTypesMap);

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
