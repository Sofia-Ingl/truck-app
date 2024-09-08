package projects.liga.parcel.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;

@Builder
@Getter
@AllArgsConstructor
public class ParcelType implements Comparable<ParcelType> {

    private Integer typeCode;
    private Integer maxWidth;
    private Integer height;
    private Boolean isRectangle;
    private char[][] filling;

    @Override
    public int compareTo(ParcelType o) {
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

    public boolean equals(ParcelType obj) {
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
