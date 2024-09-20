package ru.liga.truckapp.parcel.tasks;

import lombok.AllArgsConstructor;
import ru.liga.truckapp.parcel.counting.ParcelCounter;
import ru.liga.truckapp.parcel.entities.Truck;
import ru.liga.truckapp.parcel.json.TruckFileHandler;
import ru.liga.truckapp.parcel.printing.ParcelQuantityPrinter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class CountingTask implements Runnable {

    private final String inputFileName;

    private final TruckFileHandler truckFileHandler;
    private final ParcelCounter parcelCounter;
    private final ParcelQuantityPrinter parcelQuantityPrinter;

    @Override
    public void run() {

        List<Truck> trucks = truckFileHandler.readTrucks(inputFileName);
        List<Map<Integer, Integer>> parcelsQuantityByTypeForEveryTruck
                = parcelCounter.countParcels(trucks);
        for (int i = 0; i < trucks.size(); i++) {
            Truck truck = trucks.get(i);
            Map<Integer, Integer> parcelQuantitiesForTruck = parcelsQuantityByTypeForEveryTruck.get(i);
            parcelQuantityPrinter.print(truck, parcelQuantitiesForTruck);
        }

    }
}
