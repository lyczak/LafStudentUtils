package net.lyczak.LafStudentUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import net.lyczak.LafStudentUtils.Models.MoodleApiResponse;
import net.lyczak.LafStudentUtils.Models.MoodleEvent;
import net.lyczak.LafStudentUtils.Models.MoodleEventsData;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;

public class MoodleClient {
    private CasClient casClient;

    public MoodleClient(CasClient casClient) {
        this.casClient = casClient;
    }

    public List<MoodleEvent> getEvents(JBrowserDriver driver) {
        driver.get("https://cas.lafayette.edu/cas/login?service=https%3A%2F%2Fmoodle.lafayette.edu%2Flogin%2Findex.php");

        casClient.authenticate(driver);

        // Wait for Moodle to load.
        try {
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(40))
                    .pollingEvery(Duration.ofMillis(1000))
                    .ignoring(NullPointerException.class)
                    .until(d -> d.getTitle().equals("Dashboard"));
        } catch (TimeoutException e) {
            // We're not on moodle dashboard
            // TODO: throw exception or something
            return null;
        }

        String sessionKey = driver.findElementByName("sesskey").getAttribute("value");

        Object scriptResult = driver.executeAsyncScript(BrowserDriverUtils.getScript("MoodleGetEvents.js"), sessionKey);

        Gson gson = new Gson();
        Type responsesType = new TypeToken<List<MoodleApiResponse<MoodleEventsData>>>() {}.getType();
        List<MoodleApiResponse<MoodleEventsData>> responses = gson.fromJson((String) scriptResult, responsesType);
        MoodleApiResponse<MoodleEventsData> response = responses.get(0);

        if(response.isError()) {
            // TODO: throw exception?
            return null;
        } else {
            return response.getData().getEvents();
        }
    }
}
