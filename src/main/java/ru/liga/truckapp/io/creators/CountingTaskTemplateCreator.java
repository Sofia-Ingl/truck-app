package ru.liga.truckapp.io.creators;

import ru.liga.truckapp.io.enums.CountingAlgorithmType;
import ru.liga.truckapp.parcel.tasks.CountingTaskTemplate;

import java.io.PrintStream;
import java.io.PrintWriter;

public interface CountingTaskTemplateCreator {

    CountingTaskTemplate create(CountingAlgorithmType algorithmType, PrintStream userOutput);
}
