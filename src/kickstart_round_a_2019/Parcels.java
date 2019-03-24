package kickstart_round_a_2019;

import java.util.*;
import java.io.*;

public class Parcels {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int t1 = 1; t1 <= t; ++t1) {
            int n = in.nextInt();
            int m = in.nextInt();
            boolean[][] map = new boolean[n][m];
            for (int i = 0; i < n; ++i) {
                String str = in.next();
                for (int j = 0; j < m; ++j)
                    map[i][j] = str.charAt(j) == '1';
            }

            Point3 dis = findPlace(map);
            map[dis.x][dis.y] = true;
            Point3 dis2 = findPlace(map);
            System.out.println("Case #" + t1 + ": " + getTrueDis(dis2, map));
        }
    }

    private static Integer getTrueDis(Point3 point, boolean[][] map) {
        int dis = point.dis;
        int i = point.x;
        int j = point.y;

        int trueDis = Integer.MAX_VALUE;

        for (int i1 = i - dis, j1 = j - dis; i1 <= i + dis; ++i1)
            if (isPost(i1, j1, map)) {
                int dist = Math.abs(i - i1) + Math.abs(j - j1);
                if (trueDis > dist) {
                    trueDis = dist;
                }
            }
        for (int i1 = i - dis, j1 = j + dis; i1 <= i + dis; ++i1)
            if (isPost(i1, j1, map)) {
                int dist = Math.abs(i - i1) + Math.abs(j - j1);
                if (trueDis > dist) {
                    trueDis = dist;
                }
            }
        for (int i1 = i - dis, j1 = j - dis; j1 <= j + dis; ++j1)
            if (isPost(i1, j1, map)) {
                int dist = Math.abs(i - i1) + Math.abs(j - j1);
                if (trueDis > dist) {
                    trueDis = dist;
                }
            }
        for (int i1 = i + dis, j1 = j - dis; j1 <= j + dis; ++j1)
            if (isPost(i1, j1, map)) {
                int dist = Math.abs(i - i1) + Math.abs(j - j1);
                if (trueDis > dist) {
                    trueDis = dist;
                }
            }

        return trueDis;
    }

    private static Point3 findPlace(boolean[][] map) {
        int x = -1;
        int y = -1;
        int dis = Integer.MIN_VALUE;
        for (int i = 0; i < map.length; ++i)
            for (int j = 0; j < map[0].length; ++j) {
                int currDis = getDistToPost(i, j, map);
                if (currDis > dis) {
                    dis = currDis;
                    x = i;
                    y = j;
                }
            }
        return new Point3(x, y, dis);
    }

    private static int getDistToPost(int i, int j, boolean[][] map) {
        int dis = 0;
        for (; dis < map.length || dis < map[0].length; dis++) {
            for (int i1 = i - dis, j1 = j - dis; i1 <= i + dis; ++i1)
                if (isPost(i1, j1, map)) return dis;
            for (int i1 = i - dis, j1 = j + dis; i1 <= i + dis; ++i1)
                if (isPost(i1, j1, map)) return dis;
            for (int i1 = i - dis, j1 = j - dis; j1 <= j + dis; ++j1)
                if (isPost(i1, j1, map)) return dis;
            for (int i1 = i + dis, j1 = j - dis; j1 <= j + dis; ++j1)
                if (isPost(i1, j1, map)) return dis;
        }
        return dis - 1;
    }

    private static boolean isPost(int x, int y, boolean[][] map) {
        return -1 < x && x < map.length && -1 < y && y < map[0].length && map[x][y];
    }

    private static class Point3 {
        int x;
        int y;
        int dis;

        public Point3(int x, int y, int dis) {
            this.x = x;
            this.y = y;
            this.dis = dis;
        }
    }

}