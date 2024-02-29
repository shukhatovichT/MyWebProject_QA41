package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.BasePage;

import java.time.Duration;

public class BaseTest {
    private static final ThreadLocal <WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver(){
        return driverThreadLocal.get();
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser){
        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--lang=en");
            //options.addArguments("--headless"); // функция для запуска браузера без самого браузера
            driverThreadLocal.set(new ChromeDriver(options));
        }
        else if(browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();

            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("intl.accept_language", "en");
            driverThreadLocal.set(new FirefoxDriver(options));
        }
        else {throw new IllegalArgumentException("Invalid browser " + browser);}

        WebDriver driver = getDriver();
        driver.manage().window().maximize(); //расширить окно ан весь экран
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(20000)); //время ожидания для загрузки страницы
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(20000)); //неявное ожидание - если элемент не найден сразу, то драйвер будет ждать указанное время
        BasePage.setDriver(driver);
    }

    @AfterMethod
    public void tearDown(){
        WebDriver driver = getDriver();
        if(driver != null){
            driver.quit();
            driverThreadLocal.remove();
        }
    }


}
