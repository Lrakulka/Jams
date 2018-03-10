package round_f_2017;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Dance_Battle {
    private static final String DATA_FILE = "B-large";

    public static void main(String[] args) throws IOException {
        Input[] inputs = readInput();
        Output[] outputs = new Output[inputs.length];
        for (int i = 0; i < inputs.length; ++i) {
            try {
                outputs[i] = new Output(countHonor(inputs[i]));
            } catch (Exception e) {
                System.err.println("round_f_2017.Test case: " + i);
                e.printStackTrace();
            }
        }
        writeOutput(outputs);
    }

    private static int countHonor(Input input) {
        int honor = 0;
        input.teams.sort(Integer::compare);
        int j = input.teams.size() - 1;
        for (int i = 0; i <= j;) {
            if (input.energy > input.teams.get(i)) {
                input.energy -= input.teams.get(i);
                honor++;
                i++;
                continue;
            }
            if ((i < j) && (input.teams.get(i) < (input.teams.get(j) + input.energy)) && (honor > 0)) {
                input.energy += input.teams.get(j);
                honor--;
                j--;
                continue;
            }
            break;
        }
        return honor;
    }

    static class Input {
        List<Integer> teams;
        int energy;

        public static Input create(String inputLine, BufferedReader br) throws IOException {
            Input input = new Input();
            input.teams = new ArrayList<>();
            String[] args = inputLine.split(" ");
            input.energy = Integer.valueOf(args[0]);
            int size = Integer.valueOf(args[1]);
            inputLine = br.readLine();
            String[] teamsStr = inputLine.split(" ");
            for (int i = 0; i < size; ++i) {
                input.teams.add(Integer.valueOf(teamsStr[i]));
            }
            return input;
        }
    }

    static class Output {
        int honor;

        public Output(int honor ) {
            this.honor  = honor ;
        }

        public String getOutputMessage() {
            return String.valueOf(honor);
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
