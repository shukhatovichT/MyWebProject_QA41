package tests;

import config.BaseTest;
import helpers.TopMenuItem;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.LoginPage;
import pages.MainPage;

public class PhoneBookTest extends BaseTest {

    @Test
    public void phoneBookTest_001() throws InterruptedException {
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        loginPage.fillEmailField("email@gmail.com").clickByRegistrationButton();


        Thread.sleep(5000);

    }
}
