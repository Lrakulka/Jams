package util;

import java.io.*;
import java.util.Objects;

public class ProblemDataIO {
    private static final String OUT_SUFFIX = ".out";
    private final InputData inputData;
    private final OutputData outputData;
    private final String inURI;
    private final String outURI;

    public ProblemDataIO(InputData inputData, OutputData outputData, String packName, String uri) {
        this.getClass().getPackage();
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

    public void writeData(Object data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outURI))) {
            bw.write(outputData.getOutput(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
