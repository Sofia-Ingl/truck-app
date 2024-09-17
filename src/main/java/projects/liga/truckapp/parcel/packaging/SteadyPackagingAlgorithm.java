package projects.liga.truckapp.parcel.packaging;

import projects.liga.truckapp.parcel.entities.Parcel;
import projects.liga.truckapp.parcel.entities.Slot;
import projects.liga.truckapp.parcel.entities.Truck;

import java.util.*;

public class SteadyPackagingAlgorithm implements ParcelPackager {


    @Override
    public List<Truck> processPackaging(int truckWidth,
                                        int truckHeight,
                                        int truckQuantity,
                                        List<Parcel> parcels) {

        List<Parcel> parcelsSorted = new ArrayList<>(parcels);
        Collections.sort(parcelsSorted, Comparator.comparing(Parcel::getTypeCode));

        List<Truck> trucks = new ArrayList<>();

        int truckQuantityNeeded = Integer.min(truckQuantity, parcels.size());
        for (int i = 0; i < truckQuantityNeeded; i++) {
            trucks.add(new Truck(truckWidth, truckHeight));
        }

        while (!parcelsSorted.isEmpty()) {

            Parcel iterationMaxParcel = null;

            int trucksLoadedSteadilyInIteration = 0;
            int[] differencesWithMaxParcelSize = new int[trucks.size()];

            while (trucksLoadedSteadilyInIteration < trucks.size()) {

                for (int i = 0; i < trucks.size(); i++) {

                    if (parcelsSorted.isEmpty()) break;

                    Truck truck = trucks.get(i);

                    if (iterationMaxParcel == null) {
                        int maxParcelIdx = parcelsSorted.size() - 1;
                        Parcel currentMaxParcel = parcelsSorted.get(maxParcelIdx);
                        iterationMaxParcel = currentMaxParcel;
                        Arrays.fill(differencesWithMaxParcelSize, currentMaxParcel.getTypeCode());
                    }

                    // truck is already filled steadily on this iteration; continue with other trucks
                    if (differencesWithMaxParcelSize[i] == 0) continue;

                    int suitableParcelIndex = findMaxParcelOfLessOrEqualSizeIndex(
                            differencesWithMaxParcelSize[i],
                            parcelsSorted);

                    if (suitableParcelIndex == -1) {
                        // cannot fill truck better on current iteration
                        trucksLoadedSteadilyInIteration++;
                        differencesWithMaxParcelSize[i] = 0;
                        continue;
//                        trucksLoadedSteadilyInIteration = trucks.size();
//                        break;
                    }

                    Parcel suitableParcel = parcelsSorted.get(suitableParcelIndex);

                    tryLoadParcel(suitableParcelIndex,
                            parcelsSorted,
                            truck);

                    differencesWithMaxParcelSize[i] -= suitableParcel.getTypeCode();
                    if (differencesWithMaxParcelSize[i] == 0) {
                        trucksLoadedSteadilyInIteration++;
                    }
                }

                if (parcelsSorted.isEmpty()) break;

            }


        }

        /*
        while (!parcelsSorted.isEmpty()) {

            Parcel iterationMaxParcel = null;

            for (Truck truck : trucks) {

                int maxParcelIdx = parcelsSorted.size() - 1;
                Parcel currentMaxParcel = parcelsSorted.get(maxParcelIdx);

                tryLoadParcel(maxParcelIdx,
                        parcelsSorted,
                        truck);

                if (iterationMaxParcel == null) {
                    iterationMaxParcel = currentMaxParcel;
                }

                int difference = iterationMaxParcel.getTypeCode() - currentMaxParcel.getTypeCode();
                while (difference > 0) {

                    int suitableParcelIndex = findMaxParcelOfLessOrEqualSizeIndex(difference, parcelsSorted);
                    if (suitableParcelIndex == -1) {
                        break;
                    }

                    Parcel suitableParcel = parcelsSorted.get(suitableParcelIndex);
                    tryLoadParcel(suitableParcelIndex, parcelsSorted, truck);

                    difference -= suitableParcel.getTypeCode();

                }


            }

        }

         */

        return trucks;
    }

    private int findMaxParcelOfLessOrEqualSizeIndex(int difference,
                                                    List<Parcel> parcelsSortedAscending) {

        Parcel maxSuitableParcelInAbstract = Parcel.builder()
                .typeCode(difference)
                .build();

        int binarySearchResult = Collections.binarySearch(
                parcelsSortedAscending,
                maxSuitableParcelInAbstract,
                Comparator.comparing(Parcel::getTypeCode)
        );
        int upperBoundIndexNotIncluded;
        // read Collections.binarySearch return contract
        if (binarySearchResult < 0) {
            // key not present -> result = -(insertion point) - 1 -> insertion point = -result - 1
            upperBoundIndexNotIncluded = -binarySearchResult - 1;
        } else {
            // key not present -> return EXACTLY idx of key
            // so we need to add 1 to get upper bound not included
            upperBoundIndexNotIncluded = binarySearchResult + 1;
        }
        return upperBoundIndexNotIncluded - 1;
    }


    private Slot findNextPlaceForParcel(Truck truck,
                                        Parcel parcel) {

        // TODO: implement
        for (int y = 0; y < truck.getHeight() - parcel.getHeight(); y++) {

            int widthAvailable = truck.getWidth() - truck.getOccupiedCapacityByRow()[y];
            if (widthAvailable >= parcel.getMaxWidth()) {

                for (int x = 0; x < truck.getWidth(); x++) {
                    if (truck.getBack()[y][x] == ' '
                            && truck.checkParcelBottomWillNotHang(x, y)
                            && truck.canLoadParcel(x, y, parcel)) {
                        return new Slot(
                                x,
                                y,
                                truck.getWidth() - x,
                                truck.getHeight() - y
                        );
                    }
                }

            }
        }
        return new Slot(truck.getWidth(), truck.getHeight(), 0, 0);

    }


    private void tryLoadParcel(int parcelIndex,
                               List<Parcel> parcelsSortedAscending,
                               Truck truck) {

        Parcel suitableParcel = parcelsSortedAscending.get(parcelIndex);
        Slot slot = findNextPlaceForParcel(truck, suitableParcel);
        if (slot.getWidth() == 0 || slot.getHeight() == 0) {
            throw new RuntimeException("Cannot load parcels steadily");
        }

        truck.loadParcel(slot.getX(), slot.getY(), suitableParcel);
        parcelsSortedAscending.remove(parcelIndex);
    }

}
