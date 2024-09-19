import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.liga.truckapp.config.file.ConfigFileHandler;
import ru.liga.truckapp.config.file.ConfigFileHandlerImpl;
import ru.liga.truckapp.config.creators.RunnableListCreator;
import ru.liga.truckapp.config.creators.DefaultRunnableListCreator;
import ru.liga.truckapp.parcel.tasks.CountingTask;
import ru.liga.truckapp.parcel.tasks.PackagingTask;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class RunnableListCreatorTests {

    ConfigFileHandler configFileHandler = new ConfigFileHandlerImpl();
    RunnableListCreator runnableListCreator = new DefaultRunnableListCreator();

    @Test
    void packagingOnlyTest() throws IOException {

        /* TODO create props manually */
        Properties properties = configFileHandler.loadProperties("src/test/resources/packaging-only.config");
        List<Optional<Runnable>> tasks = runnableListCreator.createRunnableTasksFromProperties(properties);

        Assertions.assertEquals(2, tasks.size());
        Assertions.assertNotNull(tasks.get(0).orElse(null));
        Assertions.assertInstanceOf(PackagingTask.class, tasks.get(0).orElse(null));
        Assertions.assertNull(tasks.get(1).orElse(null));

    }

    @Test
    void countingOnlyTest() throws IOException {

        Properties properties = configFileHandler.loadProperties("src/test/resources/counting-only.config");
        List<Optional<Runnable>> tasks = runnableListCreator.createRunnableTasksFromProperties(properties);

        Assertions.assertEquals(2, tasks.size());
        Assertions.assertNull(tasks.get(0).orElse(null));
        Assertions.assertNotNull(tasks.get(1).orElse(null));
        Assertions.assertInstanceOf(CountingTask.class, tasks.get(1).orElse(null));

    }

    @Test
    void tasksParamNotFoundTest() throws IOException {

        Properties properties = configFileHandler.loadProperties("src/test/resources/no-tasks-param.config");
        Assertions.assertThrows(RuntimeException.class,
                ()-> runnableListCreator.createRunnableTasksFromProperties(properties));

    }


    @Test
    void requiredParamMissingTest() throws IOException {

        Properties properties = configFileHandler.loadProperties("src/test/resources/packaging-param-missing.config");
        Assertions.assertThrows(RuntimeException.class,
                ()-> runnableListCreator.createRunnableTasksFromProperties(properties));

    }

}
