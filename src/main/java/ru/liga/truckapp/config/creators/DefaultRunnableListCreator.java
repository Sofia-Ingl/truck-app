package ru.liga.truckapp.config.creators;

import lombok.AllArgsConstructor;
import ru.liga.truckapp.config.entities.AlgorithmType;

import java.util.*;

@AllArgsConstructor
public class DefaultRunnableListCreator implements RunnableListCreator {

    private final String TASKS_PARAM_NAME = "tasks";
    private final String TASKS_PARAM_VALUE_PACKAGING = "packaging";
    private final String TASKS_PARAM_VALUE_COUNTING = "counting";

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
            throw new RuntimeException("Not all required params found! Param '"
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
                throw new RuntimeException("Not all required params for packaging task found");
            }

            Runnable task = packagingTaskCreator.createPackagingTask(
                    inputFileName, outputFileName,
                    Integer.parseInt(truckWidth),
                    Integer.parseInt(truckHeight),
                    Integer.parseInt(truckQuantity),
                    AlgorithmType.valueOf(algorithm.toUpperCase())

            );

            return Optional.of(task);

        } catch (IllegalArgumentException e) {

            throw new RuntimeException("Invalid param value for packaging task: " + e.getMessage());

        }

    }

    private Optional<Runnable> createCountingTask(Properties properties) {

        String tasksToRun = properties.getProperty(TASKS_PARAM_NAME);
        if (tasksToRun == null || !tasksToRun.toLowerCase().contains(TASKS_PARAM_VALUE_COUNTING)) {
            return Optional.empty();
        }

        String inputFileName = properties.getProperty("counting-input");

        if (inputFileName == null) {
            throw new RuntimeException("Not all required params for counting task found");
        }

        Runnable task = countingTaskCreator.createCountingTask(inputFileName);
        return Optional.of(task);

    }
}
