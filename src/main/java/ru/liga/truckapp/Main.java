package ru.liga.truckapp;

import ru.liga.truckapp.config.creators.*;
import ru.liga.truckapp.config.entities.AlgorithmType;
import ru.liga.truckapp.config.file.ConfigFileHandler;
import ru.liga.truckapp.config.file.ConfigFileHandlerImpl;
import ru.liga.truckapp.parcel.tasks.CountingTask;
import ru.liga.truckapp.parcel.tasks.PackagingTask;

import java.io.IOException;
import java.util.*;

public class Main {

    private static String DEFAULT_CONFIG_FILE_NAME = "app.config";

    public static void main(String[] args) {

        try {

            Properties properties = readConfiguration(args);

            CountingTaskCreator countingTaskCreator = new DefaultCountingTaskCreator();
            PackagingTaskCreator packagingTaskCreator = new DefaultPackagingTaskCreator();

            List<Optional<Runnable>> tasks = createTasksFromGivenParameters(
                    properties,
                    countingTaskCreator,
                    packagingTaskCreator
            );
            for (Optional<Runnable> task : tasks) {
                task.ifPresent(Runnable::run);
            }
            consolePhase(properties, countingTaskCreator, packagingTaskCreator);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static Properties readConfiguration(String[] args) {

        String configFileName = DEFAULT_CONFIG_FILE_NAME;
        if (args.length > 0) {
            configFileName = args[0];
        }
        ConfigFileHandler configFileHandler = new ConfigFileHandlerImpl();
        return configFileHandler.loadProperties(configFileName);
    }

    private static List<Optional<Runnable>> createTasksFromGivenParameters(Properties properties,
                                                                           CountingTaskCreator countingTaskCreator,
                                                                           PackagingTaskCreator packagingTaskCreator) {


        RunnableListCreator runnableListCreator = new RunnableListCreatorImpl(
                countingTaskCreator,
                packagingTaskCreator
        );
        return runnableListCreator.createRunnableTasksFromProperties(properties);
    }


    private static void consolePhase(Properties properties,
                                     CountingTaskCreator countingTaskCreator,
                                     PackagingTaskCreator packagingTaskCreator) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("All tasks from config file done");

        boolean stop = askForStop(scanner);
        while (!stop) {

            String taskType = chooseTaskType(scanner);
            switch (taskType) {
                case "1":
                    processCountingTask(scanner, countingTaskCreator);
                    break;
                case "2":
                    processPackagingTask(scanner, properties, packagingTaskCreator);
                    break;
                default:
                    System.out.println("Ok, you don't want to use me. Bye");
                    System.exit(0);
            }

            stop = askForStop(scanner);
        }
        scanner.close();
    }


    private static boolean askForStop(Scanner scanner) {
        System.out.println("Do you want to stop the program? (yes/...)");
        String answer = scanner.nextLine();
        return answer.trim().equalsIgnoreCase("yes");
    }

    private static String chooseTaskType(Scanner scanner) {
        System.out.println("Choose one of the following options:");
        System.out.println("1. count");
        System.out.println("2. pack");
        System.out.println("Write its number here:");
        return scanner.nextLine().trim();
    }

    private static void processCountingTask(Scanner scanner, CountingTaskCreator countingTaskCreator) throws IOException {
        System.out.println("You have chosen counting task");
        System.out.println("Input your json file name here");
        String filename = scanner.nextLine().trim();
        Runnable task = countingTaskCreator.createCountingTask(filename);
        task.run();
        System.out.println("Counting task successfully finished");
    }

    private static void processPackagingTask(Scanner scanner,
                                             Properties properties,
                                             PackagingTaskCreator packagingTaskCreator) throws IOException {
        System.out.println("You have chosen parcel packaging task");
        System.out.println("Input your input & output file names here separated by ,");
        String[] filenames = scanner.nextLine().trim().split(",");
        String in = filenames[0].trim();
        String out = filenames[1].trim();
        System.out.println("Available algorithm types:");
        System.out.println("1. optimized");
        System.out.println("2. steady_bidirectional");
        System.out.println("Choose your number here:");
        String choice = scanner.nextLine().trim();
        AlgorithmType algorithmType = AlgorithmType.OPTIMIZED;
        switch (choice) {
            case "1":
                System.out.println("You have chosen optimized algorithm");
                break;
            case "2":
                System.out.println("You have chosen steady bidirectional algorithm");
                algorithmType = AlgorithmType.STEADY_BIDIRECTIONAL;
                break;
            default:
                System.out.println("Ok, you don't want to use me. Bye");
                System.exit(0);
        }
        System.out.println("Input truck quantity:");
        int truckQuantity = Integer.parseInt(scanner.nextLine().trim());
        int truckWidth = Integer.parseInt(properties.getProperty("truck-width"));
        int truckHeight = Integer.parseInt(properties.getProperty("truck-height"));

        Runnable task = packagingTaskCreator.createPackagingTask(
                in, out,
                truckWidth,
                truckHeight,
                truckQuantity,
                algorithmType
        );
        task.run();
        System.out.println("Packaging task successfully finished");
    }
}