package kickstart_round_b_2018;

import util.InputData;
import util.OutputData;
import util.ProblemDataIO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Sherlock_and_the_Bit_Strings {
    static class Sherlock_and_the_Bit_StringsInputData implements InputData {
        List<Parameter> parameters;

        @Override
        public void setParams(String[] params) {
            parameters = new ArrayList<>();
        }

        long rules = 0;
        Parameter parameter;
        @Override
        public void fillData(String dataLine) {
            String[] params = dataLine.split(" ");
            if (rules == 0) {
                parameter = new Parameter();
                parameter.N = Integer.valueOf(params[0]);
                parameter.P = Long.valueOf(params[2]);
                rules = Integer.valueOf(params[1]);
                parameters.add(parameter);
            } else {
                rules--;
                Parameter.Rule rule = new Parameter.Rule();
                rule.A = Integer.valueOf(params[0]);
                rule.B = Integer.valueOf(params[1]);
                rule.C = Integer.valueOf(params[2]);
                parameter.rules.add(rule);
            }

        }
    }

    static class Sherlock_and_the_Bit_StringsOutputData implements OutputData {

        @Override
        public String getOutput(Object data) {
            List<String> strs = (List<String>) data;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < strs.size(); ++i) {
                builder.append("Case #").append(i + 1).append(": ").append(strs.get(i)).append("\n");
            }
            return builder.toString();
        }
    }

    public static void main(String... args) {
        String[] inputs = {"B-small-attempt0.in"};
        for (String in : inputs) {
            ProblemDataIO dataIO = new ProblemDataIO(new Sherlock_and_the_Bit_StringsInputData(),
                    new Sherlock_and_the_Bit_StringsOutputData(),
                    "kickstart_round_b_2018", in);
            Sherlock_and_the_Bit_StringsInputData inputData = (Sherlock_and_the_Bit_StringsInputData) dataIO.readData();
            List<String> numbs = getBitStrings(inputData.parameters);
            dataIO.writeData(numbs);
        }
    }

    private static List<String> getBitStrings(List<Parameter> parameters) {
        List<String> result = new ArrayList<>();
        for (Parameter parameter : parameters) {
            result.add(getBitString(parameter));
        }
        return result;
    }

    private static String getBitString(Parameter parameter) {
        List<String> bitStrings = new LinkedList<>();
        P_ = parameter.P;
        isStop = false;
        genBitString(bitStrings, parameter.N, "", parameter.rules);
        return bitStrings.get(0);
    }

    private static void useRule(List<String> bitStrings, List<Parameter.Rule> rules, long P) {
        Iterator<String> iterator = bitStrings.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (isNonValid(str, rules)) {
                iterator.remove();
            } else {
                if (P == 1) {
                    return;
                }
                P--;
                iterator.remove();
            }
        }
    }

    private static boolean isNonValid(String str, List<Parameter.Rule> rules) {
        for (Parameter.Rule rule : rules) {
            long sum = getBitSum(str, rule.A, rule.B);
            if (sum != rule.C) {
                return true;
            }
        }
        return false;
    }

    private static long getBitSum(String str, int a, int b) {
        long sum = 0;
        for (int i = a - 1; i < b; ++i) {
            if (str.charAt(i) == '1') {
                sum++;
            }
        }
        return sum;
    }

    static long P_;
    static boolean isStop;
    private static void genBitString(List<String> bitStrings, int n, String str, List<Parameter.Rule> rules) {
        if (isStop) return;

        if (n == 0) {

            if (!isNonValid(str, rules)) {
                P_--;
            }

            if (P_ == 0) {
                bitStrings.add(str);
                isStop = true;
            }
            return;
        }
        n--;
        genBitString(bitStrings, n, str + "0", rules);
        genBitString(bitStrings, n, str + "1", rules);
    }

    private static class Parameter {
        int N;
        long P;
        List<Rule> rules = new ArrayList<>();

        private static class Rule {
            int A;
            int B;
            int C;
        }
    }
}
