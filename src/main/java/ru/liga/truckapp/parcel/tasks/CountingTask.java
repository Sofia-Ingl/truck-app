package ru.liga.truckapp.parcel.tasks;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.truckapp.parcel.counting.ParcelCounter;
import ru.liga.truckapp.parcel.entities.Truck;
import ru.liga.truckapp.parcel.file.truck.TruckFileHandler;
import ru.liga.truckapp.parcel.printing.ParcelQuantityPrinter;

import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class CountingTask implements Runnable {

    private final String inputFileName;

    private final TruckFileHandler truckFileHandler;
    private final ParcelCounter parcelCounter;
    private final ParcelQuantityPrinter parcelQuantityPrinter;

    @Override
    public void run() {

        log.info("Starting counting task...");
        List<Truck> trucks = truckFileHandler.readTrucks(inputFileName);
        log.debug("Found trucks: {}", trucks.size());

        List<Map<Integer, Integer>> parcelsQuantityByTypeForEveryTruck
                = parcelCounter.countParcels(trucks);
        log.debug("Parcels counted for every truck: {}", parcelsQuantityByTypeForEveryTruck);

        for (int i = 0; i < trucks.size(); i++) {
            Truck truck = trucks.get(i);
            Map<Integer, Integer> parcelQuantitiesForTruck = parcelsQuantityByTypeForEveryTruck.get(i);
            parcelQuantityPrinter.print(truck, parcelQuantitiesForTruck);
        }
        log.info("Counting task finished");

    }
}
