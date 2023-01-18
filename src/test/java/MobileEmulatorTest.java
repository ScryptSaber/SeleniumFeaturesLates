import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.emulation.Emulation;

import java.time.Duration;
import java.util.Optional;

public class MobileEmulatorTest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        //send command to CDP Methods (Chrome DEvTools Protocol)
        // ->CDP Methods will invoke and get access to chrome dev tools
        devTools.send(Emulation.setDeviceMetricsOverride(600, 1000, 50, true, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.cssSelector(".navbar-toggler-icon")).click();
        driver.findElement(By.linkText("Library")).click();
    }

}
