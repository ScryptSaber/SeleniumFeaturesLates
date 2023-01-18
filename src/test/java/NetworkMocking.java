import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v108.fetch.Fetch;

import java.time.Duration;
import java.util.Optional;

public class NetworkMocking {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));

        devTools.addListener(Fetch.requestPaused(), request -> {
            if (request.getRequest().getUrl().contains("shetty")) {
                String mockedUrl = request.getRequest().getUrl().replace("=shetty", "=BadGuy");
                System.out.println(mockedUrl);

                devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(mockedUrl),
                        Optional.of(request.getRequest().getMethod()), Optional.empty(),
                        Optional.empty(), Optional.empty()));

            } else {
                devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(request.getRequest().getUrl()),
                        Optional.of(request.getRequest().getMethod()), Optional.empty(),
                        Optional.empty(), Optional.empty()));

            }
        });

        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.xpath("//button[normalize-space()='Virtual Library']")).click();
        System.out.println("\n".concat(driver.findElement(By.cssSelector("p")).getText()));

    }
}
