package ru.liga.truckapp.parcel.validation;

import java.util.List;

import static ru.liga.truckapp.parcel.validation.ParcelValidationConstants.PARCEL_FILLINGS_ALLOWED;

public class DefaultParcelValidator implements ParcelValidator {

    @Override
    public boolean isValid(List<String> parcel) {

        try {
            Integer typeCode = Integer.valueOf(parcel.get(0).substring(0, 1));

            if (PARCEL_FILLINGS_ALLOWED.get(typeCode) == null) return false;

            if (PARCEL_FILLINGS_ALLOWED.get(typeCode).size() != parcel.size()) return false;

            for (int i = 0; i < PARCEL_FILLINGS_ALLOWED.get(typeCode).size(); i++) {

                if (PARCEL_FILLINGS_ALLOWED.get(typeCode).get(i).length()
                        != parcel.get(i).replace(" ", "").length()) return false;

                for (int j = 0; j < PARCEL_FILLINGS_ALLOWED.get(typeCode).get(i).length(); j++) {
                    if (PARCEL_FILLINGS_ALLOWED.get(typeCode).get(i).charAt(j)
                            != parcel.get(i).charAt(j)) return false;
                }
            }
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }


    @Override
    public boolean fitsTruck(List<String> parcel, int truckHeight, int truckWidth) {
        return parcel.size() <= truckHeight
                && parcel.get(parcel.size() - 1).length() <= truckWidth;
    }

}
