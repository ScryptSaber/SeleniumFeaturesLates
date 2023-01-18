import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v108.fetch.Fetch;
import org.openqa.selenium.devtools.v108.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v108.network.model.ErrorReason;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NetworkFailedRequest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        //java.util.Optional<java.lang.String> urlPattern
        Optional<List<RequestPattern>> patterns = Optional.of(Arrays.asList(new RequestPattern(Optional.of("*GetBook*"), Optional.empty(), Optional.empty())));

        devTools.send(Fetch.enable(patterns, Optional.empty()));

        devTools.addListener(Fetch.requestPaused(), request -> {

            devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
        });


        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.xpath("//button[normalize-space()='Virtual Library']")).click();

    }

}
