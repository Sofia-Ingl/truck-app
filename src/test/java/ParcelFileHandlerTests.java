import org.junit.jupiter.api.Test;
import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.exceptions.ValidationException;
import ru.liga.truckapp.parcel.file.parcel.ParcelFileHandler;
import ru.liga.truckapp.parcel.file.parcel.DefaultParcelFileHandler;
import ru.liga.truckapp.parcel.validation.DefaultParcelValidator;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class ParcelFileHandlerTests {

    ParcelFileHandler handler = new DefaultParcelFileHandler(new DefaultParcelValidator());

    @Test
    void readParcelsTest() throws ValidationException, IOException {


        List<Parcel> parcels =
                handler.readAllParcels("src/test/resources/parcel_file_handler_test.txt",
                        6,
                        6);

        assertThat(parcels.size()).isEqualTo(11);
    }


    @Test
    void readInvalidParcelTest() throws ValidationException, IOException {

        assertThatThrownBy(
                () -> {
                    handler.readAllParcels("src/test/resources/parcel_file_handler_test_invalid.txt",
                            6,
                            6);
                }
        ).isInstanceOf(ValidationException.class);

    }


    @Test
    void readNotFittingParcelTest() throws ValidationException, IOException {

        assertThatThrownBy(
                () -> {
                    handler.readAllParcels("src/test/resources/parcel_file_handler_test_valid.txt",
                            2,
                            2);
                }
        ).isInstanceOf(ValidationException.class);

    }

}
