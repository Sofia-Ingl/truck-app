package projects.liga.parcel.tasks;

import lombok.AllArgsConstructor;
import projects.liga.config.entities.AlgorithmType;
import projects.liga.parcel.entities.Truck;
import projects.liga.parcel.json.TruckJsonFileHandler;
import projects.liga.parcel.json.TruckJsonFileHandlerImpl;
import projects.liga.parcel.packaging.CommonAlgorithm;
import projects.liga.parcel.packaging.ParcelPackager;
import projects.liga.parcel.packaging.SingleParcelAlgorithm;
import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.file_handling.ParcelFileHandler;
import projects.liga.parcel.file_handling.ParcelFileHandlerImpl;

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
            NavigableMap<ParcelType, Integer> parcelTypesMap = parcelFileHandler.getParcelQuantityByType(
                    inputFileName,
                    truckHeight,
                    truckWidth);

            ParcelPackager parcelPackager;
            if (algorithm == AlgorithmType.OPTIMIZED) {
                parcelPackager = new CommonAlgorithm();
            } else {
                parcelPackager = new SingleParcelAlgorithm();
            }

            // TODO add quantity
            List<Truck> trucks = parcelPackager.processPackaging(truckWidth,
                    truckHeight,
                    truckQuantity,
                    parcelTypesMap);

            TruckJsonFileHandler truckJsonFileHandler = new TruckJsonFileHandlerImpl();
            truckJsonFileHandler.writeTrucks(outputFileName, trucks);

        } catch (IOException e) {
            // TODO logging
        }
    }
}
