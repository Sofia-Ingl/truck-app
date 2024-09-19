package ru.liga.truckapp.config.creators;

public interface CountingTaskCreator {

    Runnable createCountingTask(
            String inputFileName
    );
}
