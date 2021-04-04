package net.lyczak.LafStudentServlet.Jobs;

import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class Job<T, E> implements Runnable {
    private static int lastId = 1;

    protected RemoteWebDriver driver;
    protected boolean repeating;
    protected int id;
    protected JobResult<T, E> result = JobResult.pending();

    public Job(RemoteWebDriver driver, boolean repeating) {
        this.driver = driver;
        this.repeating = repeating;

        lastId++;
        this.id = lastId;
    }

    public RemoteWebDriver getDriver() {
        return driver;
    }

    public void setDriver(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public void setRepeating(boolean repeating) {
        this.repeating = repeating;
    }

    public int getId() {
        return id;
    }

    public JobResult<T, E> getResult() {
        return result;
    }
}
