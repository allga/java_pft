package ru.stqa.ptf.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created by Olga on 17.04.2016.
 */
public class RegistrationWithRealMailTests extends TestBase {

    //,будем использовать отдельностоящий почтовый сервер James, а не встроенный,
    // поэтому отключаем методы инициализации и отключения

    @Test
    public void testRegistration() throws IOException, MessagingException {
        long now = System.currentTimeMillis();
        String email = String.format("user%s@localhost.localdomain", now);
        String user = String.format("user%s", now);
        String password = "password";

        //создаем юзера на почтовом сервере
        app.getJamesHelper().createUser(user, password);

        app.registration().start(user, email);

        //получаем письмо с внешнего почтового сервера James
        List<MailMessage> mailMessages = app.getJamesHelper().waitForMail(user, password, 60000);
        //извлекаем конфирм ссылку из письма
        String confirmationLink = app.mail().findConfirmationLink(mailMessages, email);
        //заканчиваем регистрацию с переходом по линке
        app.registration().finish(confirmationLink, password);
        //проверяем логин юзером
        assertTrue(app.newSession().login(user, password));
    }
}
