package kickstart_round_f_2018;

import wrapper.ProblemExecutor;
import wrapper.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PalindromicSequence extends ProblemExecutor<PalindromicSequence.PalindromicSequenceTest> {

    public static void main(String... args) {
        String[] inputs = {"C-small-attempt2"};
        new PalindromicSequence().execute(inputs);
    }

    @Override
    protected Function<Iterator<String>, PalindromicSequenceTest> getReadConverter() {
        return strIterator -> {
            final PalindromicSequenceTest test = new PalindromicSequenceTest();
            final String[] attr = strIterator.next().split(" ");
            test.letterNumber = Long.valueOf(attr[0]);
            test.wordLength = Long.valueOf(attr[1]);
            test.pos = Long.valueOf(attr[2]);
            return test;
        };
    }

    @Override
    protected Function<Object, String> getWriteConverter() {
        return Object::toString;
    }


    public class PalindromicSequenceTest implements Test {
        Long letterNumber;
        Long wordLength;
        Long pos;
    }

    @Override
    protected Object getAnswer(PalindromicSequenceTest test) {
        final List<String> strs = new ArrayList<>();
        strs.add("a");
        for (int i = 1; i < test.wordLength; ++i) {
            strs.add(strs.get(i - 1) + "a");
        }
        StringBuilder strSup = new StringBuilder(strs.get(strs.size() - 1));
        for (char c = 'b'; c - 'a' < test.letterNumber; c++) {
            if (strs.size() >= test.pos) {
                break;
            } else {
                test.pos -= strs.size();
                strs.clear();
            }

            StringBuilder str = new StringBuilder(strSup);
            for (int i = 0; i <= test.wordLength / 2; ++i) {
                for (int len = test.wordLength % 2 == 1 ? 0 : 1; len <= test.wordLength / 2 - i; ++len) {
                    for (int j = 0; j <= len; ++j) {
                        str.setCharAt(i + j, c);
                    }
                    strs.add(str.toString());
                    str = new StringBuilder(strSup);
                }
            }

            strSup = new StringBuilder();
            for (int i = 0; i < test.wordLength; ++i) {
                strSup.append(c);
            }
            if (test.wordLength != 1) {
                strs.add(strSup.toString());
            }
        }
        if (strs.size() < test.pos) {
            return 0;
        }
        strs.sort(String::compareTo);
        return strs.get((int) (test.pos - 1)).length();
    }
}
