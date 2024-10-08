import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import projects.liga.parcel.ParcelPackager;
import projects.liga.parcel.SingleParcelAlgorithm;
import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.entities.Truck;
import projects.liga.parcel.exceptions.ValidationException;
import projects.liga.parcel.file_handling.ParcelFileHandler;
import projects.liga.parcel.file_handling.ParcelFileHandlerImpl;

import java.io.IOException;
import java.util.List;
import java.util.NavigableMap;

public class SingleParcelAlgorithmTests {

    ParcelFileHandler handler = new ParcelFileHandlerImpl();
    ParcelPackager parcelPackager = new SingleParcelAlgorithm();

    @Test
    public void testTrucksNumber() throws ValidationException, IOException {

        int truckWidth = 6;
        int truckHeight = 6;

        NavigableMap<ParcelType, Integer> parcelQuantity =
                handler.getParcelQuantityByType("src/test/resources/input_test.txt",
                        truckHeight,
                        truckWidth);

        List<Truck> trucks = parcelPackager.processPackaging(
                truckWidth, truckHeight, parcelQuantity
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
}
