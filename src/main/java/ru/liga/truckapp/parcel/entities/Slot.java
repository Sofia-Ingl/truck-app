package ru.liga.truckapp.parcel.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Slot {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
}
