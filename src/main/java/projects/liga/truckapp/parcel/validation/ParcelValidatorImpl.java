package projects.liga.truckapp.parcel.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static projects.liga.truckapp.parcel.validation.ParcelValidationConstants.PARCEL_FILLINGS_ALLOWED;

public class ParcelValidatorImpl implements ParcelValidator {

//    private final int PARCEL_TYPE_1 = 1;
//    private final int PARCEL_TYPE_2 = 2;
//    private final int PARCEL_TYPE_3 = 3;
//    private final int PARCEL_TYPE_4 = 4;
//    private final int PARCEL_TYPE_5 = 5;
//    private final int PARCEL_TYPE_6 = 6;
//    private final int PARCEL_TYPE_7 = 7;
//    private final int PARCEL_TYPE_8 = 8;
//    private final int PARCEL_TYPE_9 = 9;
//
//    private final Integer[] PARCEL_TYPES_ALLOWED = new Integer[]{PARCEL_TYPE_1,
//            PARCEL_TYPE_2, PARCEL_TYPE_3, PARCEL_TYPE_4, PARCEL_TYPE_5,
//            PARCEL_TYPE_6, PARCEL_TYPE_7, PARCEL_TYPE_8, PARCEL_TYPE_9};
//
//    private final Map<Integer, List<String>> parcelFillingsAllowed = new HashMap<>();
//
//    public ParcelValidatorImpl() {
//
//        List<String> filling1 = new ArrayList<>();
//        filling1.add("1");
//        parcelFillingsAllowed.put(PARCEL_TYPE_1, filling1);
//
//        List<String> filling2 = new ArrayList<>();
//        filling2.add("22");
//        parcelFillingsAllowed.put(PARCEL_TYPE_2, filling2);
//
//        List<String> filling3 = new ArrayList<>();
//        filling3.add("333");
//        parcelFillingsAllowed.put(PARCEL_TYPE_3, filling3);
//
//        List<String> filling4 = new ArrayList<>();
//        filling4.add("4444");
//        parcelFillingsAllowed.put(PARCEL_TYPE_4, filling4);
//
//        List<String> filling5 = new ArrayList<>();
//        filling5.add("55555");
//        parcelFillingsAllowed.put(PARCEL_TYPE_5, filling5);
//
//        List<String> filling6 = new ArrayList<>();
//        filling6.add("666");
//        filling6.add("666");
//        parcelFillingsAllowed.put(PARCEL_TYPE_6, filling6);
//
//        List<String> filling7 = new ArrayList<>();
//        filling7.add("777");
//        filling7.add("7777");
//        parcelFillingsAllowed.put(PARCEL_TYPE_7, filling7);
//
//        List<String> filling8 = new ArrayList<>();
//        filling8.add("8888");
//        filling8.add("8888");
//        parcelFillingsAllowed.put(PARCEL_TYPE_8, filling8);
//
//        List<String> filling9 = new ArrayList<>();
//        filling9.add("999");
//        filling9.add("999");
//        filling9.add("999");
//        parcelFillingsAllowed.put(PARCEL_TYPE_9, filling9);
//    }

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
