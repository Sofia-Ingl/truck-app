import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.liga.truckapp.parcel.validation.ParcelValidator;
import ru.liga.truckapp.parcel.validation.DefaultParcelValidator;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParcelValidationTests {

    ParcelValidator validator = new DefaultParcelValidator();

    @Test
    void validTypeTest() {

        List<String> validParcel = new ArrayList<>();
        validParcel.add("777");
        validParcel.add("7777");

        assertThat(validator.isValid(validParcel)).isTrue();

    }


    @Test
    void invalidTypesTest() {

        List<String> invalidParcel = new ArrayList<>();
        invalidParcel.add("777");
        invalidParcel.add("7778");

        assertThat(validator.isValid(invalidParcel)).isFalse();

        invalidParcel.clear();

        invalidParcel.add(" 777");
        invalidParcel.add("7777");

        assertThat(validator.isValid(invalidParcel)).isFalse();

        invalidParcel.add("7 77");
        invalidParcel.add("7777");

        assertThat(validator.isValid(invalidParcel)).isFalse();

        invalidParcel.clear();

        invalidParcel.add("7777");
        invalidParcel.add("7777");

        assertThat(validator.isValid(invalidParcel)).isFalse();;

    }

    @Test
    void fitsTruckTest() {

        List<String> validParcel = new ArrayList<>();
        validParcel.add("777");
        validParcel.add("7777");

        int truckHeight = 6;
        int truckWidth = 6;

        assertThat(validator.fitsTruck(validParcel, truckHeight, truckWidth)).isTrue();

    }


    @Test
    void truckTooSmallTest() {

        List<String> validParcel = new ArrayList<>();
        validParcel.add("777");
        validParcel.add("7777");

        int truckHeight = 3;
        int truckWidth = 3;

        assertThat(validator.fitsTruck(validParcel, truckHeight, truckWidth)).isFalse();

    }

}
