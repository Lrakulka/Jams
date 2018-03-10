package round_f_2017;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Eat_Cake {
    private static final String DATA_FILE = "D-large";
    private static Set<Double> cakes = new HashSet<>(100);

    public static void main(String[] args) throws IOException {
        for (int i = 1; i < 101; ++i) {
            cakes.add(Math.pow(i, 2));
        }
        Input[] inputs = readInput();
        Output[] outputs = new Output[inputs.length];
        for (int i = 0; i < inputs.length; ++i) {
            try {
                outputs[i] = new Output(getCakeNumber(inputs[i].cakeSize));
            } catch (Exception e) {
                System.err.println("round_f_2017.Test case: " + i);
                e.printStackTrace();
            }
        }
        writeOutput(outputs);
    }

    private static int getCakeNumber(double sizeToEat) {
        if (cakes.contains(sizeToEat)) {
            return 1;
        }
        if (sizeToEat < 4) {
            return (int) sizeToEat;
        }
        int seq = (int) Math.pow(sizeToEat, 0.5);
        int seqCake = Integer.MAX_VALUE;
        for (int i = 2; i < seq + 1; ++i) {
            if (cakes.contains(sizeToEat / i)) {
                seqCake = i;
            }
        }
        if (seqCake == 2) {
            return seqCake;
        }
        int count = 1 + getCakeNumber((int) (sizeToEat - Math.pow(seq, 2)));
        int count2 = 1 + getCakeNumber((int) (sizeToEat - Math.pow(seq - 1, 2)));
        count = count < count2 ? count : count2;
        return seqCake < count ? seqCake : count;
    }

    static class Input {
        int cakeSize;

        public static Input create(String inputLine, BufferedReader br) throws IOException {
            Input input = new Input();
            input.cakeSize = Integer.valueOf(inputLine);
            return input;
        }
    }

    static class Output {
        int cakes;

        public Output(int cakes) {
            this.cakes = cakes;
        }

        public String getOutputMessage() {
            return String.valueOf(cakes);
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
