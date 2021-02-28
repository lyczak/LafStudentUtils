package net.lyczak.LafStudentServlet;

import net.lyczak.LafStudentUtils.CasCredentialProvider;

import java.util.LinkedList;
import java.util.Queue;

public class UserJobExecutor implements Runnable {
    private CasCredentialProvider credProv;
    private Queue<Job> jobs = new LinkedList<>();

    public UserJobExecutor(CasCredentialProvider credentialProvider) {
        this.credProv = credentialProvider;
    }

    @Override
    public void run() {

    }
}
