package ru.liga.truckapp.parcel.packaging;

import lombok.extern.slf4j.Slf4j;
import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.entities.Slot;
import ru.liga.truckapp.parcel.entities.Truck;
import ru.liga.truckapp.parcel.exceptions.PackagingException;

import java.util.*;

@Slf4j
public class SteadyBidirectionalPackagingAlgorithm implements ParcelPackager {


    @Override
    public List<Truck> processPackaging(int truckWidth,
                                        int truckHeight,
                                        int truckQuantity,
                                        List<Parcel> parcels) {

        List<Parcel> parcelsSorted = new ArrayList<>(parcels);
        Collections.sort(parcelsSorted, Parcel.typeCodeComparator);

        List<Truck> trucks = new ArrayList<>();

        int truckQuantityNeeded = Integer.min(truckQuantity, parcels.size());
        for (int i = 0; i < truckQuantityNeeded; i++) {
            trucks.add(new Truck(truckWidth, truckHeight));
        }

        log.debug("Trucks created: {}", trucks.size());

        boolean directPhase = true;

        while (!parcelsSorted.isEmpty()) {

            int trucksLoadedSteadilyInIteration = 0;
            int[] differencesWithMaxParcelSize = new int[trucks.size()];

            int maxParcelIdx = parcelsSorted.size() - 1;
            Parcel iterationMaxParcel = parcelsSorted.get(maxParcelIdx);
            Arrays.fill(differencesWithMaxParcelSize, iterationMaxParcel.getTypeCode());


            if (directPhase) {
                log.debug("Processing direct initial packaging phase on iteration");
                trucksLoadedSteadilyInIteration += directPhase(trucks, parcelsSorted, differencesWithMaxParcelSize);
            } else {
                log.debug("Processing reversed initial packaging phase on iteration");
                trucksLoadedSteadilyInIteration += reversedPhase(trucks, parcelsSorted, differencesWithMaxParcelSize);
            }

//            for (int i = 0; i < trucks.size(); i++) {
//
//                boolean isFilledOnCurrentIter = loadNextParcelIntoTruck(
//                        parcelsSorted,
//                        trucks.get(i),
//                        i,
//                        differencesWithMaxParcelSize
//                );
//                if (isFilledOnCurrentIter) {
//                    trucksLoadedSteadilyInIteration++;
//                }
//
//                if (parcelsSorted.isEmpty()) break;
//
//            }


            while (trucksLoadedSteadilyInIteration < trucks.size()
                    && !parcelsSorted.isEmpty()) {


//                for (int i = trucks.size() - 1; i >= 0; i--) {
//
//                    boolean isFilledOnCurrentIter = loadNextParcelIntoTruck(
//                            parcelsSorted,
//                            trucks.get(i),
//                            i,
//                            differencesWithMaxParcelSize
//                    );
//                    if (isFilledOnCurrentIter) {
//                        trucksLoadedSteadilyInIteration++;
//                    }
//
//                    if (parcelsSorted.isEmpty()) break;
//
//                }
                if (!directPhase) {
                    log.debug("Processing reversed additional packaging phase on iteration");
                    trucksLoadedSteadilyInIteration += directPhase(trucks, parcelsSorted, differencesWithMaxParcelSize);
                } else {
                    log.debug("Processing direct additional packaging phase on iteration");
                    trucksLoadedSteadilyInIteration += reversedPhase(trucks, parcelsSorted, differencesWithMaxParcelSize);
                }

            }

            directPhase = !directPhase;
            log.debug("Phase direction changed. Now phase is {}", directPhase ? "direct" : "reversed");
        }

        return trucks;
    }

