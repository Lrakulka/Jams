package code_jam_1c_2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class AntStack {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int antNumber = in.nextInt();
            List<Integer> ants = new ArrayList<>(antNumber);
            for (int antId = 0; antId < antNumber; ++antId) {
                ants.add(in.nextInt());
            }
            Collections.reverse(ants);
            getMaxStack(ants, 0, 0, Integer.MAX_VALUE);
            System.out.println("Case #" + i + ": " + maxStack);
        }
    }

    private static int maxStack = 0;
    private static void getMaxStack(List<Integer> ants, int currPos, int currStack, int maxWeight) {
        if (maxStack < currStack) {
            maxStack = currStack;
        }
        for (; currPos < ants.size(); ++currPos) {
            int currWeightAnt = ants.get(currPos);
            if (currWeightAnt <= maxWeight) {
                maxWeight -= currWeightAnt;
                maxWeight = Math.min(maxWeight, currWeightAnt * 6);

                getMaxStack(ants, currPos + 1, currStack + 1, maxWeight);
            }
        }
    }
}
