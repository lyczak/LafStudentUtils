package net.lyczak.LafStudentUtils.Models;

public class MoodleException {
    private String message;
    private String errorcode;
    private String link;
    private String moreinfourl;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMoreinfourl() {
        return moreinfourl;
    }

    public void setMoreinfourl(String moreinfourl) {
        this.moreinfourl = moreinfourl;
    }
}
