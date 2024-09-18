package projects.liga.truckapp.parcel.packaging;

import projects.liga.truckapp.parcel.entities.Parcel;
import projects.liga.truckapp.parcel.entities.Slot;
import projects.liga.truckapp.parcel.entities.Truck;

import java.util.*;

public class SteadyPackagingAlgorithm extends SteadyPackagingAlgorithmAbstract {


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

                    int suitableParcelIndex = findIndexOfMaxParcelHavingLessOrEqualSize(
                            differencesWithMaxParcelSize[i],
                            parcelsSorted);

                    if (suitableParcelIndex == -1) {
                        // cannot fill truck better on current iteration
                        trucksLoadedSteadilyInIteration++;
                        differencesWithMaxParcelSize[i] = 0;
                        continue;
                    }


                    int loadedParcelSize = tryLoadParcel(suitableParcelIndex,
                            parcelsSorted,
                            truck);

                    differencesWithMaxParcelSize[i] -= loadedParcelSize;
                    if (differencesWithMaxParcelSize[i] == 0) {
                        trucksLoadedSteadilyInIteration++;
                    }
                }

                if (parcelsSorted.isEmpty()) break;

            }


        }

        return trucks;
    }



}
