import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.exceptions.ValidationException;
import projects.liga.parcel.file_handling.ParcelFileHandler;
import projects.liga.parcel.file_handling.ParcelFileHandlerImpl;

import java.io.IOException;
import java.util.NavigableMap;

public class ParcelFileHandlerTests {

    ParcelFileHandler handler = new ParcelFileHandlerImpl();

    @Test
    void readParcelsTest() throws ValidationException, IOException {

        NavigableMap<ParcelType, Integer> parcelQuantity =
                handler.getParcelQuantityByType("src/test/resources/parcel_file_handler_test.txt",
                        6,
                        6);

        Assertions.assertEquals(9, parcelQuantity.size());
        Assertions.assertEquals(3, parcelQuantity.firstEntry().getValue());
        Assertions.assertEquals(1, parcelQuantity.lastEntry().getValue());

    }


    @Test
    void readInvalidParcelTest() throws ValidationException, IOException {

        Assertions.assertThrows(ValidationException.class,
                () -> {
                    handler.getParcelQuantityByType("src/test/resources/parcel_file_handler_test_invalid.txt",
                            6,
                            6);
                });

    }


    @Test
    void readNotFittingParcelTest() throws ValidationException, IOException {

        Assertions.assertThrows(ValidationException.class,
                () -> {
                    handler.getParcelQuantityByType("src/test/resources/parcel_file_handler_test_valid.txt",
                            2,
                            2);
                });

    }

}
