package ru.liga.truckapp.parcel.file;

import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.validation.ParcelValidator;
import ru.liga.truckapp.parcel.validation.ParcelValidatorImpl;
import ru.liga.truckapp.parcel.exceptions.ValidationException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ParcelFileHandlerImpl implements ParcelFileHandler {

    private final ParcelValidator validator = new ParcelValidatorImpl();

    public NavigableMap<Parcel, Integer> getParcelQuantityByType(String filename,
                                                                 int truckHeight,
                                                                 int truckWidth) throws IOException {

        File parcelFile = new File(filename);
        Scanner parcelScanner = new Scanner(parcelFile);

        NavigableMap<Parcel, Integer> parcelQuantityByType = new TreeMap<>();

        List<String> currentParcel = new ArrayList<>();

        while (parcelScanner.hasNextLine()) {
            String line = parcelScanner.nextLine();
            if (!line.isEmpty()) {
                currentParcel.add(line);
            } else {

                if (!currentParcel.isEmpty()) {

                    validate(currentParcel, truckHeight, truckWidth);

                    Parcel currentType = extractParcel(currentParcel);
                    insertBlockType(currentType, parcelQuantityByType);
                    currentParcel.clear();
                }
            }
        }

        if (!currentParcel.isEmpty()) {

            validate(currentParcel, truckHeight, truckWidth);

            Parcel currentType = extractParcel(currentParcel);
            insertBlockType(currentType, parcelQuantityByType);
        }
        return parcelQuantityByType;

    }

    @Override
    public List<Parcel> readAllParcels(String filename, int truckHeight, int truckWidth) {

        try {
            File parcelFile = new File(filename);
            Scanner parcelScanner = new Scanner(parcelFile);

            List<Parcel> parcels = new ArrayList<>();

            List<String> currentParcel = new ArrayList<>();

            while (parcelScanner.hasNextLine()) {
                String line = parcelScanner.nextLine();
                if (!line.isEmpty()) {
                    currentParcel.add(line);
                } else {
                    if (!currentParcel.isEmpty()) {
                        validate(currentParcel, truckHeight, truckWidth);
                        Parcel currentType = extractParcel(currentParcel);
                        parcels.add(currentType);
                        currentParcel.clear();
                    }
                }
            }

            if (!currentParcel.isEmpty()) {

                validate(currentParcel, truckHeight, truckWidth);
                Parcel currentType = extractParcel(currentParcel);
                parcels.add(currentType);
            }

            return parcels;


        } catch (IOException e) {
            throw new IllegalStateException("IOException occurred while reading parcel file: " + e.getMessage());
        }
    }


    private Parcel extractParcel(List<String> currentParcel) {
        Integer height = currentParcel.size();
        Integer maxWidth = currentParcel.get(height - 1).length();
        Integer upperRowWidth = currentParcel.get(0).length();
        Boolean isRectangle = maxWidth.equals(upperRowWidth);

        char[][] filling = new char[height][maxWidth];
        for (int i = 0; i < height; i++) {
            String parcelLine = currentParcel.get(i);
            while (parcelLine.length() < maxWidth) {
                parcelLine = parcelLine + " ";
            }
            for (int j = 0; j < maxWidth; j++) {
                filling[i][j] = parcelLine.charAt(j);
            }
        }

        Integer typeCode = Integer.valueOf(currentParcel.get(0).substring(0, 1));

        return new Parcel(
                typeCode, maxWidth, height, isRectangle, filling
        );
    }

    private void insertBlockType(Parcel parcel, NavigableMap<Parcel, Integer> parcelQuantityByType) {
        if (parcelQuantityByType.containsKey(parcel)) {
            parcelQuantityByType.put(parcel, parcelQuantityByType.get(parcel) + 1);
        } else {
            parcelQuantityByType.put(parcel, 1);
        }
    }

    private void validate(List<String> currentParcel,
                          int truckHeight,
                          int truckWidth) throws ValidationException {
        if (!validator.isValid(currentParcel) ) {
            StringBuilder message = new StringBuilder("Invalid parcel!");
            message.append("\n");
            for (String line : currentParcel) {
                message.append(line).append("\n");
            }
            throw new ValidationException(message.toString());
        }
        if (!validator.fitsTruck(currentParcel, truckHeight, truckWidth) ) {
            StringBuilder message = new StringBuilder("Invalid parcel size!");
            message.append("\n");
            for (String line : currentParcel) {
                message.append(line).append("\n");
            }
            throw new ValidationException(message.toString());
        }
    }

}
