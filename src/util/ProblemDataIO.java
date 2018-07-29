package util;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ProblemDataIO {
    private static final String OUT_SUFFIX = ".out";
    private final InputData inputData;
    private final OutputData outputData;
    private final String inURI;
    private final String outURI;

    public ProblemDataIO(InputData inputData, OutputData outputData, String packName, String uri) {
        this.inURI = "src\\" + packName + "\\in\\" + uri;
        this.outURI = "src\\" + packName + "\\out\\" + uri.substring(0, uri.indexOf('.')) + OUT_SUFFIX;
        this.inputData = inputData;
        this.outputData = outputData;
    }

    public InputData readData() {
        try (BufferedReader br = new BufferedReader(new FileReader(inURI))) {
            String sCurrentLine;

            if (Objects.isNull(sCurrentLine = br.readLine())) {
                throw new IllegalArgumentException();
            }

            inputData.setParams(sCurrentLine.split(" "));

            while ((sCurrentLine = br.readLine()) != null) {
                inputData.fillData(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputData;
    }

    public void writeOutput(List data) {
        final StringBuilder builder = new StringBuilder();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outURI))) {
            for (int i = 0; i < data.size(); ++i) {
                builder.append("Case #").append(i + 1).append(":").append(outputData.getOutput(data.get(i))).append("\n");
                bw.write(builder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public void writeData(Object data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outURI))) {
            bw.write(outputData.getOutput(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
