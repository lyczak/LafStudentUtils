package net.lyczak.LafStudentUtils;

public class LsuException extends RuntimeException {
    private String pageSource;
    private String detail;

    public LsuException(String message) {
        super(message);
    }

    public LsuException(String message, String pageSource) {
        super(message);
        this.pageSource = pageSource;
    }

    public String getPageSource() {
        return pageSource;
    }

    public String getDetail() {
        return detail;
    }

    public LsuException withDetail(String detail) {
        this.detail = detail;
        return this;
    }
}
