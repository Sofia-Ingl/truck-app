package projects.liga.truckapp.parcel.packaging;

import projects.liga.truckapp.parcel.entities.Parcel;
import projects.liga.truckapp.parcel.entities.Slot;
import projects.liga.truckapp.parcel.entities.Truck;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class SteadyPackagingAlgorithmAbstract implements ParcelPackager {


    protected int tryLoadParcel(int parcelIndex,
                              List<Parcel> parcelsSortedAscending,
                              Truck truck) {

        Parcel suitableParcel = parcelsSortedAscending.get(parcelIndex);
        int size = suitableParcel.getTypeCode();

        Slot slot = findNextPlaceForParcel(truck, suitableParcel);
        if (slot.getWidth() == 0 || slot.getHeight() == 0) {
            throw new RuntimeException("Cannot load parcels steadily");
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
}
