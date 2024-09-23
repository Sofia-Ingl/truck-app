package ru.liga.truckapp.io.creators;

import ru.liga.truckapp.io.enums.CountingAlgorithmType;
import ru.liga.truckapp.parcel.counting.DefaultParcelCounter;
import ru.liga.truckapp.parcel.counting.ParcelCounter;
import ru.liga.truckapp.parcel.file.truck.TruckFileHandler;
import ru.liga.truckapp.parcel.file.truck.TruckJsonFileHandler;
import ru.liga.truckapp.parcel.printing.DefaultTruckPrinter;
import ru.liga.truckapp.parcel.printing.TruckPrinter;
import ru.liga.truckapp.parcel.tasks.CountingTaskTemplate;
import ru.liga.truckapp.parcel.tasks.DefaultCountingTaskTemplate;

public class DefaultCountingTaskCreator implements CountingTaskCreator{
    @Override
    public CountingTaskTemplate createCountingTask(CountingAlgorithmType algorithm) {
        TruckFileHandler truckFileHandler = new TruckJsonFileHandler();
        ParcelCounter parcelCounter = switch (algorithm) {
            case DEFAULT -> new DefaultParcelCounter();
        };
        TruckPrinter truckPrinter = new DefaultTruckPrinter();
        return new DefaultCountingTaskTemplate(
                truckFileHandler,
                parcelCounter,
                truckPrinter
        );
    }
}
