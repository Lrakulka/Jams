package kickstart_round_e_2018;

import wrapper.ProblemExecutor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// Google what is wrong with you. You reschedule contest twice
public class Yogurt extends ProblemExecutor<Yogurt.YogurtTest> {
    static class YogurtTest implements wrapper.Test {
        long canDrink;
        List<Long> yogurtExpired;
    }

    @Override
    protected Function<Iterator<String>, YogurtTest> getReadConverter() {
        return stringIterator -> {
            final YogurtTest test = new YogurtTest();
            test.canDrink = Long.valueOf(stringIterator.next().split(" ")[1]);
            test.yogurtExpired = Arrays.stream(stringIterator.next().split(" "))
                    .map(Long::valueOf).collect(Collectors.toCollection(LinkedList::new));
            return test;
        };
    }

    @Override
    protected Function<Object, String> getWriteConverter() {
        return Object::toString;
    }

    @Override
    protected Object getAnswer(YogurtTest test) {
        return getMaxConsumableYogurts(test);
    }

    public static void main(String... args) {
        String[] inputs = {"A-large"};
        new Yogurt().execute(inputs);
    }

    private Long getMaxConsumableYogurts(YogurtTest test) {
        Long result = 0L;
        final long[] removed = {0};
        final List<Long> yogurtExpired = test.yogurtExpired;
        Collections.sort(yogurtExpired);
        while (!yogurtExpired.isEmpty()) {
            Iterator<Long> itr = yogurtExpired.iterator();
            while (itr.hasNext()) {
                if (itr.next() > 0) {
                    break;
                }
                itr.remove();
            }

            removed[0] = 0;
            itr = yogurtExpired.iterator();
            while (itr.hasNext() && (test.canDrink > removed[0])) {
                itr.next();
                removed[0]++;
                itr.remove();
            }

            result += removed[0];
            for (int i = 0; i < yogurtExpired.size(); ++i) {
                yogurtExpired.set(i, yogurtExpired.get(i) - 1);
            }
        }
        return result;
    }
}
