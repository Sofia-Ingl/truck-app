package ru.liga.truckapp.io;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.util.Properties;

@Slf4j
@AllArgsConstructor
public class TruckCommandLineArgumentsParser implements CommandLineArgumentsParser {

    private final CommandLineParser parser;

    /**
     * Функция, парсящая аргументы командной строки
     *
     * @param args аргументы командной строки
     * @return прочитанные пары ключ-значение
     */
    public Properties parse(String[] args) {

        Properties props = new Properties();

        Options options = new Options();

        Option width = new Option("w", "width", true, "truck width");
        width.setRequired(true);
        options.addOption(width);

        Option height = new Option("h", "height", true, "truck height");
        height.setRequired(true);
        options.addOption(height);

        try {
            CommandLine cmd = parser.parse(options, args);
            props.put("truck-width", cmd.getOptionValue("width"));
            props.put("truck-height", cmd.getOptionValue("height"));

        } catch (ParseException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Required cli options not found", e);
        }

        return props;
    }

}
