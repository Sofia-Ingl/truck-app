package ru.liga.truckapp.io.creators;

import ru.liga.truckapp.io.enums.PackagingAlgorithmType;
import ru.liga.truckapp.parcel.tasks.PackagingTaskTemplate;

import java.io.PrintStream;
import java.io.PrintWriter;

public interface PackagingTaskTemplateCreator {

    PackagingTaskTemplate create(PackagingAlgorithmType packagingAlgorithmType, PrintStream userOutput);
}
