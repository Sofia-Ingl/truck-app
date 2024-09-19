import ru.liga.truckapp.parcel.packaging.ParcelPackager;
import ru.liga.truckapp.parcel.packaging.SteadyBidirectionalPackagingAlgorithm;
import ru.liga.truckapp.parcel.file.ParcelFileHandler;
import ru.liga.truckapp.parcel.file.DefaultParcelFileHandler;
import ru.liga.truckapp.parcel.validation.DefaultParcelValidator;


public class SteadyPackagingAlgorithmTests {

    ParcelFileHandler handler = new DefaultParcelFileHandler(new DefaultParcelValidator());
    ParcelPackager parcelPackager = new SteadyBidirectionalPackagingAlgorithm();


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
