import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.liga.truckapp.parcel.validation.ParcelValidator;
import ru.liga.truckapp.parcel.validation.ParcelValidatorImpl;

import java.util.ArrayList;
import java.util.List;

public class ParcelValidationTests {

    ParcelValidator validator = new ParcelValidatorImpl();

    @Test
    void testValidType() {

        List<String> validParcel = new ArrayList<>();
        validParcel.add("777");
        validParcel.add("7777");

        Assertions.assertTrue(validator.isValid(validParcel));

    }


    @Test
    void testInvalidTypes() {

        List<String> invalidParcel = new ArrayList<>();
        invalidParcel.add("777");
        invalidParcel.add("7778");

        Assertions.assertFalse(validator.isValid(invalidParcel));

        invalidParcel.clear();

        invalidParcel.add(" 777");
        invalidParcel.add("7777");

        Assertions.assertFalse(validator.isValid(invalidParcel));

        invalidParcel.add("7 77");
        invalidParcel.add("7777");

        Assertions.assertFalse(validator.isValid(invalidParcel));

        invalidParcel.clear();

        invalidParcel.add("7777");
        invalidParcel.add("7777");

        Assertions.assertFalse(validator.isValid(invalidParcel));

    }

    @Test
    void testFitsTruck() {

        List<String> validParcel = new ArrayList<>();
        validParcel.add("777");
        validParcel.add("7777");

        int truckHeight = 6;
        int truckWidth = 6;

        Assertions.assertTrue(validator.fitsTruck(validParcel, truckHeight, truckWidth));

    }


    @Test
    void testTruckTooSmall() {

        List<String> validParcel = new ArrayList<>();
        validParcel.add("777");
        validParcel.add("7777");

        int truckHeight = 3;
        int truckWidth = 3;

        Assertions.assertFalse(validator.fitsTruck(validParcel, truckHeight, truckWidth));

    }

}
