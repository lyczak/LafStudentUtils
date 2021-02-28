package net.lyczak.LafStudentServlet;

import net.lyczak.LafStudentUtils.CasCredentialProvider;

import java.util.HashMap;
import java.util.Map;

public class JobDispatcher {
    private Map<String, UserJobExecutor> jobExecs = new HashMap<>();

    private static JobDispatcher instance;

    private JobDispatcher() {}

    public static JobDispatcher getInstance() {
        if(instance == null)
            instance = new JobDispatcher();

        return instance;
    }

    public void add(String username, String password, Job job) {
        UserJobExecutor jobExec = jobExecs.get(username);

        if (jobExec == null) {

        }

        //jobExecs.put(username, )
    }
}
