package pizza;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PizzaSlicer {

    private static final char CUT_CELL = 'C';
    private static final char MUSHROOM = 'M';
    private static final char TOMATO = 'T';

    public static void main(String... args) {
        Character[][] pizza = {
                {'T', 'T', 'T', 'T', 'T'},
                {'T', 'M', 'M', 'M', 'T'},
                {'T', 'T', 'T', 'T', 'T'},
        };
        Integer minEachIngrid = 1;
        Integer maxSliceSize = 6;
        List<Slice> slices = slicePizza(pizza, minEachIngrid, maxSliceSize);
        for (Slice slice : slices) {
            System.out.println(slice.toString());
        }
    }

    private static List<Slice> slicePizza(Character[][] pizza, Integer minEachIngrid, Integer maxSliceSize) {
        AbstractQueue<Slice> slicesQuery = new PriorityQueue<>(Comparator.comparingDouble(o -> o.square));
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
                    if (isSliceEnoughDeliciousForAs(possibleSlice, pizza, minEachIngrid)) {
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

    private static List<Slice> maximizeSlices(List<Slice> slices, Character[][] pizza, Integer maxSliceSize) {
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

    private static void addLargeSlices(Slice slice, Character[][] pizza, Integer maxSliceSize,
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

    private static boolean isSlicePossible(Slice slice, Slice prevSlice, Character[][] pizza, Integer maxSliceSize) {
        return slice.end.y >= 0 && slice.end.y < pizza[0].length
                && slice.end.x >= 0 && slice.end.x < pizza.length
                && slice.square <= maxSliceSize && isSliceSolid(pizza, slice, prevSlice);
    }

    private static boolean isSliceSolid(Character[][] pizza, Slice slice, Slice prevSlice) {
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

    private static void cutSliceFromPizza(Character[][] pizza, Slice possibleSlice) {
        for (int i = possibleSlice.start.x; i <= possibleSlice.end.x; ++i) {
            for (int j = possibleSlice.start.y; j <= possibleSlice.end.y; ++j) {
                pizza[i][j] = CUT_CELL;
            }
        }
    }

    private static boolean isSliceEnoughDeliciousForAs(Slice possibleSlice, Character[][] pizza, Integer minEachIngrid) {
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

    private static class Slice {
        Point start;
        Point end;
        Double square;

        Slice(Point start, Point end) {
            this.start = start;
            this.end = end;
            square = Math.sqrt(Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2));
        }

        @Override
        public String toString() {
            return "" + start.x + ' ' + start.y + ' ' + end.x + ' ' + end.y;
        }
    }
}
