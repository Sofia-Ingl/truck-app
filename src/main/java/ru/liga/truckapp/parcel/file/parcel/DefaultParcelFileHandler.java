package ru.liga.truckapp.parcel.file.parcel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.truckapp.parcel.entities.Parcel;
import ru.liga.truckapp.parcel.validation.ParcelValidator;
import ru.liga.truckapp.parcel.exceptions.ValidationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


@Slf4j
@AllArgsConstructor
public class DefaultParcelFileHandler implements ParcelFileHandler {

    private final ParcelValidator validator;

    /**
     * Функция, читающая посылки из текстового файла и возвращающая их список
     *
     * @param filename путь к файлу
     * @param truckHeight высота грузовика
     * @param truckWidth ширина грузовика
     * @return список прочитанных посылок
     *
     * @throws ValidationException исключение бросается, если очередная посылка не прошла валидацию
     */
    @Override
    public List<Parcel> readAllParcels(String filename, int truckHeight, int truckWidth) {

        try (BufferedReader bufferedParcelReader = new BufferedReader(new FileReader(filename))) {

            List<Parcel> parcels = new ArrayList<>();

            List<String> currentParcel = new ArrayList<>();
            String line;
            while ((line = bufferedParcelReader.readLine()) != null) {

                if (!line.isEmpty()) {
                    currentParcel.add(line);
                } else {
                    if (!currentParcel.isEmpty()) {
                        validate(currentParcel, truckHeight, truckWidth);
                        Parcel parcelExtracted = extractParcel(currentParcel);
                        log.debug("Parcel extracted: {}", parcelExtracted);
                        parcels.add(parcelExtracted);
                        currentParcel.clear();
                    }
                }
            }

            if (!currentParcel.isEmpty()) {

                validate(currentParcel, truckHeight, truckWidth);
                Parcel currentType = extractParcel(currentParcel);
                parcels.add(currentType);
            }
            log.debug("Parcels read: {}", parcels);
            return parcels;


        } catch (IOException e) {
            log.error("IOException occurred while reading parcel file '{}': {}", filename, e.getMessage());
            throw new RuntimeException("IOException occurred while reading parcel file: " + e.getMessage());
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

    private void validate(List<String> currentParcel,
                          int truckHeight,
                          int truckWidth) {
        if (!validator.isValid(currentParcel)) {
            StringBuilder message = new StringBuilder("Invalid parcel.");
            message.append("\n");
            for (String line : currentParcel) {
                message.append(line).append("\n");
            }
            log.error("ValidationException: {}", message);
            throw new ValidationException(message.toString());
        }
        if (!validator.fitsTruck(currentParcel, truckHeight, truckWidth)) {
            StringBuilder message = new StringBuilder("Invalid parcel size.");
            message.append("\n");
            for (String line : currentParcel) {
                message.append(line).append("\n");
            }
            log.error("ValidationException: {}", message);
            throw new ValidationException(message.toString());
        }
    }

}
