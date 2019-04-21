package kickstart_round_b_2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DiverseSubarray {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < t; ++i) {
            int n = in.nextInt();
            int s = in.nextInt();
            int[] m = new int[n];
            for (int l = 0; l < n; ++l) {
                m[l] = in.nextInt();
            }

            System.out.println("Case #" + (i + 1) + ": " + getNumber(m, s));
        }
    }

    private static int getNumber(int[] m, int s) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < m.length; ++i) {
            int thinks = 0;
            Map<Integer, Integer> map = new HashMap<>();
            for (int j = i; j < m.length; ++j) {
                int val = map.getOrDefault(m[j], 0) + 1;
                map.put(m[j], val);
                if (val > s + 1) continue;
                thinks += (val > s) ? 1 - val : 1;
                max = Math.max(max, thinks);
            }
        }
        return max;
    }

}