package round_f_2017;

import java.io.*;
import java.util.Arrays;

import static java.lang.Math.min;
//INCORRECT
public class Catch_Them_All {
    private static final int MAX = (int) (Math.pow(10, 9) + 1);
    private static final String DATA_FILE = "C-small-practice";

    public static void main(String[] args) throws IOException {
        System.out.println(convert("ABC", 2));
        Input[] inputs = readInput();
        Output[] outputs = new Output[inputs.length];
        for (int i = 0; i < inputs.length; ++i) {
            try {
                outputs[i] = new Output(estimTimeToCatch(inputs[i]));
            } catch (Exception e) {
                System.err.println("round_f_2017.Test case: " + i);
                e.printStackTrace();
            }
        }
        writeOutput(outputs);
    }

    public static String convert(String s, int numRows) {
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0 ;i < rows.length; ++i) {
            rows[i] = new StringBuilder();
        }
        char[] chars = s.toCharArray();
        int inc = 1;
        for (int i = 0, j = 0; i < chars.length; ++i) {
            rows[j].append(chars[i]);
            j += inc;
            if ((j >= numRows) || (j <= -1)) {
                inc *= -1;
                j += inc * 2;
            }
        }
        for (int i = 1; i < rows.length; ++i) {
            rows[0].append(rows[i]);
        }
        return rows[0].toString();
    }

    private static double estimTimeToCatch(Input input) {
        int[][] timeToCity = getTime(input.matrix);
        double[] averageTime = getAverage(timeToCity);
        double size = 0;
        int[] cityL = new int[input.matrix.length];
        int[] newCityL = new int[input.matrix.length];

        cityL[0] = 1;
        for (int i = 0; i < input.pockemonNumber; ++i) {
            double step = 0;
            for (int j = 0; j < averageTime.length; ++j) {
                step += averageTime[j] * cityL[j];
            }
            size += step / Math.pow(averageTime.length - 1, i);
            for (int p = 0; p < cityL.length; ++p) {
                newCityL[p] = 0;
                for (int p2 = 0; p2 < cityL.length; ++p2) {
                    if (p != p2) {
                        newCityL[p] += cityL[p2];
                    }
                }
            }
            cityL = newCityL.clone();
        }
        return size;
    }

    private static double[] getAverage(int[][] timeToCity) {
        double[] averageTime = new double[timeToCity.length];
        for (int i = 0; i < timeToCity.length; ++i) {
            for (int j = 0; j < timeToCity.length; ++j) {
                if (i != j) {
                    averageTime[i] += timeToCity[i][j];
                }
            }
            averageTime[i] /= (timeToCity.length - 1);
        }
        return averageTime;
    }

    private static int[][] getTime(int[][] matrix) {
        for(int k = 0; k < matrix.length; k++) {
            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix.length; j++) {
                    matrix[i][j] = min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                }
            }
        }

        return matrix;
    }

    static class Input {
        int pockemonNumber;
        int[][] matrix;

        public static Input create(String inputLine, BufferedReader br) throws IOException {
            Input input = new Input();
            String[] args = inputLine.split(" ");
            input.pockemonNumber = Integer.valueOf(args[2]);
            int cityNumber = Integer.valueOf(args[0]);
            int roadNumber = Integer.valueOf(args[1]);
            input.matrix = new int[cityNumber][cityNumber];
            for (int[] row : input.matrix) {
                Arrays.fill(row, MAX);
            }
            for (int i = 0; i < roadNumber; ++i) {
                inputLine = br.readLine();
                String[] argsRoad = inputLine.split(" ");
                input.matrix[Integer.valueOf(argsRoad[0]) - 1][Integer.valueOf(argsRoad[1]) - 1] = Integer.valueOf(argsRoad[2]);
                input.matrix[Integer.valueOf(argsRoad[1]) - 1][Integer.valueOf(argsRoad[0]) - 1] = Integer.valueOf(argsRoad[2]);
            }
            return input;
        }
    }

    static class Output {
        double time;

        public Output(double time) {
            this.time = time;
        }

        public String getOutputMessage() {
            return String.format("%.6f", time);
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
