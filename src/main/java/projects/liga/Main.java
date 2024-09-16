package projects.liga;

import projects.liga.config.file_handling.ConfigFileHandler;
import projects.liga.config.file_handling.ConfigFileHandlerImpl;
import projects.liga.config.params_handling.ParamsHandler;
import projects.liga.config.params_handling.ParamsHandlerImpl;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        try {
            ConfigFileHandler configFileHandler = new ConfigFileHandlerImpl();
            Map<String, String> params = configFileHandler.loadParams();

            ParamsHandler paramsHandler = new ParamsHandlerImpl();
            List<Optional<Runnable>> tasks = paramsHandler.getRunnableTasksFromParamsMap(params);

            for (Optional<Runnable> task : tasks) {
                task.ifPresent(Runnable::run);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}