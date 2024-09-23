package ru.liga.truckapp.io.creators;

import ru.liga.truckapp.io.enums.PackagingAlgorithmType;
import ru.liga.truckapp.parcel.tasks.PackagingTaskTemplate;

public interface PackagingTaskCreator {

    PackagingTaskTemplate createPackagingTask(PackagingAlgorithmType packagingAlgorithmType);
}
