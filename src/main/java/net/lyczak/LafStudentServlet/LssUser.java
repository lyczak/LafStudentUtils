package net.lyczak.LafStudentServlet;

public class LssUser {
    private String userId;
    private String casUsername;
    private String webDriverUrl;

    public LssUser(String userId, String casUsername, String webDriverUrl) {
        this.userId = userId;
        this.casUsername = casUsername;
        this.webDriverUrl = webDriverUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCasUsername() {
        return casUsername;
    }

    public void setCasUsername(String casUsername) {
        this.casUsername = casUsername;
    }

    public String getWebDriverUrl() {
        return webDriverUrl;
    }

    public void setWebDriverUrl(String webDriverUrl) {
        this.webDriverUrl = webDriverUrl;
    }
}
