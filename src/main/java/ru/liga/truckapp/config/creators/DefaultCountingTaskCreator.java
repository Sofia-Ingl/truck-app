package ru.liga.truckapp.config.creators;

import lombok.AllArgsConstructor;
import ru.liga.truckapp.parcel.counting.ParcelCounter;
import ru.liga.truckapp.parcel.counting.DefaultParcelCounter;
import ru.liga.truckapp.parcel.file.truck.TruckFileHandler;
import ru.liga.truckapp.parcel.file.truck.TruckJsonFileHandler;
import ru.liga.truckapp.parcel.printing.TruckPrinter;
import ru.liga.truckapp.parcel.printing.DefaultTruckPrinter;
import ru.liga.truckapp.parcel.tasks.DefaultCountingTask;


@AllArgsConstructor
public class DefaultCountingTaskCreator implements CountingTaskCreator {

    @Override
    public Runnable createCountingTask(String inputFileName) {
        TruckFileHandler truckFileHandler = new TruckJsonFileHandler();
        ParcelCounter parcelCounter = new DefaultParcelCounter();
        TruckPrinter truckPrinter = new DefaultTruckPrinter();
        return new DefaultCountingTask(
                inputFileName,
                truckFileHandler,
                parcelCounter,
                truckPrinter
        );
    }
}
