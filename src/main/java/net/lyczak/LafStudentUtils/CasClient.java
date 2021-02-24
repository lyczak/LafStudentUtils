package net.lyczak.LafStudentUtils;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class CasClient {
    private CasCredentialProvider credentialProvider;
    private boolean rememberMe = true; // TODO: remember-me was buggy so I disabled it for now

    public CasClient(CasCredentialProvider credentialProvider) {
        this.credentialProvider = credentialProvider;
    }

    public void authenticate(JBrowserDriver driver) {
        if (driver.getTitle().equals("Login - CAS – Central Authentication Service")) {
            // Now at Login screen

            driver.findElementById("username").sendKeys(credentialProvider.getUsername());
            driver.findElementById("password").sendKeys(credentialProvider.getPassword());

            driver.findElementByName("submit").click();

            if (rememberMe) {
                // Now at Duo screen
                // We must enter the Duo iframe!
                driver.switchTo().frame("duo_iframe");

                try {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Cancel automatic request if sent.
                    WebElement cancelButton = driver.findElementByCssSelector(//#messages-view > .messages-list > div[data-id = '0'] >
                            ".message-content > button.btn-cancel");
                    cancelButton.click();

                    // Wait a sec to avoid "anomalous request"
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (NoSuchElementException e) {
                    // Automatic push not sent or we're remembered.
                }

                try {
                    // Remember me
                    WebElement rememberMeCheckbox = driver.findElementByXPath(
                            "//*[@id='remember_me_label_text']/preceding-sibling::input[@type='checkbox']");
                    if (!rememberMeCheckbox.isSelected()) {
                        rememberMeCheckbox.click();
                    }

                    // Send push
                    driver.findElementByCssSelector("div.push-label > button.positive.auth-button").click();

                } catch (NoSuchElementException e) {
                    // We're remembered or something broke.
                }
            }
        }
    }
}
