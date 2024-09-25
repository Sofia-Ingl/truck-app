package ru.liga.truckapp.parcel.counting;

import lombok.extern.slf4j.Slf4j;
import ru.liga.truckapp.parcel.entities.Truck;

import java.util.*;

import static ru.liga.truckapp.parcel.validation.ParcelValidationConstants.PARCEL_FILLINGS_ALLOWED;

@Slf4j
public class DefaultParcelCounter implements ParcelCounter {

    /**
     * Функция, подсчитывающая число посылок разных видов в грузовике
     *
     * @param truck грузовик
     * @return число посылок каждого типа, присутствующего в грузовике
     */
    @Override
    public Map<Integer, Integer> countParcelsInTruck(Truck truck) {

        Map<Integer, Integer> parcelsNumberByTypes = new HashMap<>();

        boolean[][] scannedPlaces = new boolean[truck.getHeight()][truck.getWidth()];
        for (int i = 0; i < scannedPlaces.length; i++) {
            Arrays.fill(scannedPlaces[i], false);
        }

        char[][] back = truck.getBack();

        log.debug("Current truck back: {}", Arrays.deepToString(back));

        for (int i = 0; i < truck.getHeight(); i++) {

            for (int j = 0; j < truck.getWidth(); j++) {

                if (!scannedPlaces[i][j]) {

                    char symbol = back[i][j];
                    if (symbol == ' ') {
                        scannedPlaces[i][j] = true;
                        continue;
                    }
                    int typeCode = Integer.parseInt(Character.toString(symbol));

                    boolean isValidParcel = scanCurrentParcel(
                            typeCode,
                            j,
                            i,
                            truck,
                            scannedPlaces
                    );
                    if (!isValidParcel) {
                        log.error("Invalid parcel found in truck");
                        throw new IllegalArgumentException("Invalid parcel occurred in a truck:\n" + truck);
                    }
                    if (parcelsNumberByTypes.containsKey(typeCode)) {
                        parcelsNumberByTypes.put(typeCode, parcelsNumberByTypes.get(typeCode) + 1);
                    } else {
                        parcelsNumberByTypes.put(typeCode, 1);
                    }
                }

            }

        }

        log.debug("parcelsNumberByTypes: {}",parcelsNumberByTypes);
        return parcelsNumberByTypes;
    }

    /**
     * Функция, подсчитывающая число посылок разных видов для каждого грузовика из списка
     *
     * @param trucks список грузовиков
     * @return список мап, каждая из которых включает информацию о числе посылок типов, которые присутствуют в грузовике
     */
    @Override
    public List<Map<Integer, Integer>> countParcels(List<Truck> trucks) {
        List<Map<Integer, Integer>> parcelsQuantityInEveryTruck = new ArrayList<>();
        for (Truck truck : trucks) {
            parcelsQuantityInEveryTruck.add(countParcelsInTruck(truck));
        }
        return parcelsQuantityInEveryTruck;
    }


    private boolean scanCurrentParcel(int typeCode,
                                      int initialX,
                                      int initialY,
                                      Truck truck,
                                      boolean[][] scannedPlaces) {

        log.debug("Scanning parcel with type code: {} - from position (x={},y={})", typeCode, initialX, initialY);

        if (PARCEL_FILLINGS_ALLOWED.containsKey(typeCode)) {

            List<String> currentParcelFilling = PARCEL_FILLINGS_ALLOWED.get(typeCode);
            for (int scanningY = 0; scanningY < currentParcelFilling.size(); scanningY++) {

                String currentLineFilling = currentParcelFilling.get(
                        currentParcelFilling.size() - 1 - scanningY
                );

                for (int scanningX = 0; scanningX < currentLineFilling.length(); scanningX++) {

                    if (initialY + scanningY >= truck.getHeight() || initialX + scanningX >= truck.getWidth()) {
                        return false;
                    }
                    scannedPlaces[initialY + scanningY][initialX + scanningX] = true;
                    char currentSymbol = truck.getBack()[initialY + scanningY][initialX + scanningX];

                    if (currentSymbol != currentLineFilling.charAt(scanningX)) {
                        return false;
                    }
                }
            }
            return true;

        }
        return false;
    }

}
