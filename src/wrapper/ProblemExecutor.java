package wrapper;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ProblemExecutor <T extends Test> {
    private static final String TEST_SEQ = "Test";

    protected abstract Function<Iterator<String>, T> getReadConverter();

    protected abstract Function<Object, String> getWriteConverter();

    public void execute(final String[] inputs) {
        final LocalTime startTime = LocalTime.now();
        final TestSourceCreator testSourceCreator = new TestFileCreatorImpl();

        for (String in : inputs) {
            if (testSourceCreator.nonExist(this.getClass(), in)) {
                testSourceCreator.createSource(this.getClass(), in);
            }
            final LocalTime startTestTime = LocalTime.now();
            System.out.println(String.format("Start test set \"%s\" time %s", in, startTestTime.toString()));

            JamDataIOHandlerImpl<T> dataIO =
                    new JamDataIOHandlerImpl<>(this.getClass(), in, getReadConverter(), getWriteConverter());
            final List<T> inputData = dataIO.readInput();
            final Stream<T> inputStream = (TEST_SEQ.equals(in)) ? inputData.stream() : inputData.parallelStream();
            testNumber = inputData.size();

            List<Object> output = inputStream.map(this::processTest).collect(Collectors.toList());

            dataIO.writeOutput(output);

            System.out.println("Test Duration " + Duration.between(LocalTime.now(), startTestTime).toString());
        }

        System.out.println("Total tests Duration " + Duration.between(LocalTime.now(), startTime).toString());
    }

    private volatile double testNumber;
    private AtomicInteger processedTestNumber = new AtomicInteger();
    private volatile int prevPercent = -1;
    private Object processTest(T test) {
        final Object answer = getAnswer(test);

        final int percent = (int) (processedTestNumber.incrementAndGet() / testNumber * 100);
        if (percent != prevPercent) {
            System.out.println(String.format("Processed %d%%", percent));
        }
        prevPercent = percent;

        return answer;
    }

    protected abstract Object getAnswer(T test);
}
