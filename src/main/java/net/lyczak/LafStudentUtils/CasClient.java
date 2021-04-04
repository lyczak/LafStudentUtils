package net.lyczak.LafStudentUtils;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

public class CasClient {
    private CasCredentialProvider credentialProvider;
    private boolean rememberMe;
    private int pushTimeout = 20; // seconds

    public CasClient(CasCredentialProvider credentialProvider, boolean rememberMe) {
        this.credentialProvider = credentialProvider;
        this.rememberMe = rememberMe;
    }

    public CasClient(CasCredentialProvider credentialProvider) {
        this(credentialProvider, false);
    }

    public void authenticate(WebDriver driver) {
        if (driver.getTitle().equals("Login - CAS – Central Authentication Service")) {
            // Now at Login screen

            driver.findElement(By.id("username")).sendKeys(credentialProvider.getUsername());
            driver.findElement(By.id("password")).sendKeys(credentialProvider.getPassword());

            driver.findElement(By.name("submit")).click();

            try {
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(5))
                        .pollingEvery(Duration.ofMillis(100))
                        .until(d -> "Duo Security Login - CAS – Central Authentication Service".equals(d.getTitle()));
            } catch (TimeoutException e) {
                throw new LsuException("Timed out while waiting for Duo page-load", driver.getPageSource());
            }

            if (rememberMe) {
                // Now at Duo screen
                // We must enter the Duo iframe!
                driver.switchTo().frame("duo_iframe");

                // there was a sleep here

                try {
                    new FluentWait<>(driver)
                            .withTimeout(Duration.ofSeconds(5))
                            .pollingEvery(Duration.ofMillis(100))
                            .ignoring(NoSuchElementException.class)
                            .until(d -> d.findElement(By.cssSelector(
                                    ".message-content > button.btn-cancel")));
                } catch (TimeoutException e) {
                    System.out.println("Timed out waiting for Duo cancel button."); // TODO: review this
                }

                try {
                    // Cancel automatic request if sent.
                    WebElement cancelButton = driver.findElement(By.cssSelector(
                            //#messages-view > .messages-list > div[data-id = '0'] >
                            ".message-content > button.btn-cancel"));
                    cancelButton.click();
                } catch (NoSuchElementException e) {
                    // TODO: error handling
                    // Automatic push not sent or we're remembered.
                }

                try {
                    // Remember me
                    WebElement rememberMeCheckbox = driver.findElement(By.xpath(
                            "//*[@id='remember_me_label_text']/preceding-sibling::input[@type='checkbox']"));
                    if (!rememberMeCheckbox.isSelected()) {
                        rememberMeCheckbox.click();
                    }
                } catch (NoSuchElementException e) {
                    // TODO: error handling
                    // We're remembered or something broke.
                }

                try {
                    new FluentWait<>(driver) // Keep trying to send second push
                            .withTimeout(Duration.ofSeconds(pushTimeout))
                            .pollingEvery(Duration.ofMillis(2000))
                            .until(d -> {
                                try {
                                    d.findElement(By.cssSelector( // click "Send Me a Push"
                                            "div.push-label > button.positive.auth-button")).click();

                                    List<WebElement> messages = d.findElement(By.id("messages-view"))
                                            .findElements(By.cssSelector("message.error"));

                                    messages.sort(Comparator.comparing(m -> m.getAttribute("data-id")));

                                    return !messages.isEmpty() && !messages.get(messages.size() - 1)
                                            .findElement(By.className("message-text"))
                                            .getText()
                                            .contains("Anomalous request");
                                } catch (NoSuchElementException e) {
                                    return true;
                                }
                            });
                } catch (TimeoutException e) {
                    throw new LsuException("Timed out waiting for first DUO push accept", driver.getPageSource());
                }

                driver.switchTo().defaultContent();

                try {
                    new FluentWait<>(driver) // Wait to leave CAS Duo
                            .withTimeout(Duration.ofSeconds(pushTimeout))
                            .pollingEvery(Duration.ofMillis(1000))
                            .until(d -> !"Duo Security Login - CAS – Central Authentication Service"
                                    .equals(d.getTitle()));
                } catch (TimeoutException e) {
                    throw new LsuException("Timed out waiting for second DUO push accept", driver.getPageSource());
                }
            }
        }
    }
}
