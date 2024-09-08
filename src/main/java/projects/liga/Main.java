package projects.liga;

import projects.liga.parcel.FirstAlgorithm;
import projects.liga.parcel.ParcelFileHandler;
import projects.liga.parcel.ParcelFileHandlerImpl;
import projects.liga.parcel.entities.ParcelType;

import java.io.IOException;
import java.util.NavigableMap;

public class Main {
    public static void main(String[] args) throws IOException {
        ParcelFileHandler parcelFileHandler = new ParcelFileHandlerImpl();
        NavigableMap<ParcelType, Integer> tm = parcelFileHandler.getParcelQuantityByType("C:\\Users\\Home\\IdeaProjects\\truck-app\\src\\main\\resources\\input.txt");

//        for (ParcelType b: tm.descendingKeySet()) {
//            System.out.println(b);
//            System.out.println(tm.get(b));
//        }

        FirstAlgorithm firstAlgorithm = new FirstAlgorithm(6, 6);
        firstAlgorithm.processPackaging(tm);
    }
}