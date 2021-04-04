package net.lyczak.LafStudentServlet;

import net.lyczak.LafStudentUtils.CasCredentialProvider;

public class SimpleCredentialProvider implements CasCredentialProvider {
    private String username;
    private String password;

    public SimpleCredentialProvider(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
