package ru.liga.truckapp.io.creators;

import ru.liga.truckapp.io.enums.PackagingAlgorithmType;
import ru.liga.truckapp.parcel.file.parcel.DefaultParcelFileHandler;
import ru.liga.truckapp.parcel.file.parcel.ParcelFileHandler;
import ru.liga.truckapp.parcel.file.truck.TruckFileHandler;
import ru.liga.truckapp.parcel.file.truck.TruckJsonFileHandler;
import ru.liga.truckapp.parcel.packaging.OptimizedPackagingAlgorithm;
import ru.liga.truckapp.parcel.packaging.ParcelPackager;
import ru.liga.truckapp.parcel.packaging.SteadyBidirectionalPackagingAlgorithm;
import ru.liga.truckapp.parcel.printing.DefaultTruckPrinter;
import ru.liga.truckapp.parcel.printing.TruckPrinter;
import ru.liga.truckapp.parcel.tasks.DefaultPackagingTaskTemplate;
import ru.liga.truckapp.parcel.tasks.PackagingTaskTemplate;
import ru.liga.truckapp.parcel.validation.DefaultParcelValidator;

import java.io.PrintStream;

public class DefaultPackagingTaskTemplateCreator implements PackagingTaskTemplateCreator {

    /**
     * Функция для создания дефолтных упаковочных тасок
     *
     * @param algorithm алгоритм упаковки
     * @param userOutput стрим, куда печатать вывод
     * @return шаблон дефолтной упаковочной таски
     */
    @Override
    public PackagingTaskTemplate create(PackagingAlgorithmType algorithm, PrintStream userOutput) {

        ParcelFileHandler parcelFileHandler = new DefaultParcelFileHandler(new DefaultParcelValidator());
        ParcelPackager parcelPackager = switch (algorithm) {
            case OPTIMIZED -> new OptimizedPackagingAlgorithm();
            case STEADY_BIDIRECTIONAL -> new SteadyBidirectionalPackagingAlgorithm();
        };
        TruckFileHandler truckFileHandler = new TruckJsonFileHandler();
        TruckPrinter truckPrinter = new DefaultTruckPrinter(userOutput);
        return new DefaultPackagingTaskTemplate(
                parcelFileHandler,
                parcelPackager,
                truckFileHandler,
                truckPrinter
        );
    }
}
