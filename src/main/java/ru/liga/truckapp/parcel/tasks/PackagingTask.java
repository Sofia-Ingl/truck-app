package ru.liga.truckapp.parcel.tasks;

public interface PackagingTask {

    void execute(String inputFileName,
                 String outputFileName,
                 int truckWidth,
                 int truckHeight,
                 int truckQuantity);
}
