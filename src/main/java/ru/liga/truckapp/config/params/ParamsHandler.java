package ru.liga.truckapp.config.params;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public interface ParamsHandler {

    List<Optional<Runnable>> createRunnableTasksFromProperties(Properties properties);

}
