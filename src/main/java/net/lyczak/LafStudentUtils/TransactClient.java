package net.lyczak.LafStudentUtils;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class TransactClient {
    private CasClient casClient;

    private static final int CAS_TIMEOUT = 4; // seconds

    public TransactClient(CasClient casClient) {
        this.casClient = casClient;
    }

    public Integer getWeekMealsRemaining(JBrowserDriver driver) {
        driver.get("https://lafayette-sp.transactcampus.com/PARDaccounts/AccountSummary.aspx?menu=0");

        try {
            // Wait a bit to see if we end up on cas
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(CAS_TIMEOUT))
                    .pollingEvery(Duration.ofMillis(100))
                    .until(d -> "Login - CAS – Central Authentication Service".equals(d.getTitle()));

            casClient.authenticate(driver);
        } catch (TimeoutException e) {
            // no cas so lets proceed
        }

        try {
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofMillis(1000))
                    .until(d -> "eAccounts Account Summary".equals(d.getTitle()));

            // fake clicking the "Board Plan" button to load in ajax from the server
            driver.executeScript("__doPostBack('ctl00$MainContent$BoardAccountContainer4','');");

            // wait for the meals-per-week section to appear
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofMillis(1000))
                    .ignoring(NoSuchElementException.class)
                    .until(d -> d.findElementById("MainContent_mprWeekValue") != null);
        } catch (TimeoutException e) {
            // something timed out
            // TODO: exception?
            return null;
        }

        try {
            return Integer.parseInt(driver.findElementById("MainContent_mprWeekValue").getText());
        } catch (NumberFormatException e) {
            // couldnt parse meal number
            return null;
        }
    }
}
