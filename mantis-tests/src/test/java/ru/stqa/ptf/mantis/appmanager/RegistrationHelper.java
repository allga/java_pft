package ru.stqa.ptf.mantis.appmanager;

import org.openqa.selenium.WebDriver;

/**
 * Created by Olga on 13.04.2016.
 */
public class RegistrationHelper {
    private final ApplicationManager app;
    private WebDriver driver;

    public RegistrationHelper(ApplicationManager applicationManager) {
        //передаем хелперу ссылку на ApplicationManager
        this.app = applicationManager;
        //берем ссылку на драйвер у ApplicationManager, в котором он инициализирован
        driver = app.getDriver();
    }

    //этому методу нужен браузер
    public void start(String username, String email) {
        driver.get(app.getProperty("web.baseUrl") + "/signup_page.php");

    }
}
