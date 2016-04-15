package ru.stqa.ptf.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.mantis.appmanager.HttpSession;
import ru.stqa.ptf.mantis.model.MailMessage;
import ru.stqa.ptf.mantis.model.User;

import java.io.IOException;
import java.util.List;

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
        String newPassword = "passwordnew";
        app.getNavigationHelper().goToLoginPage();
        app.getUserHelper().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        app.getNavigationHelper().goToManageUserPage();

        User changedUser = app.getUserHelper().getAnyUserFromBD();
        app.getNavigationHelper().goToUserPage(changedUser.getId());
        app.getUserHelper().startResetPassword();
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = app.mail().findConfirmationLink(mailMessages, changedUser.getEmail());
        app.registration().finish(confirmationLink, newPassword);

        User user = app.getUserHelper().getUserByIdFromBD(changedUser.getId());

        HttpSession sessionUser = app.newSession();
        assertTrue(sessionUser.login(user.getUsername(), newPassword));
        assertTrue(sessionUser.isLoggedInAs(user.getUsername()));
    }

    //останавливаем сервер в любом лучае, если даже тест упал
    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }

}
