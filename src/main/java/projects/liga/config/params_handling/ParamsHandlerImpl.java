package projects.liga.config.params_handling;

import projects.liga.config.entities.AlgorithmType;
import projects.liga.config.exceptions.ConfigException;
import projects.liga.parcel.tasks.CountingTask;
import projects.liga.parcel.tasks.PackagingTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ParamsHandlerImpl implements ParamsHandler {

    private final String TASKS_PARAM_NAME = "tasks";
    private final String TASKS_PARAM_VALUE_PACKAGING = "packaging";
    private final String TASKS_PARAM_VALUE_SCANNING = "counting";

    @Override
    public List<Optional<Runnable>> getRunnableTasksFromParamsMap(Map<String, String> paramsMap) {

        if (!paramsMap.containsKey(TASKS_PARAM_NAME)) {
            throw new ConfigException("Not all required params found!");
        }

        List<Optional<Runnable>> runnableTasks = new ArrayList<>();
        runnableTasks.add(getPackagingTask(paramsMap));
        runnableTasks.add(getCountingTask(paramsMap));

        return runnableTasks;
    }


    private Optional<Runnable> getPackagingTask(Map<String, String> paramsMap) {
        String tasksToRun = paramsMap.get(TASKS_PARAM_NAME);
        if (tasksToRun == null || !tasksToRun.toLowerCase().contains(TASKS_PARAM_VALUE_PACKAGING)) {
            return Optional.empty();
        }

        try {
            String inputFileName = paramsMap.get("packaging-input");
            String outputFileName = paramsMap.get("packaging-output");
            String truckWidth = paramsMap.get("truck-width");
            String truckHeight = paramsMap.get("truck-height");
            String truckQuantity = paramsMap.get("truck-quantity");
            String algorithm = paramsMap.get("algorithm");

            if (algorithm == null || inputFileName == null
                    || outputFileName == null || truckWidth == null
                    || truckHeight == null || truckQuantity == null) {
                throw new ConfigException("Not all required params for packaging task found!");
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

            throw new ConfigException("Invalid param value for packaging task!");

        }

    }

    private Optional<Runnable> getCountingTask(Map<String, String> paramsMap) {

        String tasksToRun = paramsMap.get(TASKS_PARAM_NAME);
        if (tasksToRun == null || !tasksToRun.toLowerCase().contains(TASKS_PARAM_VALUE_SCANNING)) {
            return Optional.empty();
        }

        String inputFileName = paramsMap.get("counting-input");

        if (inputFileName == null) {
            throw new ConfigException("Not all required params for counting task found!");
        }

        Runnable task = new CountingTask(inputFileName);
        return Optional.of(task);

    }

}
