package kickstart_round_b_2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < t; ++i) {
            int n = in.nextInt();
            int q = in.nextInt();
            String line = in.next();
            char[] blocks = line.toCharArray();
            int ans = 0;
            for (int l = 0; l < q; ++l) {
                int start = in.nextInt() - 1;
                int end = in.nextInt() - 1;
                ans += canAnswer(blocks, start, end) ? 1 : 0;
            }

            System.out.println("Case #" + (i + 1) + ": " + ans);
        }
    }

    private static boolean canAnswer(char[] blocks, int start, int end) {
        int[] m = new int[28];
        for (int i = start; i <= end; ++i) {
            m[blocks[i] - 'A']++;
        }
        int odd = 0;
        for (int n : m) {
            if (n % 2 != 0) odd++;
        }
        return odd == 0 || odd == 1;
    }
        
}
