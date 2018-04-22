package kickstart_round_b_2018;

import util.InputData;
import util.OutputData;
import util.ProblemDataIO;

import java.util.ArrayList;
import java.util.List;

public class No_Nine {
    static class No_NineInputData implements InputData {
        List<Long> l;
        List<Long> f;

        @Override
        public void setParams(String[] params) {
            l = new ArrayList<>();
            f = new ArrayList<>();
        }

        @Override
        public void fillData(String dataLine) {
            String[] params = dataLine.split(" ");
            f.add(Long.valueOf(params[0]));
            l.add(Long.valueOf(params[1]));
        }
    }

    static class No_NineOutputData implements OutputData {

        @Override
        public String getOutput(Object data) {
            List<Long> numbs = (List<Long>) data;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < numbs.size(); ++i) {
                builder.append("Case #").append(i + 1).append(": ").append(numbs.get(i)).append("\n");
            }
            return builder.toString();
        }
    }

    public static void main(String... args) {
        String[] inputs = {"test.in"};
        for (String in : inputs) {
            ProblemDataIO dataIO = new ProblemDataIO(new No_NineInputData(), new No_NineOutputData(),
                    "kickstart_round_b_2018", in);
            No_NineInputData inputData = (No_NineInputData) dataIO.readData();
            List<Integer> numbs = countNumbs(inputData.f, inputData.l);
            dataIO.writeData(numbs);
        }
    }

    private static List<Integer> countNumbs(List<Long> f, List<Long> l) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < f.size(); ++i) {
            int numb = 0;
            for (long F = f.get(i), L = l.get(i); F <= L; ++F) {
                if (!(isContainsNine(F) || F % 9 == 0)) {
                    numb++;
                    continue;
                }
            }
            result.add(numb);
        }
        return result;
    }

    private static boolean isContainsNine(long f) {
        while (f != 0) {
            if (f % 10 == 9) {
                return true;
            }
            f /= 10;
        }
        return false;
    }
}