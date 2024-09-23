package ru.liga.truckapp.config.creators;

import ru.liga.truckapp.io.enums.PackagingAlgorithmType;

public interface PackagingTaskCreator {

    Runnable createPackagingTask(
            String inputFileName,
            String outputFileName,
            int truckWidth,
            int truckHeight,
            int truckQuantity,
            PackagingAlgorithmType algorithm
    );
}
