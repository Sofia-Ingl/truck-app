package ru.liga.truckapp.config.file;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileHandlerImpl implements ConfigFileHandler {

    @Override
    public Properties loadProperties(String filename) {

        try (FileReader fileReader = new FileReader(filename)) {

            Properties properties = new Properties();
            properties.load(fileReader);
            return properties;

        } catch (IOException e) {
            throw new IllegalStateException("IOException occurred: " + e.getMessage());
        }
    }

}
