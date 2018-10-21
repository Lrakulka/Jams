package kickstart_round_g_2018;

import wrapper.ProblemExecutor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class ProductTriplets extends ProblemExecutor<ProductTriplets.ProductTripletsTest> {

    public class ProductTripletsTest implements wrapper.Test {
        long[] nums;
    }

    @Override
    protected Function<Iterator<String>, ProductTripletsTest> getReadConverter() {
        return stringIterator -> {
            ProductTripletsTest test = new ProductTripletsTest();
            int N = Integer.valueOf(stringIterator.next());
            String[] strNums = stringIterator.next().split(" ");
            test.nums = new long[N];
            for (int i = 0; i < N; ++i) {
                test.nums[i] = Long.valueOf(strNums[i]);
            }
            return test;
        };
    }

    @Override
    protected Function<Object, String> getWriteConverter() {
        return Object::toString;
    }

    @Override
    protected Object getAnswer(ProductTripletsTest test) {
        return countProductTripletsV1(test.nums).size();
    }

    public static void main(String... args) {
        String[] inputs = {"A-small-attempt7"};
        new ProductTriplets().execute(inputs);
    }

    private List<List<Long>> countProductTripletsV1(long[] num) {
        Arrays.sort(num);
        int nulls = 0;
        for (;nulls < num.length && num[nulls] == 0; ++nulls);
        for(int i = nulls; i < nulls + (num.length - nulls) / 2; i++) {
            long temp = num[i];
            num[i] = num[num.length - i - 1 + nulls];
            num[num.length - i - 1 + nulls] = temp;
        }
        List<List<Long>> res = new LinkedList<>();
        for (int i = 0; i < num.length-2; i++) {
            if (i == 0 || num[i] != num[i - 1]) {
                int lo = i+1, hi = num.length-1;
                long mul = num[i];
                while (lo < hi) {
                    if (num[lo] * num[hi] == mul) {
                        res.add(Arrays.asList(num[i], num[lo], num[hi]));
                        while (lo < hi && num[lo] == num[lo+1]) lo++;
                        while (lo < hi && num[hi] == num[hi-1]) hi--;
                        if (num[lo] != 0)
                            lo++;
                        hi--;
                    } else if (num[lo] * num[hi] > mul) lo++;
                    else hi--;
                }
            }
        }
        return res;
    }

}
