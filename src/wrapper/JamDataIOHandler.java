package wrapper;

import java.util.List;

public interface JamDataIOHandler<T> {
    public void writeOutput(List data);

    public List<T> readInput();
}
