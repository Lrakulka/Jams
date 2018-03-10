package round_f_2017;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Kicksort {
    private static final String DATA_FILE = "A-large";

    public static void main(String[] args) throws IOException {
        Input[] inputs = readInput();
        Output[] outputs = new Output[inputs.length];
        for (int i = 0; i < inputs.length; ++i) {
            try {
                outputs[i] = new Output(goodKicksort(inputs[i].nums));
            } catch (Exception e) {
                System.err.println("round_f_2017.Test case: " + i);
                e.printStackTrace();
            }
        }
        writeOutput(outputs);
    }

    private static boolean goodKicksort(List<Integer> nums) {
        if (nums.size() <= 1) {
            return true;
        }
        while (nums.size() > 1) {
            int piot = (nums.size() - 1) / 2;
            boolean min = false, max = false;
            for (int i = 0; i < nums.size(); ++i) {
                if (piot == i) {
                    continue;
                }
                if (nums.get(piot) > nums.get(i)) {
                    min = true;
                }
                if (nums.get(piot) <= nums.get(i)) {
                    max = true;
                }
                if (max && min) {
                    return true;
                }
            }
            nums.remove(piot);
        }
        return false;
    }

    static class Input {
        List<Integer> nums;

        public static Input create(String inputLine, BufferedReader br) throws IOException {
            Input input = new Input();
            input.nums = new LinkedList<>();
            int size = Integer.valueOf(inputLine);
            inputLine = br.readLine();
            String[] args = inputLine.split(" ");
            for (int i = 0; i < size; ++i) {
                input.nums.add(Integer.valueOf(args[i]));
            }
            return input;
        }
    }

    static class Output {
        boolean isGood;

        public Output(boolean good) {
            isGood = good;
        }

        public String getOutputMessage() {
            return isGood ? "NO" : "YES";
        }
    }

    private static void writeOutput(Output[] output) throws IOException {
        try (BufferedWriter br = new BufferedWriter(new FileWriter("src/round_f_2017/out/" + DATA_FILE + ".out"))) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < output.length; ++i) {
                builder.setLength(0);
                builder.append("Case #").append(i + 1).append(": ")
                        .append(output[i].getOutputMessage()).append(System.lineSeparator());
                br.write(builder.toString());
            }
        }
    }

    private static Input[] readInput() throws IOException {
        Input[] inputs;
        try (BufferedReader br = new BufferedReader(new FileReader("src/round_f_2017/in/" + DATA_FILE + ".in"))) {
            int T = Integer.valueOf(br.readLine());
            inputs = new Input[T];
            String inputLine;
            for (int i = 0; (inputLine = br.readLine()) != null; i++) {
                inputs[i] = Input.create(inputLine, br);
            }
            if (inputs.length != T) {
                throw new IndexOutOfBoundsException("Number of the input numbers are not equal with expected");
            }
        }
        return inputs;
    }
}

