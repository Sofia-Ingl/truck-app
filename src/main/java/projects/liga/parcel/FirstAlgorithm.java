package projects.liga.parcel;


import lombok.AllArgsConstructor;
import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.entities.Slot;
import projects.liga.parcel.entities.Truck;

import java.util.*;

// common
@AllArgsConstructor
public class FirstAlgorithm {

    private Integer truckWidth;
    private Integer truckHeight;

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

                Slot freeSlot = truck.getNextPlaceForAnyParcel(startingXPointForNewSlot, startingYPointForNewSlot);

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
//            int gaps = 0;
//            int basicRowLength = 0;
//
//            char[] basicRow = suitableParcel.getFilling()[suitableParcel.getHeight() - 1];
//            for (int i = 0; i < suitableParcel.getMaxWidth(); i++) {
//                boolean gapInBasicRow = basicRow[i] == ' ';
//                boolean gapInUnderlyingRow = underlyingRow[i] == ' ';
//                basicRowLength += (gapInBasicRow) ? 0 : 1;
//                gaps += (gapInUnderlyingRow & !gapInBasicRow) ? 1 : 0;
//            }

            if (basicRowLength  > gaps*2) {
                return suitableParcel;
            }
        }

        return null;

    }


}
