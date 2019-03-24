package kickstart_round_a_2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Contention {
    private static class Book implements Comparable{
        int l;
        int r;

        public Book(int l, int r) {
            this.l = l;
            this.r = r;
        }

        @Override
        public int compareTo(Object o) {
            Book b = (Book) o;
            return Integer.compare(this.r - this.l, b.r - b.l);
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < t; ++i) {
            int n = in.nextInt();
            int q = in.nextInt();

            PriorityQueue<Book> pQueue = new PriorityQueue<>();
            for (int j = 0; j < q; ++j) {
                int l = in.nextInt();
                int r = in.nextInt();
                pQueue.add(new Book(l, r));
            }

            boolean[] booking = new boolean[n];
            int prevAssign = Integer.MAX_VALUE;
            for (;!pQueue.isEmpty();) {
                Book b = pQueue.poll();
                int l = b.l;
                int r = b.r;
                int assign = 0;
                for (int p = l; p <= r; ++p) {
                    if (!booking[p - 1]) {
                        booking[p - 1] = true;
                        assign++;
                    }
                }
                if (prevAssign > assign)
                    prevAssign = assign;
            }
            System.out.println("Case #" + i + ": " + prevAssign);
        }
    }
}