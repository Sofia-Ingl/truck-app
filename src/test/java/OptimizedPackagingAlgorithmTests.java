import org.junit.jupiter.api.Test;
import ru.liga.truckapp.parcel.exceptions.PackagingException;
import ru.liga.truckapp.parcel.packaging.OptimizedPackagingAlgorithm;
import ru.liga.truckapp.parcel.packaging.ParcelPackager;
import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.entities.Truck;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OptimizedPackagingAlgorithmTests {

    ParcelPackager parcelPackager = new OptimizedPackagingAlgorithm();

    @Test
    public void densityTest() {

        int truckWidth = 6;
        int truckHeight = 6;
        int truckQuantity = 10;


        List<Parcel> parcels = List.of(
                TestingConstants.PARCEL_TYPES.get(1),
                TestingConstants.PARCEL_TYPES.get(2),
                TestingConstants.PARCEL_TYPES.get(3),
                TestingConstants.PARCEL_TYPES.get(5),
                TestingConstants.PARCEL_TYPES.get(7)
        );


        List<Truck> trucks = parcelPackager.processPackaging(
                truckWidth, truckHeight, truckQuantity, parcels
        );

        assertThat(trucks.size()).isEqualTo(1);

        Truck truck = trucks.get(0);

        assertThat(truck.getWidth()).isEqualTo(truckWidth);
        assertThat(truck.getHeight()).isEqualTo(truckHeight);
        assertThat(truck.getOccupiedCapacityByRow())
                .containsExactly(6, 6, 6, 0, 0, 0);
        assertThat(truck.getBack()[0]).containsExactly('5', '5', '5', '5', '5', '1');
        assertThat(truck.getBack()[1]).containsExactly('7', '7', '7', '7', '2', '2');
        assertThat(truck.getBack()[2]).containsExactly('7', '7', '7', '3', '3', '3');

    }


    @Test
    public void noHangingTest() {

        int truckWidth = 6;
        int truckHeight = 6;
        int truckQuantity = 10;

        List<Parcel> parcels = List.of(
                TestingConstants.PARCEL_TYPES.get(2),
                TestingConstants.PARCEL_TYPES.get(4),
                TestingConstants.PARCEL_TYPES.get(5)
        );

        List<Truck> trucks = parcelPackager.processPackaging(
                truckWidth, truckHeight, truckQuantity, parcels
        );

        assertThat(trucks.size()).isEqualTo(1);

        Truck truck = trucks.get(0);

        assertThat(truck.getWidth()).isEqualTo(truckWidth);
        assertThat(truck.getHeight()).isEqualTo(truckHeight);
        assertThat(truck.getOccupiedCapacityByRow())
                .containsExactly(5, 4, 2, 0, 0, 0);
        assertThat(truck.getBack()[0]).containsExactly('5', '5', '5', '5', '5', ' ');
        assertThat(truck.getBack()[1]).containsExactly('4', '4', '4', '4', ' ', ' ');
        assertThat(truck.getBack()[2]).containsExactly('2', '2', ' ', ' ', ' ', ' ');


    }


    @Test
    public void moreThanOneTruckRequiredTest() {

        int truckWidth = 6;
        int truckHeight = 6;
        int truckQuantity = 10;

        List<Parcel> parcels = List.of(
                TestingConstants.PARCEL_TYPES.get(7),
                TestingConstants.PARCEL_TYPES.get(7),
                TestingConstants.PARCEL_TYPES.get(9)
        );

        List<Truck> trucks = parcelPackager.processPackaging(
                truckWidth, truckHeight, truckQuantity, parcels
        );

        assertThat(trucks.size()).isEqualTo(2);

        Truck truck = trucks.get(0);

        assertThat(truck.getOccupiedCapacityByRow())
                .containsExactly(4, 3, 4, 3, 0, 0);
        assertThat(truck.getBack()[0]).containsExactly('7', '7', '7', '7', ' ', ' ');
        assertThat(truck.getBack()[1]).containsExactly('7', '7', '7', ' ', ' ', ' ');
        assertThat(truck.getBack()[2]).containsExactly('7', '7', '7', '7', ' ', ' ');
        assertThat(truck.getBack()[3]).containsExactly('7', '7', '7', ' ', ' ', ' ');

        Truck truck1 = trucks.get(1);

        assertThat(truck1.getOccupiedCapacityByRow())
                .containsExactly(3, 3, 3, 0, 0, 0);
        assertThat(truck1.getBack()[0]).containsExactly('9', '9', '9', ' ', ' ', ' ');
        assertThat(truck1.getBack()[1]).containsExactly('9', '9', '9', ' ', ' ', ' ');
        assertThat(truck1.getBack()[2]).containsExactly('9', '9', '9', ' ', ' ', ' ');

    }


    @Test
    public void trucksQuantityNotEnoughTest() {

        int truckWidth = 6;
        int truckHeight = 6;
        int truckQuantity = 1;

        List<Parcel> parcels = List.of(
                TestingConstants.PARCEL_TYPES.get(7),
                TestingConstants.PARCEL_TYPES.get(7),
                TestingConstants.PARCEL_TYPES.get(9)
        );

        assertThatThrownBy(() ->
                parcelPackager.processPackaging(truckWidth, truckHeight, truckQuantity, parcels)
        ).isInstanceOf(PackagingException.class);

    }


}
