import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.exceptions.ValidationException;
import ru.liga.truckapp.parcel.file.ParcelFileHandler;
import ru.liga.truckapp.parcel.file.ParcelFileHandlerImpl;
import ru.liga.truckapp.parcel.validation.ParcelValidatorImpl;

import java.io.IOException;
import java.util.List;

public class ParcelFileHandlerTests {

    ParcelFileHandler handler = new ParcelFileHandlerImpl(new ParcelValidatorImpl());

    @Test
    void readParcelsTest() throws ValidationException, IOException {


        List<Parcel> parcels =
                handler.readAllParcels("src/test/resources/parcel_file_handler_test.txt",
                        6,
                        6);

        Assertions.assertEquals(11, parcels.size());
    }


    @Test
    void readInvalidParcelTest() throws ValidationException, IOException {

        Assertions.assertThrows(ValidationException.class,
                () -> {
                    handler.readAllParcels("src/test/resources/parcel_file_handler_test_invalid.txt",
                            6,
                            6);
                });

    }


    @Test
    void readNotFittingParcelTest() throws ValidationException, IOException {

        Assertions.assertThrows(ValidationException.class,
                () -> {
                    handler.readAllParcels("src/test/resources/parcel_file_handler_test_valid.txt",
                            2,
                            2);
                });

    }

}
