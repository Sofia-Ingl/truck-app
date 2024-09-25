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

    /**
     * Функция, обрабатывающая пользовательский ввод
     *
     * @param counters объекты для подсчета посылок, привязанные к алгоритмам
     * @param packagers объекты для упаковки посылок, привязанные к алгоритмам
     * @param properties аргументы программы (тут хранятся высота и ширина грузовика)
     */
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
        String userTaskChoice = scanner.nextLine().trim().toUpperCase();
        log.debug("User task choice: {}", userTaskChoice);
        return TaskType.valueOf(userTaskChoice);
    }


    private PackagingAlgorithmType choosePackagingAlgorithmType(Scanner scanner) {
        userOutput.println("Available algorithm types:");
        userOutput.println("a. optimized");
        userOutput.println("b. steady_bidirectional");
        userOutput.println("Choose your choice here (print full name):");
        String userAlgorithmChoice = scanner.nextLine().trim().toUpperCase();
        log.debug("User packaging algorithm choice: {}", userAlgorithmChoice);
        return PackagingAlgorithmType.valueOf(userAlgorithmChoice);
    }

    private void processCountingTask(Scanner scanner,
                                     Map<CountingAlgorithmType, CountingTaskTemplate> countersAvailable) {
        try {
            userOutput.println("You have chosen counting task");
            userOutput.println("Input your json file name here");
            String filename = scanner.nextLine().trim();
            log.debug("Filename for counting task: {}", filename);
            CountingTaskTemplate countingTask = countersAvailable.get(CountingAlgorithmType.DEFAULT);
            countingTask.execute(filename);
        } catch (Exception e) {
            log.error("Error while processing counting task: {}", e.getMessage());
        }

    }

    private void processPackagingTask(Scanner scanner,
                                      Map<PackagingAlgorithmType, PackagingTaskTemplate> packagers,
                                      Properties properties) {

        try {
            userOutput.println("You have chosen parcel packaging task");
            userOutput.println("Input your input & output file names here separated by ,");
            String input = scanner.nextLine().trim();
            log.debug("Filenames for packaging task: {}", userInput);
            String[] filenames = input.split(",");
            String inputFileName = filenames[0].trim();
            String outputFileName = filenames[1].trim();

            PackagingAlgorithmType packagingAlgorithmType = choosePackagingAlgorithmType(scanner);

            userOutput.println("Input truck quantity:");

            input = scanner.nextLine().trim();
            log.debug("Truck quantity: {}; truck width: {}; truck height: {}",
                    input,
                    properties.getProperty("truck-width"),
                    properties.getProperty("truck-height"));

            int truckQuantity = Integer.parseInt(input);
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
        } catch (Exception e) {
            log.error("Error while processing packaging task: {}", e.getMessage());
        }
    }
}
