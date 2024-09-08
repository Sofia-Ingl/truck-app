package projects.liga.config;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ConfigFileHandlerImpl implements ConfigFileHandler {

    @Override
    public Config loadConfig() throws IOException {
        return loadConfig(DEFAULT_CONFIG_FILE_NAME);
    }


    @Override
    public Config loadConfig(String filename) throws IOException {

        File configFile = new File(filename);
        Scanner configScanner = new Scanner(configFile);

        String parcelFileName = null;
        AlgorithmType algorithmType = null;
        Integer truckWidth = null;
        Integer truckHeight = null;

        while (configScanner.hasNextLine()) {
            String paramLine = configScanner.nextLine();
            String[] paramWithValue = paramLine
                    .replace(" ", "")
                    .split("=");
            if (paramWithValue.length != 2) {
                throw new IOException("Invalid config file format");
            }
            String paramName = paramWithValue[0];
            String paramValue = paramWithValue[1];

            if (paramName.equals("parcelFileName")) {
                parcelFileName = paramValue;
            } else if (paramName.equals("algorithmType")) {
                algorithmType = AlgorithmType.valueOf(paramValue);
            } else if (paramName.equals("truck-width")) {
                truckWidth = Integer.valueOf(paramValue);
            } else if (paramName.equals("truck-height")) {
                truckHeight = Integer.valueOf(paramValue);
            } else
                throw new IOException("Invalid config file format");
        }

        if (parcelFileName == null || algorithmType == null) {
            throw new IOException("Not all required params found!");
        }

        return new Config(parcelFileName, algorithmType, truckWidth, truckHeight);
    }

}
