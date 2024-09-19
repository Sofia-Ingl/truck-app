import org.junit.jupiter.api.Test;
import ru.liga.truckapp.parcel.counting.ParcelCounter;
import ru.liga.truckapp.parcel.counting.DefaultParcelCounter;
import ru.liga.truckapp.parcel.entities.Truck;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ParcelCounterTests {

    private final ParcelCounter parcelCounter = new DefaultParcelCounter();

    @Test
    public void countValidParcelsInSingleTruckTest() {

        // для удобства индексации кузовы хранятся в перевернутом по вертикальной оси виде

        char[][] back = new char[][]{
                {'7', '7', '7', '7', '2', '2'},
                {'7', '7', '7', '3', '3', '3'},
                {'7', '7', '7', '7', ' ', ' '},
                {'7', '7', '7', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };
        Truck truck = new Truck(
                6, 6, back
        );

        Map<Integer, Integer> parcelQuantity = parcelCounter.countParcelsInTruck(truck);
        assertThat(parcelQuantity).hasSize(3);
        assertThat(parcelQuantity).containsKey(7);
        assertThat(parcelQuantity).containsKey(3);
        assertThat(parcelQuantity).containsKey(2);
        assertThat(parcelQuantity.get(7)).isEqualTo(2);
        assertThat(parcelQuantity.get(2)).isEqualTo(1);
        assertThat(parcelQuantity.get(3)).isEqualTo(1);

    }

    @Test
    public void countParcelsInMultipleTrucksTest() {

        // для удобства индексации кузовы хранятся в перевернутом по вертикальной оси виде

        char[][] back = new char[][]{
                {'7', '7', '7', '7', '2', '2'},
                {'7', '7', '7', '3', '3', '3'},
                {'7', '7', '7', '7', ' ', ' '},
                {'7', '7', '7', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };
        Truck truck = new Truck(
                6, 6, back
        );

        char[][] back1 = new char[][]{
                {'9', '9', '9', '1', '2', '2'},
                {'9', '9', '9', '3', '3', '3'},
                {'9', '9', '9', '1', ' ', ' '},
                {'3', '3', '3', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };
        Truck truck1 = new Truck(
                6, 6, back1
        );

        List<Truck> trucks = List.of(truck, truck1);

        List<Map<Integer, Integer>> parcelQuantity = parcelCounter.countParcels(trucks);

        assertThat(parcelQuantity).hasSize(2);

        assertThat(parcelQuantity.get(0)).hasSize(3);
        assertThat(parcelQuantity.get(0)).containsKey(7);
        assertThat(parcelQuantity.get(0)).containsKey(3);
        assertThat(parcelQuantity.get(0)).containsKey(2);
        assertThat(parcelQuantity.get(0).get(7)).isEqualTo(2);
        assertThat(parcelQuantity.get(0).get(2)).isEqualTo(1);
        assertThat(parcelQuantity.get(0).get(3)).isEqualTo(1);

        assertThat(parcelQuantity.get(1)).hasSize(4);
        assertThat(parcelQuantity.get(1)).containsKey(9);
        assertThat(parcelQuantity.get(1)).containsKey(3);
        assertThat(parcelQuantity.get(1)).containsKey(2);
        assertThat(parcelQuantity.get(1)).containsKey(1);
        assertThat(parcelQuantity.get(1).get(9)).isEqualTo(1);
        assertThat(parcelQuantity.get(1).get(1)).isEqualTo(2);
        assertThat(parcelQuantity.get(1).get(2)).isEqualTo(1);
        assertThat(parcelQuantity.get(1).get(3)).isEqualTo(2);

    }

    @Test
    public void countInvalidParcelsTest() {

        // для удобства индексации кузовы хранятся в перевернутом по вертикальной оси виде

        char[][] back = new char[][]{
                {'7', '7', '7', ' ', ' ', ' '},
                {'7', '7', '7', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };
        Truck truck = new Truck(
                6, 6, back
        );

        assertThatThrownBy(() -> parcelCounter.countParcelsInTruck(truck))
                .isInstanceOf(RuntimeException.class);


        char[][] back1 = new char[][]{
                {'0', '7', '7', '7', ' ', ' '},
                {'7', '7', '7', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };
        Truck truck1 = new Truck(
                6, 6, back1
        );

        assertThatThrownBy(() -> parcelCounter.countParcelsInTruck(truck1))
                .isInstanceOf(RuntimeException.class);

    }


}
