package projects.liga.truckapp.parcel.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;

@Builder
@Getter
@AllArgsConstructor
public class Parcel implements Comparable<Parcel> {

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


    public boolean equals(Parcel obj) {
        return Arrays.deepEquals(this.filling, obj.filling);
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
