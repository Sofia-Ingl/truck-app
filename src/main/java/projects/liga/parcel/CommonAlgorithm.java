package projects.liga.parcel;


import lombok.AllArgsConstructor;
import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.entities.Slot;
import projects.liga.parcel.entities.Truck;

import java.util.*;

// common
@AllArgsConstructor
public class CommonAlgorithm implements ParcelPackager {

    private Integer truckWidth;
    private Integer truckHeight;

    @Override
    public void processPackaging(NavigableMap<ParcelType, Integer> parcelQuantityByType) {

        List<Truck> trucks = new ArrayList<>();

        while (!parcelQuantityByType.isEmpty()) {

            Truck truck = new Truck(truckWidth, truckHeight);

            int x = 0;
            int y = 0;

            int currentWidthToFill = truckWidth;
            int currentHeightToFill = truckHeight;

            while (x < truckWidth || y < truckHeight) {

                ParcelType suitableParcel = findSuitableParcel(currentWidthToFill,
                        currentHeightToFill,
                        truck.getRowSlice(x, y - 1),
                        parcelQuantityByType.navigableKeySet());

                int startingXPointForNewSlot;
                int startingYPointForNewSlot;

                if (suitableParcel == null) {

                    if (x + currentWidthToFill == truckWidth && y + currentHeightToFill == truckHeight) {
                        break;
                    }

                    startingXPointForNewSlot = x + currentWidthToFill;
                    startingYPointForNewSlot = y;

                } else {

                    truck.loadParcel(x, y, suitableParcel);

                    int loadedParcelTypeNewQuantity = parcelQuantityByType.get(suitableParcel) - 1;
                    if (loadedParcelTypeNewQuantity == 0) {
                        parcelQuantityByType.remove(suitableParcel);
                    } else {
                        parcelQuantityByType.put(suitableParcel, parcelQuantityByType.get(suitableParcel) - 1);
                    }

                    if (parcelQuantityByType.isEmpty()) {
                        break;
                    }

                    startingXPointForNewSlot = x + suitableParcel.getMaxWidth();
                    startingYPointForNewSlot = y;

                }

                Slot freeSlot = getNextPlaceForAnyParcel(truck, startingXPointForNewSlot, startingYPointForNewSlot);

                if (freeSlot == null) {
                    x = truckWidth;
                    y = truckHeight;
                    currentHeightToFill = 0;
                    currentWidthToFill = 0;
                } else {
                    x = freeSlot.getX();
                    y = freeSlot.getY();
                    currentHeightToFill = freeSlot.getHeight();
                    currentWidthToFill = freeSlot.getWidth();
                }

            }

            trucks.add(truck);
        }


        for (Truck truck : trucks) {
            truck.print();
        }

    }

    private ParcelType findSuitableParcel(int widthToFill,
                                          int heightToFill,
                                          char[] underlyingRow,
                                          NavigableSet<ParcelType> parcelTypesAscending) {

        ParcelType maxSuitableParcelInAbstract = ParcelType.builder()
                .maxWidth(widthToFill)
                .height(heightToFill)
                .typeCode(Integer.MAX_VALUE)
                .build();
        NavigableSet<ParcelType> suitableParcels = parcelTypesAscending.headSet(maxSuitableParcelInAbstract, false);

        int[] gapsNumberCumulative = new int[underlyingRow.length];
        gapsNumberCumulative[0] = (underlyingRow[0] == ' ') ? 1 : 0;
        for (int i = 1; i < underlyingRow.length; i++) {
            gapsNumberCumulative[i] = (underlyingRow[i] == ' ') ? gapsNumberCumulative[i - 1] + 1 : gapsNumberCumulative[i - 1];
        }

        for (ParcelType suitableParcel : suitableParcels.descendingSet()) {

            int basicRowLength = suitableParcel.getMaxWidth();
            int gaps = gapsNumberCumulative[basicRowLength - 1];

            if (basicRowLength > gaps * 2 && suitableParcel.getHeight() <= heightToFill) {
                return suitableParcel;
            }
        }

        return null;

    }



    public Slot getNextPlaceForAnyParcel(Truck truck,
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

        if (maxWidth <= 0 || maxHeight <= 0) return null;

        return new Slot(x, y, maxWidth, maxHeight);

    }


}
