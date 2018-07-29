package wrapper;

import kickstart_round_d_2018.Candies;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ProblemExecutor <T extends Test> {

    protected abstract Function<Iterator<String>, T> getReadConverter();

    protected abstract Function<Object, String> getWriteConverter();

    public void execute(final String[] inputs) {
        for (String in : inputs) {
            JamDataIOHandlerImpl dataIO = new JamDataIOHandlerImpl<>(Candies.class, in,
                    getReadConverter(), getWriteConverter()
            );
            List<T> inputData = dataIO.readInput();
            List<Object> output = inputData.parallelStream().map(this::getAnswer).collect(Collectors.toList());
            dataIO.writeOutput(output);
        }

    }

    protected abstract Object getAnswer(T test);
}
