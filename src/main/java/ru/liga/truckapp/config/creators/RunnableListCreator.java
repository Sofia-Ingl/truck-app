package ru.liga.truckapp.config.creators;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public interface RunnableListCreator {

    List<Optional<Runnable>> createRunnableTasksFromProperties(Properties properties);

}
