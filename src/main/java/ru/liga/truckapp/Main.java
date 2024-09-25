package ru.liga.truckapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.DefaultParser;

import ru.liga.truckapp.io.CommandLineArgumentsParser;
import ru.liga.truckapp.io.DefaultUserIOProcessor;
import ru.liga.truckapp.io.TruckCommandLineArgumentsParser;
import ru.liga.truckapp.io.UserIOProcessor;
import ru.liga.truckapp.io.creators.CountingTaskTemplateCreator;
import ru.liga.truckapp.io.creators.DefaultCountingTaskTemplateCreator;
import ru.liga.truckapp.io.creators.DefaultPackagingTaskTemplateCreator;
import ru.liga.truckapp.io.creators.PackagingTaskTemplateCreator;
import ru.liga.truckapp.io.enums.CountingAlgorithmType;
import ru.liga.truckapp.io.enums.PackagingAlgorithmType;
import ru.liga.truckapp.parcel.tasks.CountingTaskTemplate;
import ru.liga.truckapp.parcel.tasks.PackagingTaskTemplate;

import java.util.*;

@Slf4j
public class Main {

    /**
     * Точка входа в программу
     *
     * @param args аргументы командной сроки
     */
    public static void main(String[] args) {

        try {

            log.info("Starting application...");
            CommandLineArgumentsParser cliArgsParser = new TruckCommandLineArgumentsParser(new DefaultParser());
            Properties properties = cliArgsParser.parse(args);

            log.debug("Properties loaded: {}", properties);

            UserIOProcessor userIOProcessor = new DefaultUserIOProcessor(
                    System.in,
                    System.out
            );

            log.debug("User IO processor created");

            Map<PackagingAlgorithmType, PackagingTaskTemplate> packagers = new HashMap<>();
            PackagingTaskTemplateCreator packagersCreator = new DefaultPackagingTaskTemplateCreator();
            for (PackagingAlgorithmType type : PackagingAlgorithmType.values()) {
                PackagingTaskTemplate packagingTaskTemplate = packagersCreator.create(type, System.out);
                packagers.put(type, packagingTaskTemplate);
            }

            Map<CountingAlgorithmType, CountingTaskTemplate> counters = new HashMap<>();
            CountingTaskTemplateCreator countersCreator = new DefaultCountingTaskTemplateCreator();
            for (CountingAlgorithmType type : CountingAlgorithmType.values()) {
                CountingTaskTemplate countingTaskTemplate = countersCreator.create(type, System.out);
                counters.put(type, countingTaskTemplate);
            }

            userIOProcessor.processUserIO(
                    counters,
                    packagers,
                    properties
            );

            log.info("Stopping application...");

        } catch (Exception e) {
            log.error("Error occurred in application: {}", e.getMessage());
        }

    }

}