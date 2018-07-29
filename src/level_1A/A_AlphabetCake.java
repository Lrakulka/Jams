import java.io.*;

/**
 * Created by List on 4/22/2017.
 */
public class A_AlphabetCake {
    private static final String DATA_FILE = "A-large-practice";

    public static void main(String[] args) throws IOException {
        Cake[] cakes = readInput();
        char prevCell;
        for (Cake cake : cakes) {
            // Horizontal shift
            for (int i = 0; i < cake.getCells().length; ++i) {
                // Right shift
                prevCell = Character.MIN_VALUE;
                for (int j = 0; j < cake.getCells()[0].length; ++j) {
                    prevCell = calculate(cake, i, j, prevCell);
                }
                // Left shift
                prevCell = Character.MIN_VALUE;
                for (int j = cake.getCells()[0].length - 1; j > -1; --j) {
                    prevCell = calculate(cake, i, j, prevCell);
                }
            }
            // Vertical shift
            for (int j = 0; j < cake.getCells()[0].length; ++j) {
                // Bottom shift
                prevCell = Character.MIN_VALUE;
                for (int i = 0; i < cake.getCells().length; ++i) {
                    prevCell = calculate(cake, i, j, prevCell);
                }
                // Ceil shift
                prevCell = Character.MIN_VALUE;
                for (int i = cake.getCells().length - 1; i > -1; --i) {
                    prevCell = calculate(cake, i, j, prevCell);
                }
            }
        }
        writeOutput(cakes);
    }

    private static char calculate(Cake cake, int i, int j, char prevCell) {
        if ((cake.getCells()[i][j] == '?') && (prevCell != Character.MIN_VALUE)) {
            cake.setCell(i, j, prevCell);
        }
        if ((cake.getCells()[i][j] != '?') && (prevCell != Character.MIN_VALUE)) {
            prevCell = Character.MIN_VALUE;
        }
        if ((cake.getCells()[i][j] != '?') && (prevCell == Character.MIN_VALUE)) {
            prevCell = cake.getCells()[i][j];
        }
        return prevCell;
    }

    static class Cake {
        private char[][] cells;

        public Cake(final int n, final int m) {
            cells = new char[n][m];
        }

        public void setCell(final int n, final int m, final char value) {
            cells[n][m] = value;
        }

        public char[][] getCells() {
            return cells;
        }

        public void setCells(char[][] cells) {
            this.cells = cells;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < cells.length; ++i) {
                builder.append(System.lineSeparator());
                builder.append(cells[i]);
            }
            return builder.toString();
        }

        public static Cake create(String inputLine, BufferedReader br) throws IOException {
            int R, C;
            String[] params = inputLine.split(" ");
            R = Integer.valueOf(params[0]);
            C = Integer.valueOf(params[1]);
            Cake cake = new Cake(R, C);
            for (int i = 0; i < R; ++i) {
                System.arraycopy(br.readLine().toCharArray(), 0, cake.getCells()[i], 0, C);
            }
            return cake;
        }
    }

    private static void writeOutput(Cake[] output) throws IOException {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(DATA_FILE + ".out"))) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < output.length; ++i) {
                builder.setLength(0);
                builder.append("Case #").append(i + 1).append(": ")
                        .append(output[i].toString()).append(System.lineSeparator());
                br.write(builder.toString());
            }
        }
    }

    private static Cake[] readInput() throws IOException {
        Cake[] inputs;
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE + ".in"))) {
            int T = Integer.valueOf(br.readLine());
            inputs = new Cake[T];
            String inputLine;
            for (int i = 0; (inputLine = br.readLine()) != null; i++) {
                inputs[i] = Cake.create(inputLine, br);
            }
            if (inputs.length != T) {
                throw new IndexOutOfBoundsException("Number of the input numbers are not equal with expected");
            }
        }
        return inputs;
    }
}
