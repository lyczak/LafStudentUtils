package net.lyczak.LafStudentUtils.Models;

public class MoodleApiResponse<T> {
    private boolean error;
    private T data;
    private MoodleException exception;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public MoodleException getException() {
        return exception;
    }

    public void setException(MoodleException exception) {
        this.exception = exception;
    }
}
