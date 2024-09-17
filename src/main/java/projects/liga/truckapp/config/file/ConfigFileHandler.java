package projects.liga.truckapp.config.file;

import java.util.Properties;

public interface ConfigFileHandler {

    Properties loadProperties(String filename);

}
