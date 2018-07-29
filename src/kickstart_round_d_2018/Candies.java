package kickstart_round_d_2018;

import wrapper.ProblemExecutor;
import wrapper.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Candies extends ProblemExecutor<Candies.CandiesTest> {

    private static final Function<Iterator<String>, CandiesTest> READ_CONVERTER = stringIterator -> {
        String dataLine = stringIterator.next();
        final CandiesTest candiesTest = new CandiesTest();
        String[] params = dataLine.split(" ");
        candiesTest.N = Long.valueOf(params[0]);
        candiesTest.maxOdd_O = Long.valueOf(params[1]);
        candiesTest.maxSweetness_D = Long.valueOf(params[2]);
        dataLine = stringIterator.next();
        candiesTest.candiesSweetness = new ArrayList<>();
        params = dataLine.split(" ");
        long X1 = Long.valueOf(params[0]);
        long X2 = Long.valueOf(params[1]);
        long A = Long.valueOf(params[2]);
        long B = Long.valueOf(params[3]);
        long C = Long.valueOf(params[4]);
        long M = Long.valueOf(params[5]);
        long L = Long.valueOf(params[6]);
        for (long i = 0; i < candiesTest.N; ++i) {
            long S = X1 + L;
            candiesTest.candiesSweetness.add(S);
            long c = X2;
            X2 = (A * X2 + B * X1 + C) % M;
            X1 = c;
        }
        return candiesTest;
    };
    private static final Function<Object, String> WRITE_CONVERTER = o -> Objects.isNull(o) ? "IMPOSSIBLE" : o.toString();

    static class CandiesTest implements Test {
        long N;
        long maxOdd_O;
        long maxSweetness_D;

        public List<Long> candiesSweetness;
    }

    public static void main(String... args) {
        String[] inputs = {"A-large-practice"};
        new Candies().execute(inputs);
    }

    @Override
    protected Function<Iterator<String>, CandiesTest> getReadConverter() {
        return READ_CONVERTER;
    }

    @Override
    protected Function<Object, String> getWriteConverter() {
        return WRITE_CONVERTER;
    }

    @Override
    protected Long getAnswer(CandiesTest test) {
        if (test.maxSweetness_D < -1) {
            return null;
        }

        long max = getMaxWindowValue(test.candiesSweetness, test.maxSweetness_D, test.maxOdd_O);

        return (max == Long.MIN_VALUE) ? null : max;
    }

    private static long getMaxWindowValue(List<Long> candiesSweetness, long maxSweetness_d, long odd) {
        long max_so_far = Long.MIN_VALUE, max_ending_here = 0;

        for (int i = 0, windowSize = 0, currOdd = 0; i < candiesSweetness.size(); i++) {
            max_ending_here = max_ending_here + candiesSweetness.get(i);
            windowSize++;

            if (candiesSweetness.get(i) % 2 == 1) {
                currOdd++;
            }
            if (currOdd > odd) {
                for (int start = i - windowSize + 1; ; start++) {
                    max_ending_here -= candiesSweetness.get(start);
                    windowSize--;
                    if (candiesSweetness.get(start) % 2 == 1) break;
                }
                currOdd--;
            }
            if (max_ending_here > maxSweetness_d) {
                max_ending_here -= candiesSweetness.get(i - windowSize + 1);
                windowSize--;
            }

            if (max_so_far < max_ending_here)
                max_so_far = max_ending_here;

            if (max_ending_here < 0) {
                windowSize = 0;
                max_ending_here = 0;
            }
        }
        return max_so_far;
    }

}
