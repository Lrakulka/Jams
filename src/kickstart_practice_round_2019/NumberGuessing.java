import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class NumberGuessing {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < t; ++i) {
            int a = in.nextInt() + 1;
            int b = in.nextInt();
            int n = in.nextInt();
            for(;;) {
                int mid = (a + b) >>> 1;
                System.out.println(mid);
                String answer = in.next();
                if (answer.equals("CORRECT"))
                    break;
                if (answer.equals("TOO_SMALL"))
                    a = mid + 1;
                if (answer.equals("TOO_BIG"))
                    b = mid - 1;
                if (answer.equals("WRONG_ANSWER"))
                    throw new IllegalArgumentException();
            }
        }
    }
}

