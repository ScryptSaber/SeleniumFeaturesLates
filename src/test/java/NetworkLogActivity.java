import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v108.network.Network;
import org.openqa.selenium.devtools.v108.network.model.Request;
import org.openqa.selenium.devtools.v108.network.model.Response;

import java.time.Duration;
import java.util.Optional;

public class NetworkLogActivity {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

        //log file ->
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.requestWillBeSent(), request -> {
            Request req = request.getRequest();
            System.out.println(req.getUrl());
        });

        //Event will get fired
        devTools.addListener(Network.responseReceived(), response -> {
            Response res = response.getResponse();
            System.out.println(res.getUrl());
            System.out.println(res.getStatus());

            if (res.getStatus().toString().startsWith("4")) {
                System.out.println(res.getUrl() + " is failing with status code " + res.getStatus());
            }
        });

        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.xpath("//button[normalize-space()='Virtual Library']")).click();


    }
}
