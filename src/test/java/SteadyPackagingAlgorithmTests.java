import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import projects.liga.truckapp.parcel.packaging.ParcelPackager;
import projects.liga.truckapp.parcel.packaging.SteadyPackagingAlgorithm;
import projects.liga.truckapp.parcel.entities.Parcel;
import projects.liga.truckapp.parcel.entities.Truck;
import projects.liga.truckapp.parcel.exceptions.ValidationException;
import projects.liga.truckapp.parcel.file.ParcelFileHandler;
import projects.liga.truckapp.parcel.file.ParcelFileHandlerImpl;

import java.io.IOException;
import java.util.List;
import java.util.NavigableMap;

public class SteadyPackagingAlgorithmTests {

    ParcelFileHandler handler = new ParcelFileHandlerImpl();
    ParcelPackager parcelPackager = new SteadyPackagingAlgorithm();
/*
    @Test
    public void testTrucksNumber() throws ValidationException, IOException {

        int truckWidth = 6;
        int truckHeight = 6;
        int truckQuantity = 10;


        NavigableMap<Parcel, Integer> parcelQuantity =
                handler.getParcelQuantityByType("src/test/resources/input_test.txt",
                        truckHeight,
                        truckWidth);

        List<Truck> trucks = parcelPackager.processPackaging(
                truckWidth, truckHeight, truckQuantity, parcelQuantity
        );

        Assertions.assertEquals(5, trucks.size());

        Truck truck = trucks.get(0);

        Assertions.assertEquals('1', truck.getBack()[0][0]);

        truck = trucks.get(1);

        Assertions.assertEquals('2', truck.getBack()[0][0]);

        truck = trucks.get(2);

        Assertions.assertEquals('3', truck.getBack()[0][0]);

        truck = trucks.get(3);

        Assertions.assertEquals('7', truck.getBack()[0][0]);

        truck = trucks.get(4);

        Assertions.assertEquals('5', truck.getBack()[0][0]);

    }

 */
}
