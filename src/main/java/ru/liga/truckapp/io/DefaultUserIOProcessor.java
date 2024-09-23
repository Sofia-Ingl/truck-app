package ru.liga.truckapp.io;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.truckapp.io.enums.CountingAlgorithmType;
import ru.liga.truckapp.io.enums.PackagingAlgorithmType;
import ru.liga.truckapp.io.enums.TaskType;
import ru.liga.truckapp.parcel.tasks.CountingTaskTemplate;
import ru.liga.truckapp.parcel.tasks.PackagingTaskTemplate;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

@Slf4j
@AllArgsConstructor
public class DefaultUserIOProcessor implements UserIOProcessor {

    private final InputStream userInput;
    private final PrintStream userOutput;

    @Override
    public void processUserIO(Map<CountingAlgorithmType, CountingTaskTemplate> counters,
                              Map<PackagingAlgorithmType, PackagingTaskTemplate> packagers,
                              Properties properties) {

        try (Scanner scanner = new Scanner(userInput)) {
            boolean stop = false;
            while (!stop) {

                TaskType taskType = chooseTaskType(scanner);
                switch (taskType) {
                    case COUNT:
                        processCountingTask(scanner, counters);
                        break;
                    case PACK:
                        processPackagingTask(scanner, packagers, properties);
                        break;
                    default:
                        userOutput.println("Ok, you don't want to use me. Bye");
                        return;
                }

                stop = askForStop(scanner);
            }
        }
    }


    private boolean askForStop(Scanner scanner) {
        userOutput.println("Do you want to continue the program? (yes/y)");
        String answer = scanner.nextLine().trim();
        return !(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y"));
    }

    private TaskType chooseTaskType(Scanner scanner) {
        userOutput.println("Choose one of the following options:");
        userOutput.println("a. count");
        userOutput.println("b. pack");
        userOutput.println("Write your choice here (print full name):");
        return TaskType.valueOf(scanner.nextLine().trim().toUpperCase());
    }


    private PackagingAlgorithmType choosePackagingAlgorithmType(Scanner scanner) {
        userOutput.println("Available algorithm types:");
        userOutput.println("a. optimized");
        userOutput.println("b. steady_bidirectional");
        userOutput.println("Choose your choice here (print full name):");
        return PackagingAlgorithmType.valueOf(scanner.nextLine().trim().toUpperCase());
    }

    private void processCountingTask(Scanner scanner,
                                     Map<CountingAlgorithmType, CountingTaskTemplate> countersAvailable) {
        userOutput.println("You have chosen counting task");
        userOutput.println("Input your json file name here");
        String filename = scanner.nextLine().trim();
        CountingTaskTemplate countingTask = countersAvailable.get(CountingAlgorithmType.DEFAULT);
        if (countingTask == null) throw new RuntimeException("Counting task template not found");
        countingTask.execute(filename);
    }

    private void processPackagingTask(Scanner scanner,
                                      Map<PackagingAlgorithmType, PackagingTaskTemplate> packagers,
                                      Properties properties) {
        String[] filenames = {};
        while (filenames.length != 2) {
            userOutput.println("You have chosen parcel packaging task");
            userOutput.println("Input your input & output file names here separated by ,");
            filenames = scanner.nextLine().trim().split(",");
        }
        String inputFileName = filenames[0].trim();
        String outputFileName = filenames[1].trim();

        PackagingAlgorithmType packagingAlgorithmType = choosePackagingAlgorithmType(scanner);

        userOutput.println("Input truck quantity:");

        int truckQuantity = Integer.parseInt(scanner.nextLine().trim());
        int truckWidth = Integer.parseInt(properties.getProperty("truck-width"));
        int truckHeight = Integer.parseInt(properties.getProperty("truck-height"));

        PackagingTaskTemplate packagingTask = packagers.get(packagingAlgorithmType);
        packagingTask.execute(
                inputFileName,
                outputFileName,
                truckWidth,
                truckHeight,
                truckQuantity
        );
    }
}
