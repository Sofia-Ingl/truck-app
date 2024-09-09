package projects.liga;

import projects.liga.config.AlgorithmType;
import projects.liga.config.Config;
import projects.liga.config.ConfigFileHandler;
import projects.liga.config.ConfigFileHandlerImpl;
import projects.liga.parcel.CommonAlgorithm;
import projects.liga.parcel.ParcelPackager;
import projects.liga.parcel.SingleParcelAlgorithm;
import projects.liga.parcel.exceptions.ValidationException;
import projects.liga.parcel.file_handling.ParcelFileHandler;
import projects.liga.parcel.file_handling.ParcelFileHandlerImpl;
import projects.liga.parcel.entities.ParcelType;

import java.io.IOException;
import java.util.NavigableMap;

public class Main {
    public static void main(String[] args) {

        try {
            ConfigFileHandler configFileHandler = new ConfigFileHandlerImpl();
            Config config = configFileHandler.loadConfig();

            ParcelFileHandler parcelFileHandler = new ParcelFileHandlerImpl();
            NavigableMap<ParcelType, Integer> parcelTypesMap = parcelFileHandler.getParcelQuantityByType(
                    config.getParcelFileName(),
                    config.getTruckHeight(),
                    config.getTruckWidth());

            ParcelPackager parcelPackager;
            if (config.getAlgorithmType() == AlgorithmType.COMMON) {
                parcelPackager = new CommonAlgorithm();
            } else {
                parcelPackager = new SingleParcelAlgorithm();
            }

            parcelPackager.printTrucks(parcelPackager.processPackaging(config.getTruckWidth(),
                    config.getTruckHeight(),
                    parcelTypesMap));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}