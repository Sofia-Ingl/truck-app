package projects.liga.config;

import java.io.IOException;

public interface ConfigFileHandler {

    String DEFAULT_CONFIG_FILE_NAME = "app.config";

    Config loadConfig() throws IOException;

    Config loadConfig(String filename) throws IOException;

}
