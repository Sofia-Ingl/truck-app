package ru.liga.truckapp.config.creators;

import lombok.AllArgsConstructor;
import ru.liga.truckapp.config.entities.AlgorithmType;
import ru.liga.truckapp.parcel.file.parcel.ParcelFileHandler;
import ru.liga.truckapp.parcel.file.parcel.DefaultParcelFileHandler;
import ru.liga.truckapp.parcel.file.truck.TruckFileHandler;
import ru.liga.truckapp.parcel.file.truck.TruckJsonFileHandler;
import ru.liga.truckapp.parcel.packaging.OptimizedPackagingAlgorithm;
import ru.liga.truckapp.parcel.packaging.ParcelPackager;
import ru.liga.truckapp.parcel.packaging.SteadyBidirectionalPackagingAlgorithm;
import ru.liga.truckapp.parcel.printing.DefaultTruckPrinter;
import ru.liga.truckapp.parcel.printing.TruckPrinter;
import ru.liga.truckapp.parcel.tasks.DefaultPackagingTask;
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
        TruckPrinter truckPrinter = new DefaultTruckPrinter();

        return new DefaultPackagingTask(
                inputFileName,
                outputFileName,
                truckWidth,
                truckHeight,
                truckQuantity,
                parcelFileHandler,
                parcelPackager,
                truckFileHandler,
                truckPrinter
        );
    }
}
