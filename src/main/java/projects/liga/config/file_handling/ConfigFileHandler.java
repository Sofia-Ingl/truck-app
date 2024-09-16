package projects.liga.config.file_handling;

import java.io.IOException;
import java.util.Map;

public interface ConfigFileHandler {

    String DEFAULT_CONFIG_FILE_NAME = "app.config";

    Map<String, String> loadParams() throws IOException;

    Map<String, String> loadParams(String filename) throws IOException;

}
