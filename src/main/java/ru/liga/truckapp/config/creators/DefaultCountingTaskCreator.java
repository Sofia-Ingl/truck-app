package ru.liga.truckapp.config.creators;

import lombok.AllArgsConstructor;
import ru.liga.truckapp.parcel.counting.ParcelCounter;
import ru.liga.truckapp.parcel.counting.ParcelCounterImpl;
import ru.liga.truckapp.parcel.json.TruckFileHandler;
import ru.liga.truckapp.parcel.json.TruckJsonFileHandler;
import ru.liga.truckapp.parcel.printing.ParcelQuantityPrinter;
import ru.liga.truckapp.parcel.printing.DefaultParcelQuantityPrinter;
import ru.liga.truckapp.parcel.tasks.CountingTask;


@AllArgsConstructor
public class DefaultCountingTaskCreator implements CountingTaskCreator {

//    private final String inputFileName;

    @Override
    public Runnable createCountingTask(String inputFileName) {
        TruckFileHandler truckFileHandler = new TruckJsonFileHandler();
        ParcelCounter parcelCounter = new ParcelCounterImpl();
        ParcelQuantityPrinter parcelQuantityPrinter = new DefaultParcelQuantityPrinter();
        return new CountingTask(
                inputFileName,
                truckFileHandler,
                parcelCounter,
                parcelQuantityPrinter
        );
    }
}
