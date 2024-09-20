package ru.liga.truckapp.config.creators;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.truckapp.config.entities.AlgorithmType;
import ru.liga.truckapp.config.exceptions.ConfigException;

import java.util.*;

@Slf4j
@AllArgsConstructor
public class DefaultRunnableListCreator implements RunnableListCreator {

    private final String TASKS_PARAM_NAME = "tasks";
    private final String TASKS_PARAM_VALUE_PACKAGING = "packaging";
    private final String TASKS_PARAM_VALUE_COUNTING = "counting";

    private final String COUNTING_INPUT_PROPERTY = "counting-input";

    private final String PACKAGING_INPUT_PROPERTY = "packaging-input";
    private final String PACKAGING_OUTPUT_PROPERTY = "packaging-output";
    private final String TRUCK_WIDTH_PROPERTY = "truck-width";
    private final String TRUCK_HEIGHT_PROPERTY = "truck-height";
    private final String TRUCK_QUANTITY_PROPERTY = "truck-quantity";
    private final String ALGORITHM_PROPERTY = "algorithm";

    private final CountingTaskCreator countingTaskCreator;
    private final PackagingTaskCreator packagingTaskCreator;

    @Override
    public List<Optional<Runnable>> createRunnableTasksFromProperties(Properties properties) {

        if (!properties.containsKey(TASKS_PARAM_NAME)) {

            log.error("'tasks' property not found");

            throw new ConfigException("Not all required params found. Param '"
                    + TASKS_PARAM_NAME
                    + "' not found");
        }

        List<Optional<Runnable>> runnableTasks = new ArrayList<>();
        runnableTasks.add(createPackagingTask(properties));
        runnableTasks.add(createCountingTask(properties));

        return runnableTasks;
    }


    private Optional<Runnable> createPackagingTask(Properties properties) {
        String tasksToRun = properties.getProperty(TASKS_PARAM_NAME);
        if (tasksToRun == null || !tasksToRun.toLowerCase().contains(TASKS_PARAM_VALUE_PACKAGING)) {
            log.debug("Empty packaging task created");
            return Optional.empty();
        }

        try {

            String inputFileName = properties.getProperty(PACKAGING_INPUT_PROPERTY);
            String outputFileName = properties.getProperty(PACKAGING_OUTPUT_PROPERTY);
            String truckWidth = properties.getProperty(TRUCK_WIDTH_PROPERTY);
            String truckHeight = properties.getProperty(TRUCK_HEIGHT_PROPERTY);
            String truckQuantity = properties.getProperty(TRUCK_QUANTITY_PROPERTY);
            String algorithm = properties.getProperty(ALGORITHM_PROPERTY);

            if (algorithm == null || inputFileName == null
                    || outputFileName == null || truckWidth == null
                    || truckHeight == null || truckQuantity == null) {
                log.error("Not all required params for packaging task found");
                throw new ConfigException("Not all required params for packaging task found, check: " +
                        PACKAGING_INPUT_PROPERTY + ", " + PACKAGING_OUTPUT_PROPERTY
                        + ", " + TRUCK_WIDTH_PROPERTY + ", " + TRUCK_HEIGHT_PROPERTY
                        + ", " + TRUCK_QUANTITY_PROPERTY + ", " + ALGORITHM_PROPERTY);
            }

            Runnable task = packagingTaskCreator.createPackagingTask(
                    inputFileName, outputFileName,
                    Integer.parseInt(truckWidth),
                    Integer.parseInt(truckHeight),
                    Integer.parseInt(truckQuantity),
                    AlgorithmType.valueOf(algorithm.toUpperCase())

            );

            log.debug("Packaging task created");
            return Optional.of(task);

        } catch (IllegalArgumentException e) {
            log.error("Invalid param value for packaging task");
            throw new ConfigException("Invalid param value for packaging task: " + e.getMessage());

        }

    }

    private Optional<Runnable> createCountingTask(Properties properties) {

        String tasksToRun = properties.getProperty(TASKS_PARAM_NAME);
        if (tasksToRun == null || !tasksToRun.toLowerCase().contains(TASKS_PARAM_VALUE_COUNTING)) {
            log.debug("Empty counting task created");
            return Optional.empty();
        }

        String inputFileName = properties.getProperty(COUNTING_INPUT_PROPERTY);

        if (inputFileName == null) {
            log.error("Not all required params for counting task found");
            throw new ConfigException("Not all required params for counting task found: missing "
                    + COUNTING_INPUT_PROPERTY);
        }

        Runnable task = countingTaskCreator.createCountingTask(inputFileName);
        log.debug("Counting task created");
        return Optional.of(task);

    }
}
