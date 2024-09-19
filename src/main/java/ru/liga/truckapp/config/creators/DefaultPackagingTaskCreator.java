package ru.liga.truckapp.config.creators;

import lombok.AllArgsConstructor;
import ru.liga.truckapp.config.entities.AlgorithmType;
import ru.liga.truckapp.parcel.file.ParcelFileHandler;
import ru.liga.truckapp.parcel.file.DefaultParcelFileHandler;
import ru.liga.truckapp.parcel.json.TruckFileHandler;
import ru.liga.truckapp.parcel.json.TruckJsonFileHandler;
import ru.liga.truckapp.parcel.packaging.OptimizedPackagingAlgorithm;
import ru.liga.truckapp.parcel.packaging.ParcelPackager;
import ru.liga.truckapp.parcel.packaging.SteadyBidirectionalPackagingAlgorithm;
import ru.liga.truckapp.parcel.tasks.PackagingTask;
import ru.liga.truckapp.parcel.validation.DefaultParcelValidator;


@AllArgsConstructor
public class DefaultPackagingTaskCreator implements PackagingTaskCreator {


    @Override
    public Runnable createPackagingTask(
            String inputFileName,
            String outputFileName,
            int truckWidth,
            int truckHeight,
            int truckQuantity,
            AlgorithmType algorithm
    ) {

        ParcelFileHandler parcelFileHandler = new DefaultParcelFileHandler(new DefaultParcelValidator());
        ParcelPackager parcelPackager = switch (algorithm) {
            case OPTIMIZED -> new OptimizedPackagingAlgorithm();
            case STEADY_BIDIRECTIONAL -> new SteadyBidirectionalPackagingAlgorithm();
        };
        TruckFileHandler truckFileHandler = new TruckJsonFileHandler();

        return new PackagingTask(
                inputFileName,
                outputFileName,
                truckWidth,
                truckHeight,
                truckQuantity,
                parcelFileHandler,
                parcelPackager,
                truckFileHandler
        );
    }
}
