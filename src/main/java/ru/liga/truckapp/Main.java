package ru.liga.truckapp;

import ru.liga.truckapp.config.file.ConfigFileHandler;
import ru.liga.truckapp.config.file.ConfigFileHandlerImpl;
import ru.liga.truckapp.config.params.ParamsHandler;
import ru.liga.truckapp.config.params.ParamsHandlerImpl;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        try {
            ConfigFileHandler configFileHandler = new ConfigFileHandlerImpl();
            Properties properties = configFileHandler.loadProperties("app.config");

            ParamsHandler paramsHandler = new ParamsHandlerImpl();
            List<Optional<Runnable>> tasks = paramsHandler.createRunnableTasksFromProperties(properties);

            for (Optional<Runnable> task : tasks) {
                task.ifPresent(Runnable::run);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}