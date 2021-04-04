package net.lyczak.LafStudentServlet.Jobs;

public class JobResult<T, E> {
    private String status;
    private T value;
    private E error;

    private JobResult(String status, T value, E error) {
        this.status = status;
        this.value = value;
        this.error = error;
    }

    public static <T, E> JobResult<T, E> ok(T value) {
        return new JobResult<>("ok", value, null);
    }

    public static <T, E> JobResult<T, E> error(E error) {
        return new JobResult<>("error", null, error);
    }

    public static <T, E> JobResult<T, E> pending() {
        return new JobResult<>("pending", null, null);
    }

    public String getStatus() {
        return status;
    }

    public T getValue() {
        return value;
    }

    public E getError() {
        return error;
    }
}
