package projects.liga.parcel.entities;

import lombok.*;

import java.util.Arrays;

@Setter
@Getter
public class Truck {

    private char[][] back;
    private int width;
    private int height;

    int[] occupiedCapacityByRow;
    int[] occupiedCapacityByColumn;

    public Truck(Integer width, Integer height) {
        back = new char[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(back[i], ' ');
        }

        this.width = width;
        this.height = height;

        occupiedCapacityByRow = new int[height];
        occupiedCapacityByColumn = new int[width];
        Arrays.fill(occupiedCapacityByRow, 0);
        Arrays.fill(occupiedCapacityByColumn, 0);
    }

    public void loadParcel(int x,
                           int y,
                           ParcelType suitableParcel) {

        for (int i = 0; i < suitableParcel.getHeight(); i++) {

            for (int j = 0; j < suitableParcel.getMaxWidth(); j++) {

                int yBackCoordinate = height - 1 - (y + i);
                int xBackCoordinate = x + j;

                back[yBackCoordinate][xBackCoordinate] = suitableParcel.getFilling()[suitableParcel.getHeight() - 1 - i][j];

                if (suitableParcel.getFilling()[i][j] != ' ') {
                    occupiedCapacityByRow[yBackCoordinate]++;
                    occupiedCapacityByColumn[xBackCoordinate]++;
                }

            }
        }


    }

    public char[] getRowSlice(int x, int y) {
        char[] slice = new char[width - x];
        for (int i = x; i < width; i++) {
            if (y >= 0)
                slice[i - x] = back[height - 1 - y][i];
            else
                slice[i - x] = '+';
        }
        return slice;
    }


    public Slot getNextPlaceForAnyParcel(int startingXPoint,
                                         int startingYPoint) {

        int x = startingXPoint;
        int y = startingYPoint;
        int maxHeight = 0;
        int maxWidth = 0;

        // find new coordinates
        if (startingXPoint >= width) {

            for (int i = height - 1 - (y + 1); i >= 0; i--) {
                if (occupiedCapacityByRow[i] != width) {
                    y = height - 1 - i;
                    x = occupiedCapacityByRow[i];
                    break;
                }
            }
        }

        int occupiedCapacityOfCurrentRow = occupiedCapacityByRow[height - 1 - y];
        maxHeight = 1;

        for (int i = height - 1 - (y + 1); i >= 0; i--) {
            if (occupiedCapacityByRow[i] != occupiedCapacityOfCurrentRow) {
                break;
            }
            maxHeight += 1;
        }

        maxWidth = width - x;

        if (maxWidth <= 0 || maxHeight <= 0) return null;

        return new Slot(x, y, maxWidth, maxHeight);

    }

    public void print() {

        System.out.print("\n");
        for (int i = 0; i < height; i++) {
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
