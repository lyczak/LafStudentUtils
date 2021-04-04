package net.lyczak.LafStudentServlet.Jobs;

import net.lyczak.LafStudentUtils.LsuException;
import net.lyczak.LafStudentUtils.Models.MoodleEvent;
import net.lyczak.LafStudentUtils.MoodleClient;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Arrays;
import java.util.List;

public class MoodleEventsJob extends Job<List<MoodleEvent>, LsuException> {
    private MoodleClient moodle;

    public MoodleEventsJob(RemoteWebDriver driver, boolean repeating, MoodleClient moodle) {
        super(driver, repeating);
        this.moodle = moodle;
    }

    @Override
    public void run() {
        try {
            List<MoodleEvent> events = moodle.getEvents(driver, driver);

            if (events != null) {
                result = JobResult.ok(events);
            } else {
                result = JobResult.error(new LsuException("Events list was null", driver.getPageSource()));
            }
        } catch (LsuException e) {
            result = JobResult.error(e);
        } catch (Exception e) {
            result = JobResult.error(new LsuException(e.getMessage(),
                    driver.getPageSource()).withDetail(Arrays.deepToString(e.getStackTrace())));
        }
    }
}
