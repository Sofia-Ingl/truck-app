package projects.liga.parcel.file_handling;

import lombok.AllArgsConstructor;
import projects.liga.parcel.entities.ParcelType;

import java.io.File;
import java.io.IOException;
import java.util.*;

@AllArgsConstructor
public class ParcelFileHandlerImpl implements ParcelFileHandler {

//    BlockType[] availableBlockTypes;

    public NavigableMap<ParcelType, Integer> getParcelQuantityByType(String filename) throws IOException {

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
                    ParcelType currentType = extractParcelType(currentParcel);
                    /*TODO: check available block types*/
                    insertBlockType(currentType, parcelQuantityByType);
                    currentParcel.clear();
                }
            }
        }

        if (!currentParcel.isEmpty()) {
            ParcelType currentType = extractParcelType(currentParcel);
            /*TODO: check available block types*/
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

}
