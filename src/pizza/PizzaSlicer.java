package pizza; // https://en.wikipedia.org/wiki/Java_package

import util.InputData;
import util.OutputData;
import util.ProblemDataIO;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PizzaSlicer {

    private static final char CUT_CELL = 'C';
    private static final char MUSHROOM = 'M';
    private static final char TOMATO = 'T';

    static class PizzaInputData implements InputData {
        char[][] pizza;
        int minEachIngrid;
        int maxSliceSize;

        public void setParams(String[] params) {
            minEachIngrid = Integer.valueOf(params[2]);
            maxSliceSize = Integer.valueOf(params[3]);
            int row = Integer.valueOf(params[0]);
            int column = Integer.valueOf(params[1]);
            pizza = new char[row][column];
        }

        private int currRow;
        @Override // https://stackoverflow.com/questions/94361/when-do-you-use-javas-override-annotation-and-why
        public void fillData(String sCurrentLine) {
            pizza[currRow] = sCurrentLine.toCharArray();
            currRow++;
        }
    }

    static class PizzaOutputData implements OutputData {

        @Override
        public String getOutput(Object o) {
            List<Slice> slices = (List<Slice>) o;
            StringBuilder builder = new StringBuilder(String.valueOf(slices.size()));
            for (Slice slice : slices) {
                builder.append(System.lineSeparator()).append(slice.toString());
            }
            return builder.toString();
        }

    }

    public static void main(String... args) {
        ProblemDataIO dataIO = new ProblemDataIO(new PizzaInputData(), new PizzaOutputData(), "pizza", "big.in");
        PizzaInputData inputData = (PizzaInputData) dataIO.readData();
        List<Slice> slices = slicePizza(inputData.pizza, inputData.minEachIngrid, inputData.maxSliceSize);
        dataIO.writeData(slices);
    }
// the implementation of the List of slices
    private static List<Slice> slicePizza(char[][] pizza, Integer minEachIngrid, Integer maxSliceSize) {
        AbstractQueue<Slice> slicesQuery = new PriorityQueue<>(Comparator.comparingInt(o -> o.square));
        List<Slice> slices = new ArrayList<>();
        for (int i = 0; i < pizza.length; ++i) {
            for (int j = 0; j < pizza[0].length; ++j) {
                if (pizza[i][j] == CUT_CELL) {
                    continue;
                }
                slicesQuery.clear();
                slicesQuery.add(new Slice(new Point(i, j), new Point(i, j)));
                do {
                    Slice possibleSlice = slicesQuery.poll();
                    if (isSliceEnoughDeliciousForUs(possibleSlice, pizza, minEachIngrid)) {
                        slices.add(possibleSlice);
                        cutSliceFromPizza(pizza, possibleSlice);
                        break;
                    }
                    addLargeSlices(possibleSlice, pizza, maxSliceSize, slicesQuery);
                } while (!slicesQuery.isEmpty());
            }
        }
        return maximizeSlices(slices, pizza, maxSliceSize);
    }

    private static List<Slice> maximizeSlices(List<Slice> slices, char[][] pizza, Integer maxSliceSize) {
        Queue<Slice> slicesQuery = new LinkedList<>();
        List<Slice> maximizeSlices = new ArrayList<>();
        for (Slice slice : slices) {
            Slice biggerSlice = slice;
            slicesQuery.add(biggerSlice);
            do {
                Slice possibleSlice = slicesQuery.poll();
                if (biggerSlice.square < possibleSlice.square) {
                    biggerSlice = possibleSlice;
                }
                addLargeSlices(possibleSlice, pizza, maxSliceSize, slicesQuery);
            } while (!slicesQuery.isEmpty());

            if (!slice.equals(biggerSlice)) {
                cutSliceFromPizza(pizza, biggerSlice);
            }
            maximizeSlices.add(biggerSlice);
        }
        return maximizeSlices;
    }
// adding more slices
    private static void addLargeSlices(Slice slice, char[][] pizza, Integer maxSliceSize,
                                       Queue<Slice> slicesQuery) {
        Slice sliceLargeRight = new Slice(slice.start, new Point(slice.end.x + 1, slice.end.y));
        Slice sliceLargeBottom = new Slice(slice.start, new Point(slice.end.x, slice.end.y + 1));
        if (isSlicePossible(sliceLargeRight, slice, pizza, maxSliceSize)) {
            slicesQuery.add(sliceLargeRight);
        }
        if (isSlicePossible(sliceLargeBottom, slice, pizza, maxSliceSize)) {
            slicesQuery.add(sliceLargeBottom);
        }
    }
// checking whether it is possible to cut a slice out
    private static boolean isSlicePossible(Slice slice, Slice prevSlice, char[][] pizza, Integer maxSliceSize) {
        return slice.end.y >= 0 && slice.end.y < pizza[0].length
                && slice.end.x >= 0 && slice.end.x < pizza.length
                && slice.square <= maxSliceSize && isSliceSolid(pizza, slice, prevSlice);
    }
// checking the "all-in-one" condition (rectangular)
    private static boolean isSliceSolid(char[][] pizza, Slice slice, Slice prevSlice) {
        if (slice.end.x == prevSlice.end.x) {
            for (int i = slice.start.x; i <= slice.end.x; ++i) {
                if (pizza[i][slice.end.y] == CUT_CELL) {
                    return false;
                }
            }
        } else {
            for (int i = slice.start.y; i <= slice.end.y; ++i) {
                if (pizza[slice.end.x][i] == CUT_CELL) {
                    return false;
                }
            }
        }
        return true;
    }
// taking a slice from the pizza
    private static void cutSliceFromPizza(char[][] pizza, Slice possibleSlice) {
        for (int i = possibleSlice.start.x; i <= possibleSlice.end.x; ++i) {
            for (int j = possibleSlice.start.y; j <= possibleSlice.end.y; ++j) {
                pizza[i][j] = CUT_CELL;
            }
        }
    }
// cheking mushrooms and tomatos in the specific slice
    private static boolean isSliceEnoughDeliciousForUs(Slice possibleSlice, char[][] pizza, Integer minEachIngrid) {
        int tomatoCount = 0;
        int mushroomCount = 0;
        for (int i = possibleSlice.start.x; i <= possibleSlice.end.x; ++i) {
            for (int j = possibleSlice.start.y; j <= possibleSlice.end.y; ++j) {
                switch (pizza[i][j]) {
                    case TOMATO: {
                        tomatoCount++;
                        break;
                    }
                    case MUSHROOM: {
                        mushroomCount++;
                        break;
                    }
                    default: throw new IllegalArgumentException("i=" + i + " j=" + j + " value=" + pizza[i][j]);
                }
            }
        }
        return tomatoCount >= minEachIngrid && mushroomCount >= minEachIngrid;
    }
// slice's parameters
    private static class Slice {
        Point start;
        Point end;
        int square;

        Slice(Point start, Point end) {
            this.start = start;
            this.end = end;
            square = (Math.abs(start.x - end.x) + 1) * (Math.abs(start.y - end.y) + 1);
        }

        @Override
        public String toString() {
            return "" + start.x + ' ' + start.y + ' ' + end.x + ' ' + end.y;
        }
    }
}
