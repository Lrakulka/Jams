package qualification_round_2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class YouCanGoYourOwnWay {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < t; ++i) {
            int n = in.nextInt();
            String path = in.next();
            String newPath = findPath(0, 0, "", path, n, 0);

            System.out.println("Case #" + (i + 1) + ": " + newPath);
        }
    }

    private static String findPath(int x, int y, String newPath, String path, int n, int pos) {
        if (x == n - 1 && y == n - 1) return newPath;
        if (x >= n || y >= n) return null;

        if (path.charAt(pos) != 'E') {
            String p = findPath(x + 1, y, newPath + "E", path, n, pos + 1);
            if (p == null) p = findPath(x, y + 1, newPath + "S", path, n, pos + 1);
            return p;
        } else {
            String p = findPath(x, y + 1, newPath + "S", path, n, pos + 1);
            if (p == null) p = findPath(x + 1, y, newPath + "E", path, n, pos + 1);
            return p;
        }
    }
}
