package projects.liga.truckapp.parcel.validation;

import java.util.*;

public final class ParcelValidationConstants {

    public static final int PARCEL_TYPE_1 = 1;
    public static final int PARCEL_TYPE_2 = 2;
    public static final int PARCEL_TYPE_3 = 3;
    public static final int PARCEL_TYPE_4 = 4;
    public static final int PARCEL_TYPE_5 = 5;
    public static final int PARCEL_TYPE_6 = 6;
    public static final int PARCEL_TYPE_7 = 7;
    public static final int PARCEL_TYPE_8 = 8;
    public static final int PARCEL_TYPE_9 = 9;

    public static final Integer[] PARCEL_TYPES_ALLOWED = new Integer[]{PARCEL_TYPE_1,
            PARCEL_TYPE_2, PARCEL_TYPE_3, PARCEL_TYPE_4, PARCEL_TYPE_5,
            PARCEL_TYPE_6, PARCEL_TYPE_7, PARCEL_TYPE_8, PARCEL_TYPE_9};

    public static final Map<Integer, List<String>> PARCEL_FILLINGS_ALLOWED;

    static {
        Map<Integer, List<String>> parcelFillings = new HashMap<>();

        List<String> filling1 = new ArrayList<>();
        filling1.add("1");
        parcelFillings.put(PARCEL_TYPE_1, filling1);

        List<String> filling2 = new ArrayList<>();
        filling2.add("22");
        parcelFillings.put(PARCEL_TYPE_2, filling2);

        List<String> filling3 = new ArrayList<>();
        filling3.add("333");
        parcelFillings.put(PARCEL_TYPE_3, filling3);

        List<String> filling4 = new ArrayList<>();
        filling4.add("4444");
        parcelFillings.put(PARCEL_TYPE_4, filling4);

        List<String> filling5 = new ArrayList<>();
        filling5.add("55555");
        parcelFillings.put(PARCEL_TYPE_5, filling5);

        List<String> filling6 = new ArrayList<>();
        filling6.add("666");
        filling6.add("666");
        parcelFillings.put(PARCEL_TYPE_6, filling6);

        List<String> filling7 = new ArrayList<>();
        filling7.add("777");
        filling7.add("7777");
        parcelFillings.put(PARCEL_TYPE_7, filling7);

        List<String> filling8 = new ArrayList<>();
        filling8.add("8888");
        filling8.add("8888");
        parcelFillings.put(PARCEL_TYPE_8, filling8);

        List<String> filling9 = new ArrayList<>();
        filling9.add("999");
        filling9.add("999");
        filling9.add("999");
        parcelFillings.put(PARCEL_TYPE_9, filling9);

        PARCEL_FILLINGS_ALLOWED = Collections.unmodifiableMap(parcelFillings);
    }


}
