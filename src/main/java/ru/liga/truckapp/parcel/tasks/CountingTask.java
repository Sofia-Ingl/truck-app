package ru.liga.truckapp.parcel.tasks;

import lombok.AllArgsConstructor;
import ru.liga.truckapp.parcel.counting.ParcelCounter;
import ru.liga.truckapp.parcel.counting.ParcelCounterImpl;
import ru.liga.truckapp.parcel.entities.Truck;
import ru.liga.truckapp.parcel.json.TruckJsonFileHandler;
import ru.liga.truckapp.parcel.json.TruckJsonFileHandlerImpl;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
public class CountingTask implements Runnable {

    private final String inputFileName;

    @Override
    public void run() {

        try {

            TruckJsonFileHandler truckJsonFileHandler = new TruckJsonFileHandlerImpl();
            Truck truck = truckJsonFileHandler.readTruck(inputFileName);
            ParcelCounter parcelCounter = new ParcelCounterImpl();
            Map<Integer, Integer> parcelsQuantityByType = parcelCounter.countParcels(truck);

            System.out.println("Parcels quantity by type in a truck:");
            System.out.println(parcelsQuantityByType);

        } catch (IOException e) {
            // TODO logging
        }

    }
}
