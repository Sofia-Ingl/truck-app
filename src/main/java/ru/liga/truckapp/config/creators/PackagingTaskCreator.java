package ru.liga.truckapp.config.creators;

import ru.liga.truckapp.config.entities.AlgorithmType;

public interface PackagingTaskCreator {

    Runnable createPackagingTask(
            String inputFileName,
            String outputFileName,
            int truckWidth,
            int truckHeight,
            int truckQuantity,
            AlgorithmType algorithm
    );
}
