package ru.liga.truckapp.parcel.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;

@Builder
@Getter
@AllArgsConstructor
public class Parcel implements Comparable<Parcel> {

    public final static Comparator<Parcel> typeCodeComparator
            = Comparator.comparing(Parcel::getTypeCode);
    public final static Comparator<Parcel> widthComparator
            = Parcel::compareTo;

    private final Integer typeCode;
    private final Integer maxWidth;
    private final Integer height;
    private final Boolean isRectangle;
    private final char[][] filling;

    @Override
    public int compareTo(Parcel o) {
        int byWidth = this.maxWidth.compareTo(o.maxWidth);
        if (byWidth != 0) {
            return byWidth;
        }
        int byHeight = this.height.compareTo(o.height);
        if (byHeight != 0) {
            return byHeight;
        }
        return typeCode.compareTo(o.typeCode);
    }


    @Override
    public String toString() {
        return "BlockType{" +
                "typeCode=" + typeCode +
                ", maxWidth=" + maxWidth +
                ", height=" + height +
                ", isRectangle=" + isRectangle +
                ", filling=" + Arrays.deepToString(filling) +
                '}';
    }
}
