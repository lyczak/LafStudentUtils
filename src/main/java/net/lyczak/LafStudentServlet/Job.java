package net.lyczak.LafStudentServlet;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;

public class Job {
    private int id;
    private JBrowserDriver driver;
    private boolean repeating;

    public Job(int id, JBrowserDriver driver, boolean repeating) {
        this.id = id;
        this.driver = driver;
        this.repeating = repeating;
    }

    public int getId() {
        return id;
    }

    public JBrowserDriver getDriver() {
        return driver;
    }

    public void setDriver(JBrowserDriver driver) {
        this.driver = driver;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public void setRepeating(boolean repeating) {
        this.repeating = repeating;
    }
}
