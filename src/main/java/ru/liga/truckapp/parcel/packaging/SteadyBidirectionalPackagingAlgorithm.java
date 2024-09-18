package ru.liga.truckapp.parcel.packaging;

import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.entities.Truck;

import java.util.*;

public class SteadyBidirectionalPackagingAlgorithm extends SteadyPackagingAlgorithmAbstract {


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

            int trucksLoadedSteadilyInIteration = 0;
            int[] differencesWithMaxParcelSize = new int[trucks.size()];

            int maxParcelIdx = parcelsSorted.size() - 1;
            Parcel iterationMaxParcel = parcelsSorted.get(maxParcelIdx);
            Arrays.fill(differencesWithMaxParcelSize, iterationMaxParcel.getTypeCode());

            for (int i = 0; i < trucks.size(); i++) {

                boolean isFilledOnCurrentIter = loadNextParcelIntoTruck(
                        parcelsSorted,
                        trucks.get(i),
                        i,
                        differencesWithMaxParcelSize
                );
                if (isFilledOnCurrentIter) {
                    trucksLoadedSteadilyInIteration++;
                }

                if (parcelsSorted.isEmpty()) break;

            }


            while (trucksLoadedSteadilyInIteration < trucks.size()
                    && !parcelsSorted.isEmpty()) {

                for (int i = trucks.size() - 1; i >= 0; i--) {

                    boolean isFilledOnCurrentIter = loadNextParcelIntoTruck(
                            parcelsSorted,
                            trucks.get(i),
                            i,
                            differencesWithMaxParcelSize
                    );
                    if (isFilledOnCurrentIter) {
                        trucksLoadedSteadilyInIteration++;
                    }

                    if (parcelsSorted.isEmpty()) break;

                }

            }


        }

        return trucks;
    }

    private boolean loadNextParcelIntoTruck(List<Parcel> parcelsSorted,
                                            Truck truck,
                                            int truckIndex,
                                            int[] differencesWithMaxParcelSize) {

        boolean truckIsLoadedSteadilyOnCurrentIter = false;

        // truck is already filled steadily on this iteration; continue with other trucks
        if (differencesWithMaxParcelSize[truckIndex] == 0) return truckIsLoadedSteadilyOnCurrentIter;

        int suitableParcelIndex = findIndexOfMaxParcelHavingLessOrEqualSize(
                differencesWithMaxParcelSize[truckIndex],
                parcelsSorted);

        if (suitableParcelIndex == -1) {
            // cannot fill truck better on current iteration
            differencesWithMaxParcelSize[truckIndex] = 0;
            truckIsLoadedSteadilyOnCurrentIter = true;
            return truckIsLoadedSteadilyOnCurrentIter;
        }


        int loadedParcelSize = tryLoadParcel(suitableParcelIndex,
                parcelsSorted,
                truck);

        differencesWithMaxParcelSize[truckIndex] -= loadedParcelSize;
        if (differencesWithMaxParcelSize[truckIndex] == 0) {
            truckIsLoadedSteadilyOnCurrentIter = true;
            return truckIsLoadedSteadilyOnCurrentIter;
        }
        return truckIsLoadedSteadilyOnCurrentIter;
    }





}
