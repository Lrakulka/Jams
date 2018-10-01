package kickstart_round_f_2018;

import wrapper.ProblemExecutor;
import wrapper.Test;

import java.util.Iterator;
import java.util.function.Function;

public class CommonAnagrams extends ProblemExecutor<CommonAnagrams.CommonAnagramsTest> {

    private static final int MAX = 256;

    public static void main(String... args) {
        String[] inputs = {"A-large"};
        new CommonAnagrams().execute(inputs);
    }

    static boolean compare(char arr1[], char arr2[]) {
        for (int i = 0; i < MAX; i++)
            if (arr1[i] != arr2[i])
                return false;
        return true;
    }

    @Override
    protected Function<Iterator<String>, CommonAnagramsTest> getReadConverter() {
        return strIterator -> {
            final CommonAnagramsTest test = new CommonAnagramsTest();
            strIterator.next();
            test.A = strIterator.next();
            test.B = strIterator.next();
            return test;
        };
    }

    @Override
    protected Function<Object, String> getWriteConverter() {
        return Object::toString;
    }

    @Override
    protected Object getAnswer(CommonAnagramsTest test) {
        int count = 0;
        for (int i = 0; i <= test.A.length(); ++i) {
            for (int len = 1; len <= test.A.length() - i; ++len) {
                count += search(test.A.substring(i, i + len), test.B) > 0 ? 1 : 0;
            }
        }
        return count;
    }

    private Integer search(String pat, String txt) {
        int count = 0;
        int M = pat.length();
        int N = txt.length();

        char[] countP = new char[MAX];
        char[] countTW = new char[MAX];
        for (int i = 0; i < M; i++) {
            (countP[pat.charAt(i)])++;
            (countTW[txt.charAt(i)])++;
        }

        for (int i = M; i < N; i++) {
            if (compare(countP, countTW))
                count++;

            (countTW[txt.charAt(i)])++;
            countTW[txt.charAt(i - M)]--;
        }

        if (compare(countP, countTW))
            count++;
        return count;
    }

    class CommonAnagramsTest implements Test {
        String A;
        String B;
    }
}
