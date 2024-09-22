package ru.liga.truckapp.io;

import java.util.Properties;

public interface CommandLineArgumentsParser {

    Properties parse(String[] args);
}
