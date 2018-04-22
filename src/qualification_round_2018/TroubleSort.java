package qualification_round_2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class TroubleSort {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int numberKol = in.nextInt();
            final List<Integer> evenNumbers = new ArrayList<>(numberKol / 2 + 1);
            final List<Integer> oddNumbers = new ArrayList<>(numberKol / 2 + 1);
            for (int j = 0; j < numberKol; ++j) {
                oddNumbers.add(in.nextInt());
                ++j;
                if (j < numberKol) {
                    evenNumbers.add(in.nextInt());
                }
            }
            System.out.println("Case #" + i + ": " + getValue(getWrong(oddNumbers, evenNumbers)));
        }
    }

    private static Integer getWrong(List<Integer> oddNumbers, List<Integer> evenNumbers) {
        Collections.sort(oddNumbers);
        Collections.sort(evenNumbers);
        int length = Math.min(evenNumbers.size(), oddNumbers.size());
        for (int i = 0; i < length; ++i) {
            if (oddNumbers.get(i) > evenNumbers.get(i)) {
                return i + i;
            }
            if ((i + 1 < oddNumbers.size()) && (oddNumbers.get(i + 1) < evenNumbers.get(i))) {
                return i + i + 1;
            }
        }
        return null;
    }

    private static String getValue(Integer position) {
        return Objects.isNull(position) ? "OK" : position.toString();
    }
}
