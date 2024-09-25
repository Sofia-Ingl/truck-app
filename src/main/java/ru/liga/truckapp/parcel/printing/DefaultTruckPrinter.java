package ru.liga.truckapp.parcel.printing;

import lombok.AllArgsConstructor;
import ru.liga.truckapp.parcel.entities.Truck;

import java.io.PrintStream;
import java.util.Map;

@AllArgsConstructor
public class DefaultTruckPrinter implements TruckPrinter {

    private final PrintStream out;

    /**
     * Процедура, выводящая грузовик и число посылок разных видов в нем пользователю
     *
     * @param truck грузовик
     * @param parcelQuantity число посылок кажого типа в формате тип: количество
     */
    @Override
    public void printParcelQuantityInTruck(Truck truck, Map<Integer, Integer> parcelQuantity) {

        out.println("-----------------------");
        out.println("TRUCK:");
        truck.print(out);
        out.println("Parcel quantity by type in a truck:");
        out.println(parcelQuantity);
        out.println("-----------------------");

    }

    /**
     * Процедура, выводящая грузовик пользователю
     *
     * @param truck грузовик
     */
    @Override
    public void printTruck(Truck truck) {
        out.println("-----------------------");
        out.println("TRUCK:");
        truck.print(out);
        out.println("-----------------------");
    }
}
