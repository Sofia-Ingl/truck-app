import org.junit.jupiter.api.Test;
import ru.liga.truckapp.config.creators.DefaultCountingTaskCreator;
import ru.liga.truckapp.config.creators.DefaultPackagingTaskCreator;
import ru.liga.truckapp.config.creators.RunnableListCreator;
import ru.liga.truckapp.config.creators.DefaultRunnableListCreator;
import ru.liga.truckapp.config.exceptions.ConfigException;
import ru.liga.truckapp.parcel.tasks.DefaultCountingTaskTemplate;
import ru.liga.truckapp.parcel.tasks.DefaultPackagingTaskTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RunnableListCreatorTests {

    private final RunnableListCreator runnableListCreator = new DefaultRunnableListCreator(
            new DefaultCountingTaskCreator(),
            new DefaultPackagingTaskCreator()
    );

    @Test
    void packagingOnlyTest() throws IOException {

        String config = """
                packaging-input=src/main/resources/packaging-input.txt
                algorithm=optimized
                truck-width=6
                truck-height=6
                truck-quantity=10
                packaging-output=src/main/resources/packaging-output.json
                
                counting-input=src/main/resources/input.json
                
                tasks=packaging
                """;
        Properties properties = new Properties();
        properties.load(new StringReader(config));


        List<Optional<Runnable>> tasks = runnableListCreator.createRunnableTasksFromProperties(properties);

        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks.get(0).orElse(null)).isNotNull();
        assertThat(tasks.get(0).orElse(null)).isInstanceOf(DefaultPackagingTaskTemplate.class);
        assertThat(tasks.get(1).orElse(null)).isNull();

    }

    @Test
    void countingOnlyTest() throws IOException {

        String config = """
                packaging-input=src/main/resources/packaging-input.txt
                algorithm=optimized
                truck-width=6
                truck-height=6
                truck-quantity=10
                packaging-output=src/main/resources/packaging-output.json
                
                counting-input=src/main/resources/input.json
                
                tasks=counting
                """;
        Properties properties = new Properties();
        properties.load(new StringReader(config));

        List<Optional<Runnable>> tasks = runnableListCreator.createRunnableTasksFromProperties(properties);

        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks.get(0).orElse(null)).isNull();
        assertThat(tasks.get(1).orElse(null)).isNotNull();
        assertThat(tasks.get(1).orElse(null)).isInstanceOf(DefaultCountingTaskTemplate.class);


    }

    @Test
    void tasksParamNotFoundTest() throws IOException {

        String config = """
                packaging-input=src/main/resources/packaging-input.txt
                algorithm=optimized
                truck-width=6
                truck-height=6
                truck-quantity=10
                packaging-output=src/main/resources/packaging-output.json
                
                counting-input=src/main/resources/input.json
                """;
        Properties properties = new Properties();
        properties.load(new StringReader(config));


        assertThatThrownBy(() -> runnableListCreator.createRunnableTasksFromProperties(properties))
                .isInstanceOf(ConfigException.class);

    }


    @Test
    void requiredParamMissingTest() throws IOException {

        String config = """
                packaging-input=src/main/resources/packaging-input.txt
                algorithm=optimized
                truck-width=6
                truck-height=6
                packaging-output=src/main/resources/packaging-output.json
                
                counting-input=src/main/resources/input.json
                
                tasks=packaging,counting
                """;
        Properties properties = new Properties();
        properties.load(new StringReader(config));

        assertThatThrownBy(() -> runnableListCreator.createRunnableTasksFromProperties(properties))
                .isInstanceOf(ConfigException.class);

    }


    @Test
    void emptyTasksParamTest() throws IOException {

        String config = """
                packaging-input=src/main/resources/packaging-input.txt
                algorithm=optimized
                truck-width=6
                truck-height=6
                packaging-output=src/main/resources/packaging-output.json
                
                counting-input=src/main/resources/input.json
                
                tasks=
                """;
        Properties properties = new Properties();
        properties.load(new StringReader(config));

        List<Optional<Runnable>> tasks = runnableListCreator.createRunnableTasksFromProperties(properties);

        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks.get(0).orElse(null)).isNull();
        assertThat(tasks.get(1).orElse(null)).isNull();

    }

}
