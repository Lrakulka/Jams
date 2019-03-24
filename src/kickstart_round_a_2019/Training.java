package kickstart_round_a_2019;

import java.util.*;
import java.io.*;

public class Training {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < t; ++i) {
            int n = in.nextInt();
            int p = in.nextInt();
            Map<Integer, Integer> players = new HashMap<>();
            for (int j = 0; j < n; ++j) {
                int player = in.nextInt();
                players.put(player, players.getOrDefault(player, 0) + 1);
            }

            List<Integer> levels = new ArrayList<>(players.keySet());
            levels.sort(Integer::compareTo);

            int minHours = Integer.MAX_VALUE;
            for (int k = 1; k < levels.size(); ++k) {
                Integer level = levels.get(k);
                int train = p;
                int currMin = 0;
                for (int l = k; train != 0 && l > -1; --l) {
                    int minP = Math.min(train, players.get(levels.get(l)));
                    currMin += minP * (level - levels.get(l));
                    train -= minP;
                }
                if (train == 0)
                    minHours = Math.min(currMin, minHours);
            }

            System.out.println("Case #" + i + ": " + minHours);
        }
    }
}