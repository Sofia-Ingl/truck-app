package projects.liga.config.file_handling;

import projects.liga.config.exceptions.ConfigException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConfigFileHandlerImpl implements ConfigFileHandler {

    @Override
    public Map<String, String> loadParams() throws IOException {
        return loadParams(DEFAULT_CONFIG_FILE_NAME);
    }

    @Override
    public Map<String, String> loadParams(String filename) throws IOException {

        File configFile = new File(filename);
        Scanner configScanner = new Scanner(configFile);

        Map<String, String> params = new HashMap<>();

        while (configScanner.hasNextLine()) {
            String paramLine = configScanner.nextLine().replace(" ", "");
            if (!paramLine.isEmpty()) {
                String[] paramWithValue = paramLine
                        .split("=");
                if (paramWithValue.length != 2) {
                    throw new ConfigException("Invalid config file format");
                }
                String paramName = paramWithValue[0];
                String paramValue = paramWithValue[1];

                params.put(paramName, paramValue);
            }

        }

        return params;
    }

}
