package qualification_round_2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Foregone {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < t; ++i) {
            String nStr = in.next();
            StringBuilder x = new StringBuilder(nStr);
            StringBuilder y = new StringBuilder();
            int pos = -1;
            int prevPos = -1;
            while ((pos = nStr.indexOf('4', pos + 1)) != -1) {
                if (prevPos != -1) {
                    for (int j = 0; j < pos - prevPos - 1; ++j) {
                        y.append('0');
                    }
                }
                y.append('1');
                x.setCharAt(pos, '3');

                prevPos = pos;
            }
            for (int j = 0; j < nStr.length() - prevPos - 1; ++j) {
                y.append('0');
            }
            System.out.println("Case #" + (i + 1) + ": " + x.toString() + " " + y.toString());
        }
    }

    /*
    // AAAAAAAAAAAAAAAAAAAAAAAAA "Case #" + (i +++++++ 1) + ": "
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < t; ++i) {
            int n = in.nextInt();
            String nStr = Integer.toString(n);
            StringBuilder x = new StringBuilder(nStr);
            StringBuilder y = new StringBuilder();
            int pos = -1;
            while ((pos = nStr.indexOf('4', pos + 1)) != -1) {
                x.setCharAt(pos, '3');
            }

            System.out.println("Case #" + i + ": " + x.toString() + " " + (n - Integer.valueOf(x.toString())));
        }
    }*/

   /*
   All tests pass what is problem ?
   public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
      //  int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < 1000000000; ++i) {
            String nStr = String.valueOf(i);
            if (!nStr.contains("4")) continue;
            StringBuilder x = new StringBuilder(nStr);
            StringBuilder y = new StringBuilder();
            int pos = -1;
            int prevPos = -1;
            while ((pos = nStr.indexOf('4', pos + 1)) != -1) {
                if (prevPos != -1) {
                    for (int j = 0; j < pos - prevPos - 1; ++j) {
                        y.append('0');
                    }
                }
                y.append('1');
                x.setCharAt(pos, '3');

                prevPos = pos;
            }
            for (int j = 0; j < nStr.length() - prevPos - 1; ++j) {
                y.append('0');
            }
            if (((Integer.valueOf(x.toString()) + Integer.valueOf(y.toString())) != i) || x.toString().contains("4") || y.toString().contains("4") || x.charAt(0) == '0' || y.charAt(0) == '0')
                System.out.println("Case #" + i + ": " + x.toString() + " " + y.toString());
        }
    }
*/
    /*public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < t; ++i) {
            String nStr = in.next();
            StringBuilder x = new StringBuilder(nStr);
            StringBuilder y = new StringBuilder();
            int pos = -1;
            int prevPos = -1;
            while ((pos = nStr.indexOf('4', pos + 1)) != -1) {
                if (prevPos != -1) {
                    for (int j = 0; j < pos - prevPos - 1; ++j) {
                        y.append('0');
                    }
                }
                y.append('1');
                x.setCharAt(pos, '3');

                prevPos = pos;
            }
            for (int j = 0; j < nStr.length() - prevPos - 1; ++j) {
                y.append('0');
            }
            System.out.println("Case #" + i + ": " + x.toString() + " " + y.toString());
        }
    }*/

    /*
    incorrect WAT?????
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < t; ++i) {
            int n = in.nextInt();
            for (Integer x = 0; x < n /2 + 1; ++x) {
                Integer y = n - x;
                if (!x.toString().contains("4") && !y.toString().contains("4")) {
                    System.out.println("Case #" + i + ": " + x + " " + y);
                    break;
                }
            }
        }
    }*/
}