package projects.liga.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Config {
    String parcelFileName;
    AlgorithmType algorithmType;
    Integer truckWidth;
    Integer truckHeight;
}
