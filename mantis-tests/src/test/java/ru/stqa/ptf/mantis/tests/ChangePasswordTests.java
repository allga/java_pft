package ru.stqa.ptf.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * Created by Olga on 15.04.2016.
 */
public class ChangePasswordTests extends TestBase{

    @BeforeMethod
    //добавляем запуск почтового сервера перед методом, чтоб пропадала старая почта
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangeUserPasswordByAdmin() throws IOException {
        HttpSession sessionAdmin = app.newSession();
        assertTrue(sessionAdmin.login("administrator", "root"));
        assertTrue(sessionAdmin.goToManageUserPage());
    }

    //останавливаем сервер в любом лучае, если даже тест упал
    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }

}
