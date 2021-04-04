package net.lyczak.LafStudentServlet;

public class Result<T, E> {
    private String status;
    private T value;
    private E error;

    private Result(String status, T value, E error) {
        this.status = status;
        this.value = value;
        this.error = error;
    }

    public static <T, E> Result<T, E> ok(T value) {
        return new Result<>("ok", value, null);
    }

    public static <T, E> Result<T, E> error(E error) {
        return new Result<>("error", null, error);
    }

    public static <T, E> Result<T, E> pending() {
        return new Result<>("pending", null, null);
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
