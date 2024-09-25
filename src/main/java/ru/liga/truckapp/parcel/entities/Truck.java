package ru.liga.truckapp.parcel.entities;

import lombok.*;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;

@Getter
public class Truck {

    private final char[][] back;
    private final int width;
    private final int height;

    private final int[] occupiedCapacityByRow;

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

    /**
     * Процедура, загружающая посылку на заданное место в грузовике
     *
     * @param x позиция по горизонтали
     * @param y позиция по вертикали
     * @param suitableParcel посылка
     */
    public void loadParcel(int x,
                           int y,
                           Parcel suitableParcel) {


        if (!canLoadParcel(x, y, suitableParcel)) {
            throw new IllegalArgumentException("Can't load parcel into truck on position "
                    + x + ", " + y + "; truck:\n" + this +"\n" + "parcel:\n" + suitableParcel);
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

    /**
     * Функция, проверяющая, можно ли загрузить посылку на заданное место в грузовике
     *
     * @param x позиция по горизонтали
     * @param y позиция по вертикали
     * @param suitableParcel посылка
     *
     * @return можно ли загрузить посылку
     */
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

    /**
     * Функция, предоставляющая срез ряда в грузовике
     *
     * @param x стартовая позиция по горизонтали
     * @param y позиция по вертикали (индекс ряда, может быть отрицательный)
     *
     * @return срез части ряда или +++..., если индекс ряда отрицательный (+++... = срез дна)
     */
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


    /**
     * Функция, проверяющая, что дно посылки не будет нависать
     *
     * @param x позиция по горизонтали, куда будет загружаться посылка
     * @param y позиция по вертикали, куда будет загружаться посылка
     * @param parcelWidth ширина посылки
     *
     * @return не будет ли дно нависать (не нависает = true)
     */
    public boolean checkParcelBottomWillNotHang(int x, int y, int parcelWidth) {
        if (y <= 0) return true;

        int blanks = 0;
        for (int i = x; i < x + parcelWidth; i++) {
            if (back[y - 1][i] == ' ') blanks++;
        }
        return blanks * 2 < parcelWidth;
    }

    /**
     * Процедура, печатающая грузовик
     *
     * @param out куда грузовик печатается
     */
    public void print(PrintStream out) {

        out.println(this);

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (int i = height - 1; i >= 0; i--) {
            sb.append("+");
            for (int j = 0; j < width; j++) {
                sb.append(back[i][j]);
            }
            sb.append("+\n");
        }
        sb.append("+".repeat(Math.max(0, width + 2)));

        return sb.toString();
    }
}
