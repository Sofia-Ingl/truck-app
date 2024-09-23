package ru.liga.truckapp.io;

import ru.liga.truckapp.io.enums.CountingAlgorithmType;
import ru.liga.truckapp.io.enums.PackagingAlgorithmType;
import ru.liga.truckapp.parcel.tasks.CountingTaskTemplate;
import ru.liga.truckapp.parcel.tasks.PackagingTaskTemplate;

import java.util.Map;
import java.util.Properties;

public interface UserIOProcessor {

    void processUserIO(Map<CountingAlgorithmType, CountingTaskTemplate> counters,
                       Map<PackagingAlgorithmType, PackagingTaskTemplate> packagers,
                       Properties properties);

}
