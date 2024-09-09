package projects.liga.parcel;

import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.entities.Truck;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

public class SingleParcelAlgorithm implements ParcelPackager {

    @Override
    public List<Truck> processPackaging(
            int truckWidth,
            int truckHeight,
            NavigableMap<ParcelType, Integer> parcelQuantityByType) {

        List<Truck> trucks = new ArrayList<>();

        while (!parcelQuantityByType.isEmpty()) {

            Truck truck = new Truck(truckWidth, truckHeight);
            ParcelType suitableParcel = parcelQuantityByType.firstEntry().getKey();
            truck.loadParcel(0, 0, suitableParcel);

            int loadedParcelTypeNewQuantity = parcelQuantityByType.get(suitableParcel) - 1;
            if (loadedParcelTypeNewQuantity == 0) {
                parcelQuantityByType.remove(suitableParcel);
            } else {
                parcelQuantityByType.put(suitableParcel, parcelQuantityByType.get(suitableParcel) - 1);
            }

            trucks.add(truck);

        }

        return trucks;
    }

}
