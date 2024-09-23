package ru.liga.truckapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.DefaultParser;

import ru.liga.truckapp.io.CommandLineArgumentsParser;
import ru.liga.truckapp.io.DefaultUserIOProcessor;
import ru.liga.truckapp.io.TruckCommandLineArgumentsParser;
import ru.liga.truckapp.io.UserIOProcessor;
import ru.liga.truckapp.io.creators.CountingTaskCreator;
import ru.liga.truckapp.io.creators.DefaultCountingTaskCreator;
import ru.liga.truckapp.io.creators.DefaultPackagingTaskCreator;
import ru.liga.truckapp.io.creators.PackagingTaskCreator;
import ru.liga.truckapp.io.enums.CountingAlgorithmType;
import ru.liga.truckapp.io.enums.PackagingAlgorithmType;
import ru.liga.truckapp.parcel.tasks.CountingTaskTemplate;
import ru.liga.truckapp.parcel.tasks.PackagingTaskTemplate;

import java.util.*;

@Slf4j
public class Main {

    private static final String DEFAULT_CONFIG_FILE_NAME = "src/main/resources/app.config";

    public static void main(String[] args) {

        try {

            log.info("Starting application...");
            CommandLineArgumentsParser cliArgsParser = new TruckCommandLineArgumentsParser(new DefaultParser());
            Properties properties = cliArgsParser.parse(args);
            UserIOProcessor userIOProcessor = new DefaultUserIOProcessor(
                    System.in,
                    System.out
            );

            Map<PackagingAlgorithmType, PackagingTaskTemplate> packagers = new HashMap<>();
            PackagingTaskCreator packagingTaskCreator = new DefaultPackagingTaskCreator();
            for (PackagingAlgorithmType packagingAlgorithmType : PackagingAlgorithmType.values()) {
                PackagingTaskTemplate packagingTask = packagingTaskCreator.createPackagingTask(packagingAlgorithmType);
                packagers.put(packagingAlgorithmType, packagingTask);
            }

            Map<CountingAlgorithmType, CountingTaskTemplate> counters = new HashMap<>();
            CountingTaskCreator countingTaskCreator = new DefaultCountingTaskCreator();
            for (CountingAlgorithmType countingAlgorithmType : CountingAlgorithmType.values()) {
                CountingTaskTemplate countingTask = countingTaskCreator.createCountingTask(countingAlgorithmType);
                counters.put(countingAlgorithmType, countingTask);
            }

            userIOProcessor.processUserIO(
                    counters,
                    packagers,
                    properties
            );

//            Properties properties = readConfiguration(args);
//
//            log.debug("Properties loaded");
//
//            CountingTaskCreator countingTaskCreator = new DefaultCountingTaskCreator();
//            PackagingTaskCreator packagingTaskCreator = new DefaultPackagingTaskCreator();
//
//            List<Optional<Runnable>> tasks = createTasksFromGivenParameters(
//                    properties,
//                    countingTaskCreator,
//                    packagingTaskCreator
//            );
//            log.debug("Runnable tasks created");
//
//            for (Optional<Runnable> task : tasks) {
//                task.ifPresent(Runnable::run);
//            }
//
//            log.info("Starting console phase...");
//            consolePhase(properties, countingTaskCreator, packagingTaskCreator);

            log.info("Stopping application...");

        } catch (Exception e) {
            log.error("Error occurred in application: {}", e.getMessage());
//            System.out.println(e.getMessage());
        }

    }

//    private static Properties readConfiguration(String[] args) {
//
//        String configFileName = DEFAULT_CONFIG_FILE_NAME;
//        if (args.length > 0) {
//            configFileName = args[0];
//        }
//        log.debug("Config file: {}", configFileName);
//        ConfigFileHandler configFileHandler = new DefaultConfigFileHandler();
//        return configFileHandler.loadProperties(configFileName);
//    }
//
//    private static List<Optional<Runnable>> createTasksFromGivenParameters(Properties properties,
//                                                                           CountingTaskCreator countingTaskCreator,
//                                                                           PackagingTaskCreator packagingTaskCreator) {
//
//
//        RunnableListCreator runnableListCreator = new DefaultRunnableListCreator(
//                countingTaskCreator,
//                packagingTaskCreator
//        );
//        return runnableListCreator.createRunnableTasksFromProperties(properties);
//    }


//    private static void consolePhase(Properties properties,
//                                     CountingTaskCreator countingTaskCreator,
//                                     PackagingTaskCreator packagingTaskCreator) throws IOException {
//
//        try (Scanner scanner = new Scanner(System.in)) {
//            System.out.println("All tasks from config file done");
//
//            boolean stop = askForStop(scanner);
//            while (!stop) {
//
//                String taskType = chooseTaskType(scanner);
//                switch (taskType) {
//                    case "1":
//                        processCountingTask(scanner, countingTaskCreator);
//                        break;
//                    case "2":
//                        processPackagingTask(scanner, properties, packagingTaskCreator);
//                        break;
//                    default:
//                        System.out.println("Ok, you don't want to use me. Bye");
//                        return;
//                }
//
//                stop = askForStop(scanner);
//            }
//        }
//
//    }
//
//
//    private static boolean askForStop(Scanner scanner) {
//        System.out.println("Do you want to continue the program? (yes/...)");
//        String answer = scanner.nextLine();
//        return !answer.trim().equalsIgnoreCase("yes");
//    }
//
//    private static String chooseTaskType(Scanner scanner) {
//        System.out.println("Choose one of the following options:");
//        System.out.println("1. count");
//        System.out.println("2. pack");
//        System.out.println("Write its number here:");
//        return scanner.nextLine().trim();
//    }
//
//    private static void processCountingTask(Scanner scanner, CountingTaskCreator countingTaskCreator) throws IOException {
//        System.out.println("You have chosen counting task");
//        System.out.println("Input your json file name here");
//        String filename = scanner.nextLine().trim();
//        Runnable task = countingTaskCreator.createCountingTask(filename);
//        task.run();
//        System.out.println("Counting task successfully finished");
//    }
//
//    private static void processPackagingTask(Scanner scanner,
//                                             Properties properties,
//                                             PackagingTaskCreator packagingTaskCreator) throws IOException {
//        System.out.println("You have chosen parcel packaging task");
//        System.out.println("Input your input & output file names here separated by ,");
//        String[] filenames = scanner.nextLine().trim().split(",");
//        String in = filenames[0].trim();
//        String out = filenames[1].trim();
//        System.out.println("Available algorithm types:");
//        System.out.println("1. optimized");
//        System.out.println("2. steady_bidirectional");
//        System.out.println("Choose your number here:");
//        String choice = scanner.nextLine().trim();
//        PackagingAlgorithmType packagingAlgorithmType = PackagingAlgorithmType.OPTIMIZED;
//        switch (choice) {
//            case "1":
//                System.out.println("You have chosen optimized algorithm");
//                break;
//            case "2":
//                System.out.println("You have chosen steady bidirectional algorithm");
//                packagingAlgorithmType = PackagingAlgorithmType.STEADY_BIDIRECTIONAL;
//                break;
//            default:
//                System.out.println("Ok, you don't want to use me. Bye");
//                return;
//        }
//        System.out.println("Input truck quantity:");
//        int truckQuantity = Integer.parseInt(scanner.nextLine().trim());
//        int truckWidth = Integer.parseInt(properties.getProperty("truck-width"));
//        int truckHeight = Integer.parseInt(properties.getProperty("truck-height"));
//
//        Runnable task = packagingTaskCreator.createPackagingTask(
//                in, out,
//                truckWidth,
//                truckHeight,
//                truckQuantity,
//                packagingAlgorithmType
//        );
//        task.run();
//        System.out.println("Packaging task successfully finished");
//    }
}