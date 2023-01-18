import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.emulation.Emulation;

import java.time.Duration;
import java.util.Optional;

public class SetGeoLocation {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        //send command to CDP Methods (Chrome DEvTools Protocol)
        // ->CDP Methods will invoke and get access to chrome dev tools
        devTools.send(Emulation.setGeolocationOverride(Optional.of(40), Optional.of(3), Optional.of(1)));

        driver.get("https://my-location.org/");
//        driver.get("https://google.com");
//        driver.findElement(By.name("q")).sendKeys("netflix", Keys.ENTER);
//        driver.findElements(By.cssSelector(".LC20lb")).get(0).click();
//        String title = driver.findElement(By.cssSelector(".our-story-card-title")).getText();
//        System.out.println(title);

    }
}
