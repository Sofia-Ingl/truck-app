package projects.liga.config.file_handling;

import java.util.Properties;

public interface ConfigFileHandler {

    Properties loadProperties(String filename);

}
