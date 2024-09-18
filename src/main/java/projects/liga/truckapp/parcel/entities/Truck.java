package projects.liga.truckapp.parcel.entities;

import lombok.*;
import projects.liga.truckapp.parcel.exceptions.TruckException;

import java.util.Arrays;

@Getter
public class Truck {

    private char[][] back;
    private int width;
    private int height;

    int[] occupiedCapacityByRow;

    public Truck(Integer width, Integer height) {
        back = new char[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(back[i], ' ');
        }

        this.width = width;
        this.height = height;

        occupiedCapacityByRow = new int[height];
        Arrays.fill(occupiedCapacityByRow, 0);
    }


    public Truck(Integer width, Integer height, char[][] back) {

        occupiedCapacityByRow = new int[height];
        Arrays.fill(occupiedCapacityByRow, 0);

        this.back = new char[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.back[i][j] = back[i][j];
                if (back[i][j] != ' ')
                    occupiedCapacityByRow[i]++;
            }
        }

        this.width = width;
        this.height = height;


    }

    public void loadParcel(int x,
                           int y,
                           Parcel suitableParcel) {


        if (!canLoadParcel(x, y, suitableParcel)) {
            throw new TruckException("Can't load parcel on position " + x + ", " + y);
        }

        for (int i = 0; i < suitableParcel.getHeight(); i++) {

            for (int j = 0; j < suitableParcel.getMaxWidth(); j++) {

                int yBackCoordinate = y + i;
                int xBackCoordinate = x + j;

                back[yBackCoordinate][xBackCoordinate] =
                        suitableParcel.getFilling()[suitableParcel.getHeight() - 1 - i][j];

                if (suitableParcel.getFilling()[suitableParcel.getHeight() - 1 - i][j] != ' ') {
                    occupiedCapacityByRow[yBackCoordinate]++;
                }

            }
        }


    }


    public boolean canLoadParcel(int x,
                                 int y,
                                 Parcel suitableParcel) {

        if (suitableParcel.getHeight() > height - y || suitableParcel.getMaxWidth() > width - x) {
            return false;
        }

        for (int i = 0; i < suitableParcel.getHeight(); i++) {
            for (int j = 0; j < suitableParcel.getMaxWidth(); j++) {

                int yBackCoordinate = y + i;
                int xBackCoordinate = x + j;

                if (back[yBackCoordinate][xBackCoordinate] != ' ') {
                    return false;
                }

            }
        }
        return true;
    }

    public char[] getRowSlice(int x, int y) {
        char[] slice = new char[width - x];
        for (int i = x; i < width; i++) {
            if (y >= 0)
                slice[i - x] = back[y][i];
            else
                slice[i - x] = '+';
        }
        return slice;
    }


    public boolean checkParcelBottomWillNotHang(int x, int y, int parcelWidth) {
        if (y <= 0) return true;

        int blanks = 0;
        for (int i = x; i < x + parcelWidth; i++) {
            if (back[y - 1][i] == ' ') blanks++;
        }
        return blanks * 2 < parcelWidth;
    }

    public void print() {

        System.out.print("\n");
        for (int i = height - 1; i >= 0; i--) {
            System.out.print("+");
            for (int j = 0; j < width; j++) {
                System.out.print(back[i][j]);
            }
            System.out.print("+\n");
        }
        for (int i = 0; i < width + 2; i++) {
            System.out.print("+");
        }
        System.out.print("\n");

    }
}
