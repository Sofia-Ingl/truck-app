package projects.liga.parcel;

import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.entities.Truck;

import java.util.*;

public class SixDimensionalAlgorithm implements ParcelPackager {

    private final int PARCEL_TYPE_1 = 1;
    private final int PARCEL_TYPE_2 = 2;
    private final int PARCEL_TYPE_3 = 3;
    private final int PARCEL_TYPE_4 = 4;
    private final int PARCEL_TYPE_5 = 5;
    private final int PARCEL_TYPE_6 = 6;
    private final int PARCEL_TYPE_7 = 7;
    private final int PARCEL_TYPE_8 = 8;
    private final int PARCEL_TYPE_9 = 9;

    private final int TRUCK_WIDTH = 6;
    private final int TRUCK_HEIGHT = 6;

    private Map<Integer, Integer[]> parcelTypeComplements;
    private Map<Integer, Integer[][]> parcelTypeEquivalences;

    public SixDimensionalAlgorithm() {

        parcelTypeComplements = new HashMap<>();
        parcelTypeComplements.put(PARCEL_TYPE_1, new Integer[]{5});
        parcelTypeComplements.put(PARCEL_TYPE_2, new Integer[]{4});
        parcelTypeComplements.put(PARCEL_TYPE_3, new Integer[]{3});
        parcelTypeComplements.put(PARCEL_TYPE_4, new Integer[]{2});
        parcelTypeComplements.put(PARCEL_TYPE_5, new Integer[]{1});
        parcelTypeComplements.put(PARCEL_TYPE_6, new Integer[]{6});
        parcelTypeComplements.put(PARCEL_TYPE_7, new Integer[]{2, 3});
        parcelTypeComplements.put(PARCEL_TYPE_8, new Integer[]{2, 2});
        parcelTypeComplements.put(PARCEL_TYPE_9, new Integer[]{9});

        parcelTypeEquivalences = new HashMap<>();
//        parcelTypeEquivalences.put(PARCEL_TYPE_1, new Integer[][]{{1}});
        parcelTypeEquivalences.put(PARCEL_TYPE_2, new Integer[][]{{1, 1}});
        parcelTypeEquivalences.put(PARCEL_TYPE_3, new Integer[][]{{2, 1}});
        parcelTypeEquivalences.put(PARCEL_TYPE_4, new Integer[][]{{3, 1}, {2, 2}});
        parcelTypeEquivalences.put(PARCEL_TYPE_5, new Integer[][]{{3, 2}});
        parcelTypeEquivalences.put(PARCEL_TYPE_6, new Integer[][]{{3, 3}});
        parcelTypeEquivalences.put(PARCEL_TYPE_7, new Integer[][]{{4, 3}});
        parcelTypeEquivalences.put(PARCEL_TYPE_8, new Integer[][]{{4, 4}});
        parcelTypeEquivalences.put(PARCEL_TYPE_9, new Integer[][]{{6, 3}});
    }


    @Override
    public void processPackaging(NavigableMap<ParcelType, Integer> parcelQuantityByType) {

        List<Truck> oddTrucks;
        List<Truck> evenTrucks;
        List<Truck> fullyLoadedTrucks;
        List<Truck> standaloneSevenTrucks;

//        List<Truck> oddTrucksWithWidthDividedBy3;
//        List<Truck> oddTrucksWithWidthDividedBy2;
//        List<Truck> oddTrucksWithWidthDividedBy1;
//
//        List<Truck> evenTrucksWithWidthDividedBy3;
//        List<Truck> evenTrucksWithWidthDividedBy2;
//        List<Truck> evenTrucksWithWidthDividedBy1;
//
//
//        List<Truck> evenTrucksWithWidthDividedBy1;


        NavigableMap<Integer, Integer> simplifiedParcelQuantityByType = new TreeMap<>();
        for (ParcelType parcelType : parcelQuantityByType.keySet()) {

            if (parcelType.getTypeCode() > PARCEL_TYPE_9 || parcelType.getTypeCode() < PARCEL_TYPE_1) {
                // error
            }

            simplifiedParcelQuantityByType.put(parcelType.getTypeCode(),
                    parcelQuantityByType.get(parcelType)
            );

        }

        for (Integer parcelTypeCode : simplifiedParcelQuantityByType.descendingKeySet()) {

            while (simplifiedParcelQuantityByType.get(parcelTypeCode) != null) {

                // reserve current type parcel

                simplifiedParcelQuantityByType.put(parcelTypeCode,
                        simplifiedParcelQuantityByType.get(parcelTypeCode) - 1);

                // try to find complement

                Integer[] complements = parcelTypeComplements.get(parcelTypeCode);

//                if (complements == null) {
//                    // error
//                }

                NavigableMap<Integer, Integer> currentComplementsMap = new TreeMap<>();

                for (Integer complement : complements) {
                    if (currentComplementsMap.containsKey(complement)) {
                        currentComplementsMap.put(complement, currentComplementsMap.get(complement) + 1);
                    } else {
                        currentComplementsMap.put(complement, 1);
                    }
                }

                boolean canFinishBlock = true;

                for (Integer complement : currentComplementsMap.descendingKeySet()) {

                    if (currentComplementsMap.get(complement)
                            > simplifiedParcelQuantityByType.get(complement)) {

                        // not enough parcels of such type
                        // should find equivalences

                        Integer numberOfCurrentComplementSplitIntoEquivalents
                                = currentComplementsMap.get(complement) -
                                simplifiedParcelQuantityByType.get(complement);

                        if (parcelTypeEquivalences.get(complement) == null) {
                            // we reached 1; it's not enough; we cannot finish block
                            canFinishBlock = false;
                            break;
                        }

                        for (int i = 0; i < parcelTypeEquivalences.get(complement).length; i++) {

                            Integer[] equivalentSet = parcelTypeEquivalences.get(complement)[i];
                            boolean allSuite = true;
                            for (Integer equivalent : equivalentSet) {
                                // equivalent may be in complemet map
                                if (simplifiedParcelQuantityByType.get(equivalent) <
                                        ((currentComplementsMap.get(equivalent) == null) ?
                                                0 : currentComplementsMap.get(equivalent)) +
                                        numberOfCurrentComplementSplitIntoEquivalents ) {

                                    allSuite = false;
                                    break;

                                }
                            }

                            if (allSuite || i == parcelTypeComplements.get(complement).length - 1) {

                                // put (numberOfCurrentComplementSplitIntoEquivalents) copies of equivalents into complement map,
                                // delete (numberOfCurrentComplementSplitIntoEquivalents) copies of complement not available

                                // TODO !!!

                            }

                        }

                    }
                }

                // check


            }

        }


    }
}
