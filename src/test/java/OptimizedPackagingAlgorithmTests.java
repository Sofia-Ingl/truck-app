import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import projects.liga.truckapp.parcel.packaging.OptimizedPackagingAlgorithm;
import projects.liga.truckapp.parcel.packaging.ParcelPackager;
import projects.liga.truckapp.parcel.entities.Parcel;
import projects.liga.truckapp.parcel.entities.Truck;
import projects.liga.truckapp.parcel.exceptions.ValidationException;
import projects.liga.truckapp.parcel.file.ParcelFileHandler;
import projects.liga.truckapp.parcel.file.ParcelFileHandlerImpl;

import java.io.IOException;
import java.util.List;
import java.util.NavigableMap;

public class OptimizedPackagingAlgorithmTests {

    ParcelFileHandler handler = new ParcelFileHandlerImpl();
    ParcelPackager parcelPackager = new OptimizedPackagingAlgorithm();
/*
    @Test
    public void testDensity() throws ValidationException, IOException {

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

        Assertions.assertEquals(1, trucks.size());

        Truck truck = trucks.get(0);

        Assertions.assertEquals(truckWidth, truck.getWidth());
        Assertions.assertEquals(truckHeight, truck.getHeight());

        Assertions.assertEquals(6, truck.getOccupiedCapacityByRow()[0]);
        Assertions.assertEquals(6, truck.getOccupiedCapacityByRow()[1]);
        Assertions.assertEquals(6, truck.getOccupiedCapacityByRow()[2]);
        Assertions.assertEquals(0, truck.getOccupiedCapacityByRow()[3]);

        Assertions.assertEquals('5', truck.getBack()[0][0]);
        Assertions.assertEquals('7', truck.getBack()[1][0]);
        Assertions.assertEquals('7', truck.getBack()[2][0]);

    }


    @Test
    public void testNoHanging() throws ValidationException, IOException {

        int truckWidth = 6;
        int truckHeight = 6;
        int truckQuantity = 10;

        NavigableMap<Parcel, Integer> parcelQuantity =
                handler.getParcelQuantityByType("src/test/resources/no_hanging_test.txt",
                        truckHeight,
                        truckWidth);

        List<Truck> trucks = parcelPackager.processPackaging(
                truckWidth, truckHeight, truckQuantity, parcelQuantity
        );

        Assertions.assertEquals(1, trucks.size());

        Truck truck = trucks.get(0);

        Assertions.assertEquals(5, truck.getOccupiedCapacityByRow()[0]);
        Assertions.assertEquals(4, truck.getOccupiedCapacityByRow()[1]);
        Assertions.assertEquals(2, truck.getOccupiedCapacityByRow()[2]);
        Assertions.assertEquals(0, truck.getOccupiedCapacityByRow()[3]);

        Assertions.assertEquals('5', truck.getBack()[0][0]);
        Assertions.assertEquals('4', truck.getBack()[1][0]);
        Assertions.assertEquals('2', truck.getBack()[2][0]);

    }



    @Test
    public void testNotEnoughSpace() throws ValidationException, IOException {

        int truckWidth = 6;
        int truckHeight = 6;
        int truckQuantity = 10;

        NavigableMap<Parcel, Integer> parcelQuantity =
                handler.getParcelQuantityByType("src/test/resources/2cars_test.txt",
                        truckHeight,
                        truckWidth);

        List<Truck> trucks = parcelPackager.processPackaging(
                truckWidth, truckHeight, truckQuantity, parcelQuantity
        );

        Assertions.assertEquals(2, trucks.size());

        Truck truck = trucks.get(1);

        Assertions.assertEquals(3, truck.getOccupiedCapacityByRow()[0]);
        Assertions.assertEquals(3, truck.getOccupiedCapacityByRow()[1]);
        Assertions.assertEquals(3, truck.getOccupiedCapacityByRow()[2]);
        Assertions.assertEquals(0, truck.getOccupiedCapacityByRow()[3]);

        Assertions.assertEquals('9', truck.getBack()[0][0]);

    }


 */



}
