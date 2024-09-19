package ru.liga.truckapp.parcel.packaging;


import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.entities.Slot;
import ru.liga.truckapp.parcel.entities.Truck;

import java.util.*;

public class OptimizedPackagingAlgorithm implements ParcelPackager {


    @Override
    public List<Truck> processPackaging(int truckWidth,
                                        int truckHeight,
                                        int truckQuantity,
                                        List<Parcel> parcels) {

        List<Parcel> parcelsSorted = new ArrayList<>(parcels);
        Collections.sort(parcelsSorted, Parcel.widthComparator);

        List<Truck> trucks = new ArrayList<>();

        while (!parcelsSorted.isEmpty()) {

            if (trucks.size() >= truckQuantity) {
                throw new RuntimeException("Cannot pack all the parcels: not enough trucks");
            }

            Truck truck = new Truck(truckWidth, truckHeight);

            int x = 0;
            int y = 0;

            int currentWidthToFill = truckWidth;
            int currentHeightToFill = truckHeight;

            while (x < truckWidth || y < truckHeight) {

                int suitableParcelIndex = findSuitableParcelIndex(currentWidthToFill,
                        currentHeightToFill,
                        truck.getRowSlice(x, y - 1),
                        parcelsSorted);

                int startingXPointForNewSlot;
                int startingYPointForNewSlot;

                if (suitableParcelIndex == -1) {

                    if (x + currentWidthToFill == truckWidth && y + currentHeightToFill == truckHeight) {
                        break;
                    }

                    startingXPointForNewSlot = x + currentWidthToFill;
                    startingYPointForNewSlot = y;

                } else {

                    Parcel suitableParcel = parcelsSorted.get(suitableParcelIndex);
                    truck.loadParcel(x, y, suitableParcel);

                    parcelsSorted.remove(suitableParcelIndex);

                    if (parcelsSorted.isEmpty()) {
                        break;
                    }

                    startingXPointForNewSlot = x + suitableParcel.getMaxWidth();
                    startingYPointForNewSlot = y;

                }

                Slot freeSlot = findNextPlaceForAnyParcel(truck,
                        startingXPointForNewSlot,
                        startingYPointForNewSlot);

                x = freeSlot.getX();
                y = freeSlot.getY();
                currentHeightToFill = freeSlot.getHeight();
                currentWidthToFill = freeSlot.getWidth();

            }

            trucks.add(truck);
        }


        return trucks;
    }


    private int findSuitableParcelIndex(int widthToFill,
                                        int heightToFill,
                                        char[] underlyingRow,
                                        List<Parcel> parcelsAscending) {

        Parcel maxSuitableParcelInAbstract = Parcel.builder()
                .maxWidth(widthToFill)
                .height(heightToFill)
                .typeCode(Integer.MAX_VALUE)
                .build();

        int upperBoundNotIncludedIndex = Collections.binarySearch(parcelsAscending,
                maxSuitableParcelInAbstract,
                Parcel.widthComparator);

        if (upperBoundNotIncludedIndex < 0) {
            // read Collections.binarySearch return contract
            upperBoundNotIncludedIndex = -upperBoundNotIncludedIndex - 1;
        }

        int[] gapsQuantityCumulative = new int[underlyingRow.length];
        gapsQuantityCumulative[0] = (underlyingRow[0] == ' ') ? 1 : 0;
        for (int i = 1; i < underlyingRow.length; i++) {
            gapsQuantityCumulative[i] = (underlyingRow[i] == ' ') ? gapsQuantityCumulative[i - 1] + 1 : gapsQuantityCumulative[i - 1];
        }

        for (int i = upperBoundNotIncludedIndex - 1; i >= 0; i--) {

            Parcel suitableParcel = parcelsAscending.get(i);
            int basicRowLength = suitableParcel.getMaxWidth();
            int gaps = gapsQuantityCumulative[basicRowLength - 1];

            if (basicRowLength > gaps * 2 && suitableParcel.getHeight() <= heightToFill) {
                return i;
            }
        }

        return -1;

    }


    public Slot findNextPlaceForAnyParcel(Truck truck,
                                          int startingXPoint,
                                          int startingYPoint) {

        int x = startingXPoint;
        int y = startingYPoint;
        int maxHeight;
        int maxWidth;

        int currentLoadedCapacity = truck.getOccupiedCapacityByRow()[startingYPoint];

        // find new coordinates
        if (startingXPoint >= truck.getWidth()) {

            for (int i = y + 1; i < truck.getHeight(); i++) {
                if (truck.getOccupiedCapacityByRow()[i] != truck.getWidth()
                        && truck.getOccupiedCapacityByRow()[i] < currentLoadedCapacity) {
                    y = i;
                    x = truck.getOccupiedCapacityByRow()[i];
                    break;
                }
            }
        }

        int occupiedCapacityOfCurrentRow = truck.getOccupiedCapacityByRow()[y];
        maxHeight = 1;

        for (int i = y + 1; i < truck.getHeight(); i++) {
            if (truck.getOccupiedCapacityByRow()[i] != occupiedCapacityOfCurrentRow) {
                break;
            }
            maxHeight += 1;
        }

        maxWidth = truck.getWidth() - x;

        if (maxWidth <= 0 || maxHeight <= 0)
            return new Slot(truck.getWidth(), truck.getHeight(), 0, 0);

        return new Slot(x, y, maxWidth, maxHeight);

    }




}
