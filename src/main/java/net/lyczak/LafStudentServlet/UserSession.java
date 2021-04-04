package net.lyczak.LafStudentServlet;

import net.lyczak.LafStudentServlet.Jobs.Job;
import net.lyczak.LafStudentServlet.Jobs.JobType;
import net.lyczak.LafStudentServlet.Jobs.MoodleEventsJob;
import net.lyczak.LafStudentServlet.Jobs.TransactMealsJob;
import net.lyczak.LafStudentUtils.CasClient;
import net.lyczak.LafStudentUtils.CasCredentialProvider;
import net.lyczak.LafStudentUtils.MoodleClient;
import net.lyczak.LafStudentUtils.TransactClient;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.remote.RemoteWebDriver;

public class UserSession {
    private static final int TOKEN_LENGTH = 24;

    private LssUser user;
    private String token;
    private UserJobSequence jobs = new UserJobSequence();
    private RemoteWebDriver webDriver;

    private CasCredentialProvider credProv;
    private CasClient casClient;
    private MoodleClient moodleClient;
    private TransactClient transactClient;

    public UserSession(LssUser user, RemoteWebDriver webDriver, CasCredentialProvider credProv) {
        this.user = user;
        this.webDriver = webDriver;
        this.credProv = credProv;

        generateToken();
    }

    public Job addJob(JobType jobType, boolean repeating) {
        Job job = null;

        switch (jobType) {
            case MOODLE_EVENTS:
                job = new MoodleEventsJob(webDriver, repeating, getMoodleClient());
                break;
            case TRANSACT_MEALS:
                job = new TransactMealsJob(webDriver, repeating, getTransactClient());
                break;
        }

        if (jobs.add(job)) {
            return job;
        } else {
            return null;
        }
    }

    public CasClient getCasClient() {
        if (casClient == null) {
            casClient = new CasClient(credProv);
        }

        return casClient;
    }

    public MoodleClient getMoodleClient() {
        if (moodleClient == null) {
            moodleClient = new MoodleClient(getCasClient());
        }

        return moodleClient;
    }

    public TransactClient getTransactClient() {
        if (transactClient == null) {
            transactClient = new TransactClient(getCasClient());
        }

        return transactClient;
    }

    public String getUserId() {
        return user.getUserId();
    }

    public String getToken() {
        return token;
    }

    public void generateToken() {
        token = RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH);
    }

    public UserJobSequence getJobs() {
        return jobs;
    }

    public RemoteWebDriver getWebDriver() {
        return webDriver;
    }
}
