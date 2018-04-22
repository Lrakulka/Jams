package qualification_round_2018;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Scanner;

public class GoGopher {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int area = in.nextInt();
            processGopher(area, in);
        }
    }

    private static void processGopher(int area, final Scanner in) {
        int i = 2, j = 2;
        Boolean isLeftShift = null;
        int shifts = area / 3 + (area % 3 != 0 ? 1 : 0);
        shifts = shifts < 3 ? 3 : shifts;
        boolean[][] leftArea = new boolean[4][shifts + 1];
        boolean[][] bottomArea = new boolean[shifts + 1][4];
        Point processedPosition = new Point(0, 0);
        do {
            if (Objects.isNull(isLeftShift) || isLeftShift) {
                leftArea[processedPosition.x][processedPosition.y] = true;
                if (isLeftShiftAvailable(leftArea, j) && j < leftArea[0].length - 2) {
                    isLeftShift = Boolean.TRUE;
                    do {
                        j++;
                    } while (isLeftShiftAvailable(leftArea, j) && j < leftArea[0].length - 2);
                }

            }
            if (Objects.isNull(isLeftShift) || !isLeftShift) {
                bottomArea[processedPosition.x][processedPosition.y] = true;
                if (isBottomShiftAvailable(bottomArea, i) && i < bottomArea.length - 2) {
                    isLeftShift = Boolean.FALSE;
                    do {
                        i++;
                    } while (isBottomShiftAvailable(bottomArea, i) && i < bottomArea.length - 2);
                }
            }

            sendPosition(i, j);
            processedPosition = getGopherPosition(in);
            if (processedPosition.y == -1) {
                System.err.println("Invalid " + i + " " + j);
                System.exit(10);
            }
        } while (processedPosition.y != 0);
    }

    private static boolean isBottomShiftAvailable(boolean[][] bottomArea, int i) {
        boolean isAvailable = true;
        for (int l = 1; l < 4; ++l) {
            isAvailable &= bottomArea[i - 1][l];
        }
        return isAvailable;
    }

    private static boolean isLeftShiftAvailable(boolean[][] leftArea, int j) {
        boolean isAvailable = true;
        for (int l = 1; l < 4; ++l) {
            isAvailable &= leftArea[l][j - 1];
        }
        return isAvailable;
    }

    private static Point getGopherPosition(final Scanner in) {
        return new Point(in.nextInt(), in.nextInt());
    }

    private static void sendPosition(int i, int j) {
        System.out.println(i + " " + j);
    }
}
