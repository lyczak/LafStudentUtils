package net.lyczak.LafStudentUtils;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import net.lyczak.LafStudentUtils.Models.MoodleEvent;
import org.openqa.selenium.Cookie;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UglyTest {
    public static void main(String[] args) {
        JBrowserDriver driver = new JBrowserDriver();

        if (args[0] != null) {
            Set<Cookie> cookies = loadCookies(args[0]);
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    driver.manage().addCookie(cookie);
                }
            }
        }

        try {
            CasCredentialProvider credProv = new CasCredentialProvider() {
                @Override
                public String getUsername() {
                    return System.console().readLine("Username: ");
                }

                @Override
                public String getPassword() {
                    return new String(System.console()
                            .readPassword("Password: "));
                }
            };

            CasClient casClient = new CasClient(credProv);

            MoodleClient moodle = new MoodleClient(casClient);
            List<MoodleEvent> events = moodle.getEvents(driver);

            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d h:mm a");
            log("\nMoodle Events: " + events.size());
            events.forEach(e -> System.out.printf(
                    "%s - %s (%s)%n",
                    sdf.format(HelperUtils.fromMoodleDate(e.getTimestart())),
                    e.getName(),
                    e.getCourse().getShortname()));

            TransactClient transact = new TransactClient(casClient);
            Integer meals = transact.getWeekMealsRemaining(driver);
            log("\nMeals: " + meals);
        } finally {
            if (args[0] != null) {
                trySaveCookies(args[0], driver.manage().getCookies());
            }

            driver.quit();
        }
    }


    private static boolean trySaveCookies(String path, Set<Cookie> cookies) {
        try {
            FileOutputStream f = new FileOutputStream(new File(path));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(new HashSet<Cookie>(cookies));

            o.close();
            f.close();

            log("Saved cookies.");
            return true;
        } catch (IOException e) {
            log("Failed to save cookies.");
            return false;
        }
    }

    private static Set<Cookie> loadCookies(String path) {
        try {
            FileInputStream f = new FileInputStream(new File(path));
            ObjectInputStream o = new ObjectInputStream(f);


            Set<Cookie> cookies = (HashSet<Cookie>) o.readObject();

            o.close();
            f.close();

            log("Loaded cookies.");
            return cookies;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            log("Failed to load cookies.");
            return null;
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
