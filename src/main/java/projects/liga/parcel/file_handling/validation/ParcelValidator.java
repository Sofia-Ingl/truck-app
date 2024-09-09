package projects.liga.parcel.file_handling.validation;

import java.util.List;

public interface ParcelValidator {

    boolean isValid(List<String> parcel);
    boolean fitsTruck(List<String> parcel, int truckHeight, int truckWidth);

}
