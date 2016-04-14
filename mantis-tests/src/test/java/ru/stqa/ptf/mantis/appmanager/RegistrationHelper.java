package ru.stqa.ptf.mantis.appmanager;

import org.openqa.selenium.By;

/**
 * Created by Olga on 13.04.2016.
 */
public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(ApplicationManager app) {
        //передаем в конструктор HelperBase ссылку на ApplicationManager
        super(app);
    }

    //этому методу нужен браузер
    public void start(String username, String email) {
        driver.get(app.getProperty("web.baseUrl") + "/signup_page.php");
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[value='Signup']"));
    }
}
