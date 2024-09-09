package projects.liga;

import projects.liga.parcel.CommonAlgorithm;
import projects.liga.parcel.ParcelPackager;
import projects.liga.parcel.SingleParcelAlgorithm;
import projects.liga.parcel.file_handling.ParcelFileHandler;
import projects.liga.parcel.file_handling.ParcelFileHandlerImpl;
import projects.liga.parcel.entities.ParcelType;

import java.io.IOException;
import java.util.NavigableMap;

public class Main {
    public static void main(String[] args) throws IOException {

        ParcelFileHandler parcelFileHandler = new ParcelFileHandlerImpl();
        NavigableMap<ParcelType, Integer> tm = parcelFileHandler.getParcelQuantityByType("C:\\Users\\Home\\IdeaProjects\\truck-app\\src\\main\\resources\\input.txt");

        ParcelPackager parcelPackager = new SingleParcelAlgorithm();
        parcelPackager.printTrucks(parcelPackager.processPackaging(6,6,tm));
    }
}