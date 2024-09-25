package ru.liga.truckapp.io.creators;

import ru.liga.truckapp.io.enums.CountingAlgorithmType;
import ru.liga.truckapp.parcel.counting.DefaultParcelCounter;
import ru.liga.truckapp.parcel.counting.ParcelCounter;
import ru.liga.truckapp.parcel.file.truck.TruckFileHandler;
import ru.liga.truckapp.parcel.file.truck.TruckJsonFileHandler;
import ru.liga.truckapp.parcel.printing.DefaultTruckPrinter;
import ru.liga.truckapp.parcel.printing.TruckPrinter;
import ru.liga.truckapp.parcel.tasks.CountingTaskTemplate;
import ru.liga.truckapp.parcel.tasks.DefaultCountingTaskTemplate;

import java.io.PrintStream;
import java.io.PrintWriter;

public class DefaultCountingTaskTemplateCreator implements CountingTaskTemplateCreator {

    /**
     * Функция для создания дефолтных тасок подсчета посылок
     *
     * @param algorithm алгоритм подсчета (сейчас всего один)
     * @param userOutput стрим, куда печатать вывод
     * @return шаблон дефолтной таски подсчета посылок
     */
    @Override
    public CountingTaskTemplate create(CountingAlgorithmType algorithm, PrintStream userOutput) {
        TruckFileHandler truckFileHandler = new TruckJsonFileHandler();
        ParcelCounter parcelCounter = switch (algorithm) {
            case DEFAULT -> new DefaultParcelCounter();
        };
        TruckPrinter truckPrinter = new DefaultTruckPrinter(userOutput);
        return new DefaultCountingTaskTemplate(
                truckFileHandler,
                parcelCounter,
                truckPrinter
        );
    }
}
