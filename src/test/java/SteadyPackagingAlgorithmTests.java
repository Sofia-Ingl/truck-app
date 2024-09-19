import org.junit.jupiter.api.Test;
import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.entities.Truck;
import ru.liga.truckapp.parcel.packaging.ParcelPackager;
import ru.liga.truckapp.parcel.packaging.SteadyBidirectionalPackagingAlgorithm;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class SteadyPackagingAlgorithmTests {

    ParcelPackager parcelPackager = new SteadyBidirectionalPackagingAlgorithm();

    @Test
    public void trucksQuantityBiggerThanNumberOfParcelsTest() {

        int truckWidth = 6;
        int truckHeight = 6;
        int truckQuantity = 10;

        List<Parcel> parcels = List.of(
                TestingConstants.PARCEL_TYPES.get(1),
                TestingConstants.PARCEL_TYPES.get(2),
                TestingConstants.PARCEL_TYPES.get(3)
        );

        List<Truck> trucks = parcelPackager.processPackaging(
                truckWidth, truckHeight, truckQuantity, parcels
        );

        assertThat(trucks.size()).isEqualTo(3);

        Truck truck = trucks.get(0);

        assertThat(truck.getOccupiedCapacityByRow())
                .containsExactly(3, 0, 0, 0, 0, 0);
        assertThat(truck.getBack()[0]).containsExactly('3', '3', '3', ' ', ' ', ' ');

        truck = trucks.get(1);

        assertThat(truck.getOccupiedCapacityByRow())
                .containsExactly(2, 0, 0, 0, 0, 0);
        assertThat(truck.getBack()[0]).containsExactly('2', '2', ' ', ' ', ' ', ' ');

        truck = trucks.get(2);

        assertThat(truck.getOccupiedCapacityByRow())
                .containsExactly(1, 0, 0, 0, 0, 0);
        assertThat(truck.getBack()[0]).containsExactly('1', ' ', ' ', ' ', ' ', ' ');


    }



    @Test
    public void trucksQuantityLessThanNumberOfParcelsTest() {

        int truckWidth = 6;
        int truckHeight = 6;
        int truckQuantity = 1;

        List<Parcel> parcels = List.of(
                TestingConstants.PARCEL_TYPES.get(1),
                TestingConstants.PARCEL_TYPES.get(2),
                TestingConstants.PARCEL_TYPES.get(3)
        );

        List<Truck> trucks = parcelPackager.processPackaging(
                truckWidth, truckHeight, truckQuantity, parcels
        );
        assertThat(trucks.size()).isEqualTo(1);

        Truck truck = trucks.get(0);

        assertThat(truck.getOccupiedCapacityByRow())
                .containsExactly(6, 0, 0, 0, 0, 0);
        assertThat(truck.getBack()[0]).containsExactly('3', '3', '3', '2', '2', '1');
    }


    @Test
    public void stabilityTest() {

        int truckWidth = 6;
        int truckHeight = 6;
        int truckQuantity = 3;

        List<Parcel> parcels = List.of(
                TestingConstants.PARCEL_TYPES.get(7),
                TestingConstants.PARCEL_TYPES.get(7),
                TestingConstants.PARCEL_TYPES.get(9),
                TestingConstants.PARCEL_TYPES.get(2),
                TestingConstants.PARCEL_TYPES.get(2),
                TestingConstants.PARCEL_TYPES.get(6),
                TestingConstants.PARCEL_TYPES.get(6),
                TestingConstants.PARCEL_TYPES.get(6),
                TestingConstants.PARCEL_TYPES.get(3),
                TestingConstants.PARCEL_TYPES.get(3),
                TestingConstants.PARCEL_TYPES.get(3)
        );

        List<Truck> trucks = parcelPackager.processPackaging(
                truckWidth, truckHeight, truckQuantity, parcels
        );


        assertThat(trucks.size()).isEqualTo(3);

        Truck truck = trucks.get(0);

        assertThat(truck.getOccupiedCapacityByRow())
                .containsExactly(6, 6, 6, 0, 0, 0);
        assertThat(truck.getBack()[0]).containsExactly('9', '9', '9', '6', '6', '6');
        assertThat(truck.getBack()[1]).containsExactly('9', '9', '9', '6', '6', '6');
        assertThat(truck.getBack()[2]).containsExactly('9', '9', '9', '3', '3', '3');

        Truck truck1 = trucks.get(1);

        assertThat(truck1.getOccupiedCapacityByRow())
                .containsExactly(6, 6, 6, 0, 0, 0);
        assertThat(truck1.getBack()[0]).containsExactly('7', '7', '7', '7', '2', '2');
        assertThat(truck1.getBack()[1]).containsExactly('7', '7', '7', '6', '6', '6');
        assertThat(truck1.getBack()[2]).containsExactly('3', '3', '3', '6', '6', '6');


        Truck truck2 = trucks.get(2);

        assertThat(truck2.getOccupiedCapacityByRow())
                .containsExactly(6, 6, 6, 0, 0, 0);
        assertThat(truck2.getBack()[0]).containsExactly('7', '7', '7', '7', '2', '2');
        assertThat(truck2.getBack()[1]).containsExactly('7', '7', '7', '6', '6', '6');
        assertThat(truck2.getBack()[2]).containsExactly('3', '3', '3', '6', '6', '6');


    }

}
