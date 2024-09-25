package ru.liga.truckapp.parcel.file.truck;

import com.google.gson.JsonParseException;
import org.junit.jupiter.api.Test;
import ru.liga.truckapp.parcel.entities.Truck;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TruckJsonFileHandlerTests {

    TruckFileHandler truckJsonFileHandler = new TruckJsonFileHandler();


    @Test
    void readSingleValidTruckTest() {

        Truck truck = truckJsonFileHandler.readTruck("src/test/resources/single-valid-truck.json");

        char[] firstRow = new char[]{'7', '7', '7', '7', '2', '2'};
        assertThat(truck.getWidth()).isEqualTo(6);
        assertThat(truck.getHeight()).isEqualTo(6);
        assertThat(truck.getBack()[0]).isEqualTo(firstRow);
        assertThat(truck.getOccupiedCapacityByRow()[0]).isEqualTo(6);
        assertThat(truck.getOccupiedCapacityByRow()[1]).isEqualTo(4);
        assertThat(truck.getOccupiedCapacityByRow()[2]).isEqualTo(3);
        assertThat(truck.getOccupiedCapacityByRow()[3]).isEqualTo(0);

    }

    @Test
    void readSingleInvalidTruckTest() {

        assertThatThrownBy(() ->
                truckJsonFileHandler.readTruck("src/test/resources/single-invalid-border-truck.json"))
                .isInstanceOf(JsonParseException.class);


        assertThatThrownBy(() ->
                truckJsonFileHandler.readTruck("src/test/resources/single-invalid-size-truck.json"))
                .isInstanceOf(JsonParseException.class);
    }


    @Test
    void readMultipleValidTrucksTest() {

        List<Truck> trucks = truckJsonFileHandler.readTrucks(
                "src/test/resources/multiple-valid-trucks.json"
        );

        char[] firstTruckfirstRow = new char[]{'7', '7', '7', '7', '2', '2'};
        char[] secondTruckfirstRow = new char[]{'7', '7', '7', '7', '1', '1'};

        assertThat(trucks.size()).isEqualTo(2);

        assertThat(trucks.get(0).getWidth()).isEqualTo(6);
        assertThat(trucks.get(0).getHeight()).isEqualTo(6);
        assertThat(trucks.get(0).getBack()[0]).isEqualTo(firstTruckfirstRow);
        assertThat(trucks.get(0).getOccupiedCapacityByRow()[0]).isEqualTo(6);
        assertThat(trucks.get(0).getOccupiedCapacityByRow()[1]).isEqualTo(4);
        assertThat(trucks.get(0).getOccupiedCapacityByRow()[2]).isEqualTo(3);
        assertThat(trucks.get(0).getOccupiedCapacityByRow()[3]).isEqualTo(0);

        assertThat(trucks.get(1).getWidth()).isEqualTo(6);
        assertThat(trucks.get(1).getHeight()).isEqualTo(6);
        assertThat(trucks.get(1).getBack()[0]).isEqualTo(secondTruckfirstRow);
        assertThat(trucks.get(1).getOccupiedCapacityByRow()[0]).isEqualTo(6);
        assertThat(trucks.get(1).getOccupiedCapacityByRow()[1]).isEqualTo(4);
        assertThat(trucks.get(1).getOccupiedCapacityByRow()[2]).isEqualTo(0);

    }

    @Test
    void writeValidTrucksTest() {

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
        String filename = "src/test/resources/valid-trucks-out.json";

        truckJsonFileHandler.writeTrucks(filename, trucks);
        List<Truck> trucksExtracted = truckJsonFileHandler.readTrucks(filename);

        assertThat(trucksExtracted.size())
                .isEqualTo(trucks.size());

        assertThat(trucksExtracted.get(0).getBack()[0]).isEqualTo(trucks.get(0).getBack()[0]);
        assertThat(trucksExtracted.get(0).getBack()[1]).isEqualTo(trucks.get(0).getBack()[1]);
        assertThat(trucksExtracted.get(0).getBack()[2]).isEqualTo(trucks.get(0).getBack()[2]);
        assertThat(trucksExtracted.get(0).getBack()[3]).isEqualTo(trucks.get(0).getBack()[3]);
        assertThat(trucksExtracted.get(0).getBack()[4]).isEqualTo(trucks.get(0).getBack()[4]);

        assertThat(trucksExtracted.get(1).getBack()[0]).isEqualTo(trucks.get(1).getBack()[0]);
        assertThat(trucksExtracted.get(1).getBack()[1]).isEqualTo(trucks.get(1).getBack()[1]);
        assertThat(trucksExtracted.get(1).getBack()[2]).isEqualTo(trucks.get(1).getBack()[2]);
        assertThat(trucksExtracted.get(1).getBack()[3]).isEqualTo(trucks.get(1).getBack()[3]);
        assertThat(trucksExtracted.get(1).getBack()[4]).isEqualTo(trucks.get(1).getBack()[4]);
    }

}
