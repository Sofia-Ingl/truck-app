package ru.liga.truckapp.io.creators;

import ru.liga.truckapp.io.enums.PackagingAlgorithmType;
import ru.liga.truckapp.parcel.tasks.PackagingTaskTemplate;

public interface PackagingTaskTemplateCreator {

    PackagingTaskTemplate create(PackagingAlgorithmType packagingAlgorithmType);
}
