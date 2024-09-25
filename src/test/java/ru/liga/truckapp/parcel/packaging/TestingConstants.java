package ru.liga.truckapp.parcel.packaging;

import ru.liga.truckapp.parcel.entities.Parcel;

import java.util.HashMap;
import java.util.Map;

public final class TestingConstants {

    public final static Map<Integer, Parcel> PARCEL_TYPES = new HashMap<>();

    static {
        Parcel parcel1 = new Parcel(
                1, 1, 1, true, new char[][]{{'1'}}
        );
        PARCEL_TYPES.put(1, parcel1);

        Parcel parcel2 = new Parcel(
                2, 2, 1, true, new char[][]{{'2', '2'}}
        );
        PARCEL_TYPES.put(2, parcel2);

        Parcel parcel3 = new Parcel(
                3, 3, 1, true, new char[][]{{'3', '3', '3'}}
        );
        PARCEL_TYPES.put(3, parcel3);

        Parcel parcel4 = new Parcel(
                4, 4, 1, true, new char[][]{{'4', '4', '4', '4'}}
        );
        PARCEL_TYPES.put(4, parcel4);

        Parcel parcel5 = new Parcel(
                5, 5, 1, true, new char[][]{{'5', '5', '5', '5', '5'}}
        );
        PARCEL_TYPES.put(5, parcel5);

        Parcel parcel6 = new Parcel(
                6, 3, 2, true, new char[][]{{'6', '6', '6'}, {'6', '6', '6'}}
        );
        PARCEL_TYPES.put(6, parcel6);

        Parcel parcel7 = new Parcel(
                7, 4, 2, false, new char[][]{{'7', '7', '7', ' '}, {'7', '7', '7', '7'}}
        );
        PARCEL_TYPES.put(7, parcel7);

        Parcel parcel8 = new Parcel(
                8, 4, 2, true, new char[][]{{'8', '8', '8', '8'}, {'8', '8', '8', '8'}}
        );
        PARCEL_TYPES.put(8, parcel8);

        Parcel parcel9 = new Parcel(
                9, 3, 3, true, new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}}
        );
        PARCEL_TYPES.put(9, parcel9);
    }
}
