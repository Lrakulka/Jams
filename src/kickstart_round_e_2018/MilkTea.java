package kickstart_round_e_2018;

import wrapper.ProblemExecutor;

import java.util.*;
import java.util.function.Function;

public class MilkTea extends ProblemExecutor<MilkTea.MilkTeaTest> {

    static class MilkTeaTest implements wrapper.Test {
        long P;
        String[] friendsOrder;
        Set<String> restrictedOrders;
    }

    @Override
    protected Function<Iterator<String>, MilkTeaTest> getReadConverter() {
        return strIterator -> {
            MilkTeaTest test = new MilkTeaTest();
            String[] strs = strIterator.next().split(" ");
            int N = Integer.valueOf(strs[0]);
            int M = Integer.valueOf(strs[1]);
            test.P = Long.valueOf(strs[2]);
            test.friendsOrder = new String[N];
            for (int i = 0; i < N; ++i) {
                test.friendsOrder[i] = strIterator.next();
            }
            test.restrictedOrders = new HashSet<>();
            for (int i = 0; i < M; ++i) {
                test.restrictedOrders.add(strIterator.next());
            }
            return test;
        };
    }

    @Override
    protected Function<Object, String> getWriteConverter() {
        return Object::toString;
    }

    @Override
    protected Object getAnswer(MilkTeaTest test) {
        return getOptimalOrder(test);
    }

    public static void main(String... args) {
        String[] inputs = {"B-large"};
        new MilkTea().execute(inputs);
    }

    private static class OptimalOrder {
        List<BinaryChoice> order;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (BinaryChoice binaryChoice : order) {
                builder.append(binaryChoice.toString());
            }

            return builder.toString();
        }

        public int getCompleins() {
            return order.stream().mapToInt(choice -> choice.isOne ? choice.zeros : choice.ones).sum();
        }

        public OptimalOrder getCopy() {
            OptimalOrder copy = new OptimalOrder();
            copy.order = new ArrayList<>();
            for (int i = 0; i < this.order.size(); ++i) {
                copy.order.add(this.order.get(i).getClone());
            }
            return copy;
        }
    }

    private static class BinaryChoice {
        int zeros;
        int ones;
        boolean isOne;

        @Override
        public String toString() {
            return isOne ? "1" : "0";
        }

        public BinaryChoice getClone() {
            BinaryChoice choice = new BinaryChoice();
            choice.isOne = this.isOne;
            choice.ones = this.ones;
            choice.zeros = this.zeros;
            return choice;
        }
    }
    private Integer getOptimalOrder(MilkTeaTest test) {
        OptimalOrder optimalOrder = getOptimalOrderSec(test);

        return getOptimalOrderWithRestrictions(optimalOrder, test.restrictedOrders, 0, test.P);
    }

    private int getOptimalOrderWithRestrictions(OptimalOrder optimalOrder,
                                                Set<String> restrictedOrders, int pos, Long P) {
        int res = Integer.MAX_VALUE;
        if (!restrictedOrders.contains(optimalOrder.toString())) {
            res = optimalOrder.getCompleins();
        }
        if (pos >= P) {
            return res;
        }
        OptimalOrder optimalOrderLeft = optimalOrder.getCopy();
        OptimalOrder optimalOrderRight = optimalOrder.getCopy();
        optimalOrderLeft.order.get(pos).isOne = !optimalOrderLeft.order.get(pos).isOne;
        pos++;
        res = Math.min(res, getOptimalOrderWithRestrictions(optimalOrderLeft, restrictedOrders, pos, P));
        return  Math.min(res, getOptimalOrderWithRestrictions(optimalOrderRight, restrictedOrders, pos, P));

    }

    private OptimalOrder getOptimalOrderSec(MilkTeaTest test) {
        OptimalOrder optimalOrder = new OptimalOrder();
        optimalOrder.order = new ArrayList<>();
        for (int i = 0; i < test.P; ++i) {
            final BinaryChoice choice = new BinaryChoice();
            optimalOrder.order.add(choice);
            for (int j = 0; j < test.friendsOrder.length; ++j) {
                if (test.friendsOrder[j].charAt(i) == '0') {
                    choice.zeros++;
                } else {
                    choice.ones++;
                }
            }
            choice.isOne = choice.ones > choice.zeros;
        }
        return optimalOrder;
    }
}
