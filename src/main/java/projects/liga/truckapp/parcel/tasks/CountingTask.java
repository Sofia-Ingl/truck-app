package projects.liga.truckapp.parcel.tasks;

import lombok.AllArgsConstructor;
import projects.liga.truckapp.parcel.counting.ParcelCounter;
import projects.liga.truckapp.parcel.counting.ParcelCounterImpl;
import projects.liga.truckapp.parcel.entities.Truck;
import projects.liga.truckapp.parcel.json.TruckJsonFileHandler;
import projects.liga.truckapp.parcel.json.TruckJsonFileHandlerImpl;

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
            Map<String, Integer> parcelsQuantityByType = parcelCounter.countParcels(truck);

            for (String type : parcelsQuantityByType.keySet()) {
                System.out.println(type + ": " + parcelsQuantityByType.get(type));
                // TODO logging COMMON REPORT OUT
            }

        } catch (IOException e) {
            // TODO logging
        }

    }
}