    private boolean loadNextParcelIntoTruck(List<Parcel> parcelsSorted,
                                            Truck truck,
                                            int truckIndex,
                                            int[] differencesWithMaxParcelSize) {

        log.debug("Working with truck: {}", Arrays.deepToString(truck.getBack()));
        boolean truckIsLoadedSteadilyOnCurrentIteration = false;

        // truck is already filled steadily on this iteration; continue with other trucks
        if (differencesWithMaxParcelSize[truckIndex] == 0) return truckIsLoadedSteadilyOnCurrentIteration;

        int suitableParcelIndex = findIndexOfMaxParcelHavingLessOrEqualSize(
                differencesWithMaxParcelSize[truckIndex],
                parcelsSorted);

        if (suitableParcelIndex == -1) {
            log.debug("Cannot fill truck better on current iteration: no suitable parcels available " +
                    "-> truck is loaded steadily on current iteration");
            differencesWithMaxParcelSize[truckIndex] = 0;
            truckIsLoadedSteadilyOnCurrentIteration = true;
            return truckIsLoadedSteadilyOnCurrentIteration;
        }


        int loadedParcelSize = tryLoadParcel(suitableParcelIndex,
                parcelsSorted,
                truck);

        log.debug("Working with truck: {}", Arrays.deepToString(truck.getBack()));

        differencesWithMaxParcelSize[truckIndex] -= loadedParcelSize;

        log.debug("Free space in a truck left to fill on current iteration: {}", differencesWithMaxParcelSize[truckIndex]);

        if (differencesWithMaxParcelSize[truckIndex] == 0) {
            truckIsLoadedSteadilyOnCurrentIteration = true;
            return truckIsLoadedSteadilyOnCurrentIteration;
        }
        return truckIsLoadedSteadilyOnCurrentIteration;
    }


    protected int tryLoadParcel(int parcelIndex,
                                List<Parcel> parcelsSortedAscending,
                                Truck truck) {

        Parcel suitableParcel = parcelsSortedAscending.get(parcelIndex);

        log.debug("Parcel to load: {}", suitableParcel);

        int size = suitableParcel.getTypeCode();

        Slot slot = findNextPlaceForParcel(truck, suitableParcel);
        if (slot.getWidth() == 0 || slot.getHeight() == 0) {
            log.error("Cannot load parcels steadily");
            throw new PackagingException("Cannot load parcels steadily; truck current state:\n"
                    + truck + "\n" + "trying to load parcel:\n" + suitableParcel);
        }

        truck.loadParcel(slot.getX(), slot.getY(), suitableParcel);
        parcelsSortedAscending.remove(parcelIndex);
        return size;
    }


    protected int findIndexOfMaxParcelHavingLessOrEqualSize(int size,
                                                            List<Parcel> parcelsSortedAscending) {

        Parcel maxSuitableParcelInAbstract = Parcel.builder()
                .typeCode(size)
                .build();

        int binarySearchResult = Collections.binarySearch(
                parcelsSortedAscending,
                maxSuitableParcelInAbstract,
                Parcel.typeCodeComparator
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

        for (int y = 0; y <= truck.getHeight() - parcel.getHeight(); y++) {

            int widthAvailable = truck.getWidth() - truck.getOccupiedCapacityByRow()[y];
            if (widthAvailable >= parcel.getMaxWidth()) {

                for (int x = 0; x <= truck.getWidth() - parcel.getMaxWidth(); x++) {
                    if (truck.getBack()[y][x] == ' '
                            && truck.checkParcelBottomWillNotHang(x, y, parcel.getMaxWidth())
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


    private int directPhase(List<Truck> trucks,
                             List<Parcel> parcelsSorted,
                             int[] differencesWithMaxParcelSize) {

        int trucksLoadedSteadilyInIteration = 0;
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
        return trucksLoadedSteadilyInIteration;
    }



    private int reversedPhase(List<Truck> trucks,
                            List<Parcel> parcelsSorted,
                            int[] differencesWithMaxParcelSize) {

        int trucksLoadedSteadilyInIteration = 0;
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
        return trucksLoadedSteadilyInIteration;
    }


}
