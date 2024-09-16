import org.junit.jupiter.api.Test;
import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.entities.Truck;
import projects.liga.parcel.file_handling.ParcelFileHandler;
import projects.liga.parcel.file_handling.ParcelFileHandlerImpl;
import projects.liga.parcel.json.TruckJsonFileHandler;
import projects.liga.parcel.json.TruckJsonFileHandlerImpl;
import projects.liga.parcel.packaging.CommonAlgorithm;
import projects.liga.parcel.packaging.ParcelPackager;

import java.io.IOException;
import java.util.List;
import java.util.NavigableMap;

public class JsonFileHandlerTests {

    ParcelFileHandler handler = new ParcelFileHandlerImpl();
    ParcelPackager parcelPackager = new CommonAlgorithm();
    TruckJsonFileHandler truckJsonFileHandler = new TruckJsonFileHandlerImpl();

//    @Test
//    void test() throws IOException {
//        int truckWidth = 6;
//        int truckHeight = 6;
//
//        NavigableMap<ParcelType, Integer> parcelQuantity =
//                handler.getParcelQuantityByType("src/test/resources/input_test.txt",
//                        truckHeight,
//                        truckWidth);
//
//        List<Truck> trucks = parcelPackager.processPackaging(
//                truckWidth, truckHeight, parcelQuantity
//        );
//
//        truckJsonFileHandler.writeTrucks("file.json", trucks);
//        Truck truck = truckJsonFileHandler.readTruck("file1.json");
//        truck.print();
//
//    }

}
