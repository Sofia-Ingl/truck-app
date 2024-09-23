package ru.liga.truckapp.io.creators;

import ru.liga.truckapp.io.enums.CountingAlgorithmType;
import ru.liga.truckapp.parcel.tasks.CountingTaskTemplate;

public interface CountingTaskCreator {

    CountingTaskTemplate createCountingTask(CountingAlgorithmType algorithmType);
}
