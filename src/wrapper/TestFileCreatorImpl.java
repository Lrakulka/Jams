package wrapper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TestFileCreatorImpl implements TestSourceCreator {
    private static final String END_OF_INPUT = "";

    @Override
    public boolean nonExist(Class<? extends ProblemExecutor> clazz, String in) {
        final Path testFilePath = getPath(clazz, in);
        return Files.notExists(testFilePath);
    }

    @Override
    public boolean createSource(Class<? extends ProblemExecutor> clazz, String in) {
        final Path testFilePath = getPath(clazz, in);
        if (!createFile(testFilePath)) {
            return false;
        }

        try (BufferedWriter bw = Files.newBufferedWriter(testFilePath)) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please write test input");
            String line;
            while (!(line = sc.nextLine()).equals(END_OF_INPUT)) {
                bw.write(line, 0, line.length());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean createFile(Path testFilePath) {
        try {
            Files.createDirectories(testFilePath.getParent());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Path getPath(Class<? extends ProblemExecutor> clazz, String in) {
        final String packName = clazz.getPackage().getName();
        return Paths.get(String.format("src\\%s\\in\\%s%s", packName, in, JamDataIOHandlerImpl.IN_SUFFIX));
    }
}
