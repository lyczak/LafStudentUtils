package net.lyczak.LafStudentServlet.Jobs;

import net.lyczak.LafStudentUtils.LsuException;
import net.lyczak.LafStudentUtils.TransactClient;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Arrays;

public class TransactMealsJob extends Job<Integer, LsuException> {
    private TransactClient transact;

    public TransactMealsJob(RemoteWebDriver driver, boolean repeating, TransactClient transact) {
        super(driver, repeating);
        this.transact = transact;
    }

    @Override
    public void run() {
        try {
            Integer meals = transact.getWeekMealsRemaining(driver, driver);

            if (meals != null) {
                result = JobResult.ok(meals);
            } else {
                result = JobResult.error(new LsuException("Meal number was null", driver.getPageSource()));
            }
        } catch (LsuException e) {
            result = JobResult.error(e);
        } catch (Exception e) {
            result = JobResult.error(new LsuException(e.getMessage(),
                    driver.getPageSource()).withDetail(Arrays.deepToString(e.getStackTrace())));
        }
    }
}
