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

    public void loadParcel(int x,
                           int y,
                           ParcelType suitableParcel) {


        if (!checkCanLoadParcel(x, y, suitableParcel)) {
            // error
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


    private boolean checkCanLoadParcel(int x,
                                       int y,
                                       ParcelType suitableParcel) {

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


//    public Slot getNextPlaceForAnyParcel(int startingXPoint,
//                                         int startingYPoint) {
//
//        int x = startingXPoint;
//        int y = startingYPoint;
//        int maxHeight;
//        int maxWidth;
//
//        int currentLoadedCapacity = occupiedCapacityByRow[startingYPoint];
//
//        // find new coordinates
//        if (startingXPoint >= width) {
//
//            for (int i = y + 1; i < height; i++) {
//                if (occupiedCapacityByRow[i] != width && occupiedCapacityByRow[i] < currentLoadedCapacity) {
//                    y = i;
//                    x = occupiedCapacityByRow[i];
//                    break;
//                }
//            }
//        }
//
//        int occupiedCapacityOfCurrentRow = occupiedCapacityByRow[y];
//        maxHeight = 1;
//
//        for (int i = y + 1; i < height; i++) {
//            if (occupiedCapacityByRow[i] != occupiedCapacityOfCurrentRow) {
//                break;
//            }
//            maxHeight += 1;
//        }
//
//        maxWidth = width - x;
//
//        if (maxWidth <= 0 || maxHeight <= 0) return null;
//
//        return new Slot(x, y, maxWidth, maxHeight);
//
//    }

    public void print() {

        System.out.print("\n");
        for (int i = height-1; i >= 0; i--) {
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
