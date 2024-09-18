package projects.liga.truckapp.config.params;

import projects.liga.truckapp.config.entities.AlgorithmType;
import projects.liga.truckapp.parcel.tasks.CountingTask;
import projects.liga.truckapp.parcel.tasks.PackagingTask;

import java.util.*;

public class ParamsHandlerImpl implements ParamsHandler {

    private final String TASKS_PARAM_NAME = "tasks";
    private final String TASKS_PARAM_VALUE_PACKAGING = "packaging";
    private final String TASKS_PARAM_VALUE_COUNTING = "counting";

    @Override
    public List<Optional<Runnable>> createRunnableTasksFromProperties(Properties properties) {

        if (!properties.containsKey(TASKS_PARAM_NAME)) {
            throw new RuntimeException("Not all required params found! Param '"
                    + TASKS_PARAM_NAME
                    + "' not found");
        }

        List<Optional<Runnable>> runnableTasks = new ArrayList<>();
        runnableTasks.add(getPackagingTask(properties));
        runnableTasks.add(getCountingTask(properties));

        return runnableTasks;
    }


    private Optional<Runnable> getPackagingTask(Properties properties) {
        String tasksToRun = properties.getProperty(TASKS_PARAM_NAME);
        if (tasksToRun == null || !tasksToRun.toLowerCase().contains(TASKS_PARAM_VALUE_PACKAGING)) {
            return Optional.empty();
        }

        try {
            String inputFileName = properties.getProperty("packaging-input");
            String outputFileName = properties.getProperty("packaging-output");
            String truckWidth = properties.getProperty("truck-width");
            String truckHeight = properties.getProperty("truck-height");
            String truckQuantity = properties.getProperty("truck-quantity");
            String algorithm = properties.getProperty("algorithm");

            if (algorithm == null || inputFileName == null
                    || outputFileName == null || truckWidth == null
                    || truckHeight == null || truckQuantity == null) {
                throw new RuntimeException("Not all required params for packaging task found!");
            }

            Runnable task = new PackagingTask(
                    inputFileName, outputFileName,
                    Integer.parseInt(truckWidth),
                    Integer.parseInt(truckHeight),
                    Integer.parseInt(truckQuantity),
                    AlgorithmType.valueOf(algorithm.toUpperCase())
            );

            return Optional.of(task);

        } catch (IllegalArgumentException e) {

            throw new IllegalStateException("Invalid param value for packaging task!");

        }

    }

    private Optional<Runnable> getCountingTask(Properties properties) {

        String tasksToRun = properties.getProperty(TASKS_PARAM_NAME);
        if (tasksToRun == null || !tasksToRun.toLowerCase().contains(TASKS_PARAM_VALUE_COUNTING)) {
            return Optional.empty();
        }

        String inputFileName = properties.getProperty("counting-input");

        if (inputFileName == null) {
            throw new IllegalStateException("Not all required params for counting task found!");
        }

        Runnable task = new CountingTask(inputFileName);
        return Optional.of(task);

    }
}
