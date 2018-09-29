package wrapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class JamDataIOHandlerImpl<T extends Test> {
    static final String OUT_SUFFIX = ".out";
    static final String IN_SUFFIX = ".in";

    private final String inURI;
    private final String outURI;

    private Function<Iterator<String>, T> readConverter;
    private Function<Object, String> writeConverter;

    public JamDataIOHandlerImpl(final Class problemSolutionClass, final String uri,
                                final Function<Iterator<String>, T> readConverter,
                                final Function<Object, String> writeConverter) {
        final String packName = problemSolutionClass.getPackage().getName();
        this.inURI = "src\\" + packName + "\\in\\" + uri + IN_SUFFIX;
        this.outURI = "src\\" + packName + "\\out\\" + uri + OUT_SUFFIX;
        this.readConverter = readConverter;
        this.writeConverter = writeConverter;
    }

    public List<T> readInput() {
        List<T> tests = Collections.emptyList();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inURI))) {
            final Iterator<String> inputIterator = br.lines().iterator();
            final String currentLine = inputIterator.next();
            tests = new ArrayList<>(Integer.valueOf(currentLine.split(" ")[0]));
            while (inputIterator.hasNext()) {
                final T test = readConverter.apply(inputIterator);
                tests.add(test);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tests;
    }

    public void writeOutput(List data) {
        final StringBuilder builder = new StringBuilder();
        final File file = new File(outURI);
        file.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < data.size(); ++i) {
                builder.append("Case #").append(i + 1).append(": ").append(writeConverter.apply(data.get(i)))
                        .append(System.lineSeparator());
            }
            bw.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
