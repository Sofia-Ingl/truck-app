package projects.liga.parcel.file_handling;

import projects.liga.parcel.entities.ParcelType;
import projects.liga.parcel.file_handling.validation.ParcelValidator;
import projects.liga.parcel.file_handling.validation.ParcelValidatorImpl;
import projects.liga.parcel.exceptions.ValidationException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ParcelFileHandlerImpl implements ParcelFileHandler {

    private final ParcelValidator validator = new ParcelValidatorImpl();

    public NavigableMap<ParcelType, Integer> getParcelQuantityByType(String filename,
                                                                     int truckHeight,
                                                                     int truckWidth) throws IOException, ValidationException {

        File parcelFile = new File(filename);
        Scanner parcelScanner = new Scanner(parcelFile);

        NavigableMap<ParcelType, Integer> parcelQuantityByType = new TreeMap<>();

        List<String> currentParcel = new ArrayList<>();

        while (parcelScanner.hasNextLine()) {
            String line = parcelScanner.nextLine();
            if (!line.isEmpty()) {
                currentParcel.add(line);
            } else {

                if (!currentParcel.isEmpty()) {

                    validate(currentParcel, truckHeight, truckWidth);

                    ParcelType currentType = extractParcelType(currentParcel);
                    insertBlockType(currentType, parcelQuantityByType);
                    currentParcel.clear();
                }
            }
        }

        if (!currentParcel.isEmpty()) {

            validate(currentParcel, truckHeight, truckWidth);

            ParcelType currentType = extractParcelType(currentParcel);
            insertBlockType(currentType, parcelQuantityByType);
        }
        return parcelQuantityByType;

    }

    private ParcelType extractParcelType(List<String> currentParcel) {
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

        return new ParcelType(
                typeCode, maxWidth, height, isRectangle, filling
        );
    }

    private void insertBlockType(ParcelType parcelType, NavigableMap<ParcelType, Integer> parcelQuantityByType) {
        if (parcelQuantityByType.containsKey(parcelType)) {
            parcelQuantityByType.put(parcelType, parcelQuantityByType.get(parcelType) + 1);
        } else {
            parcelQuantityByType.put(parcelType, 1);
        }
    }

    private void validate(List<String> currentParcel,
                          int truckHeight,
                          int truckWidth) throws ValidationException {
        if (!validator.isValid(currentParcel) ) {
            StringBuilder message = new StringBuilder("Invalid parcel!");
            message.append("\n");
            for (String line : currentParcel) {
                message.append(line);
            }
            throw new ValidationException(message.toString());
        }
        if (!validator.fitsTruck(currentParcel, truckHeight, truckWidth) ) {
            StringBuilder message = new StringBuilder("Invalid parcel size!");
            message.append("\n");
            for (String line : currentParcel) {
                message.append(line);
            }
            throw new ValidationException(message.toString());
        }
    }

}
