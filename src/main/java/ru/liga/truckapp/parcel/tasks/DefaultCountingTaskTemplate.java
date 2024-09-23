package ru.liga.truckapp.parcel.tasks;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.truckapp.parcel.counting.ParcelCounter;
import ru.liga.truckapp.parcel.entities.Truck;
import ru.liga.truckapp.parcel.file.truck.TruckFileHandler;
import ru.liga.truckapp.parcel.printing.TruckPrinter;

import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class DefaultCountingTaskTemplate implements Runnable, CountingTaskTemplate {

//    private final String inputFileName;

    private final TruckFileHandler truckFileHandler;
    private final ParcelCounter parcelCounter;
    private final TruckPrinter truckPrinter;

    @Override
    public void run() {

//        log.info("Starting counting task...");
//        List<Truck> trucks = truckFileHandler.readTrucks(inputFileName);
//        log.debug("Found trucks: {}", trucks.size());
//
//        List<Map<Integer, Integer>> parcelsQuantityByTypeForEveryTruck
//                = parcelCounter.countParcels(trucks);
//        log.debug("Parcels counted for every truck: {}", parcelsQuantityByTypeForEveryTruck);
//
//        for (int i = 0; i < trucks.size(); i++) {
//            Truck truck = trucks.get(i);
//            Map<Integer, Integer> parcelQuantitiesForTruck = parcelsQuantityByTypeForEveryTruck.get(i);
//            truckPrinter.printParcelQuantityInTruck(truck, parcelQuantitiesForTruck);
//        }
//        log.info("Counting task finished");

    }

    @Override
    public void execute(String inputFileName) {
        log.info("Starting counting task...");
        List<Truck> trucks = truckFileHandler.readTrucks(inputFileName);
        log.debug("Found trucks: {}", trucks.size());

        List<Map<Integer, Integer>> parcelsQuantityByTypeForEveryTruck
                = parcelCounter.countParcels(trucks);
        log.debug("Parcels counted for every truck: {}", parcelsQuantityByTypeForEveryTruck);

        for (int i = 0; i < trucks.size(); i++) {
            Truck truck = trucks.get(i);
            Map<Integer, Integer> parcelQuantitiesForTruck = parcelsQuantityByTypeForEveryTruck.get(i);
            truckPrinter.printParcelQuantityInTruck(truck, parcelQuantitiesForTruck);
        }
        log.info("Counting task finished");
    }
}
