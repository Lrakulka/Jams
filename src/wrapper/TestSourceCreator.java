package wrapper;

public interface TestSourceCreator {
    boolean nonExist(Class<? extends ProblemExecutor> clazz, String in);

    boolean createSource(Class<? extends ProblemExecutor> clazz, String in);
}
