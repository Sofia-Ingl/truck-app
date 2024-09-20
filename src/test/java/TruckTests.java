import org.junit.jupiter.api.Test;
import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.entities.Truck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TruckTests {

    @Test
    public void loadParcelOnFreeSpaceTest() {

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

        char[][] parcelFilling = new char[][]{
                {'2', '2'}
        };
        Parcel parcel = new Parcel(
                2, 2, 1, true, parcelFilling
        );

        boolean canLoad = truck.canLoadParcel(4, 2, parcel);
        assertThat(canLoad).isTrue();

        assertThat(truck.getOccupiedCapacityByRow()[2]).isEqualTo(4);

        truck.loadParcel(4, 2, parcel);

        char[] slice = truck.getRowSlice(4, 2);
        assertThat(slice.length).isEqualTo(2);
        assertThat(slice[0]).isEqualTo('2');
        assertThat(slice[1]).isEqualTo('2');

        canLoad = truck.canLoadParcel(4, 2, parcel);
        assertThat(canLoad).isFalse();

        assertThat(truck.getOccupiedCapacityByRow()[2]).isEqualTo(6);

    }


    @Test
    public void tryLoadParcelWhenPlaceAlreadyOccupiedTest() {

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

        char[][] parcelFilling = new char[][]{
                {'2', '2'}
        };
        Parcel parcel = new Parcel(
                2, 2, 1, true, parcelFilling
        );

        boolean canLoad = truck.canLoadParcel(0, 0, parcel);
        assertThat(canLoad).isFalse();

        assertThatThrownBy(() -> truck.loadParcel(0, 0, parcel))
                .isInstanceOf(IllegalArgumentException.class);


        char[] slice = truck.getRowSlice(0, 0);
        assertThat(slice[0]).isEqualTo('7');
        assertThat(slice[1]).isEqualTo('7');

    }

}
