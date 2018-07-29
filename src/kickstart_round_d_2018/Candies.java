package kickstart_round_d_2018;

import util.InputData;
import util.OutputData;
import util.ProblemDataIO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Candies {
    static class Candies_InputData implements InputData {
        List<Test> tests;

        static class Test {
            long N;
            long maxOdd_O;
            long maxSweetness_D;

            public List<Long> candiesSweetness;
        }

        @Override
        public void setParams(String[] params) {
            tests = new ArrayList<>(Integer.valueOf(params[0]));
        }

        private boolean gettingTest;
        @Override
        public void fillData(String dataLine) {
            if (!gettingTest) {
                Test test = new Test();
                String[] params = dataLine.split(" ");
                test.N = Long.valueOf(params[0]);
                test.maxOdd_O = Long.valueOf(params[1]);
                test.maxSweetness_D = Long.valueOf(params[2]);
                tests.add(test);
                gettingTest = true;
            } else {
                Test test = tests.get(tests.size() - 1);
                test.candiesSweetness = new ArrayList<>();
                String[] params = dataLine.split(" ");
                long X1 = Long.valueOf(params[0]);
                long X2 = Long.valueOf(params[1]);
                long A = Long.valueOf(params[2]);
                long B = Long.valueOf(params[3]);
                long C = Long.valueOf(params[4]);
                long M = Long.valueOf(params[5]);
                long L = Long.valueOf(params[6]);
                for (long i = 0; i < test.N; ++i) {
                    long S = X1 + L;
                    test.candiesSweetness.add(S);
                    long c = X2;
                    X2 = (A * X2 + B * X1 + C) % M;
                    X1 = c;
                }
                gettingTest = false;
            }
        }
    }

    static class Candies_OutputData implements OutputData {

        @Override
        public String getOutput(Object data) {
            if (Objects.isNull(data)) {
                return "IMPOSSIBLE";
            } else {
                return data.toString();
            }
        }
    }

    public static void main(String... args) {
        String[] inputs = {"A-small-practice.in"};
        for (String in : inputs) {
            ProblemDataIO dataIO = new ProblemDataIO(new Candies.Candies_InputData(),
                    new Candies.Candies_OutputData(),"kickstart_round_d_2018", in);
            Candies_InputData inputData = (Candies_InputData) dataIO.readData();
            List<Long> numbs = getAnswers(inputData.tests);
            dataIO.writeOutput(numbs);
        }
    }

    private static List<Long> getAnswers(List<Candies_InputData.Test> tests) {
        return tests.stream().map(Candies::getAnswer).collect(Collectors.toList());
    }

    private static Long getAnswer(Candies_InputData.Test test) {
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

    private static boolean isLessOdd(List<Long> candiesSweetness, int start, int end, long oddMaxNumber) {
        long oddCount = 0;
        for (long i = start; i <= end; ++i) {
            if (candiesSweetness.get((int) i) % 2 == 1) {
                oddCount++;
            }
            if (oddCount > oddMaxNumber) {
                return false;
            }
        }
        return true;
    }
}
