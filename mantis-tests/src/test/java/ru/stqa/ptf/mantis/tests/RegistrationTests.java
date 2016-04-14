package ru.stqa.ptf.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.ptf.mantis.model.MailMessage;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created by Olga on 13.04.2016.
 */
public class RegistrationTests extends TestBase {

    //чтоб тестируемое приложение знало что почту нужно доставлять именнно на наш почтовый сервер,
    // нужно добавить в конфигурационный файл config_inc.php две строчки:
    // $g_phpMailer_method = PHPMAILER_METHOD_SMTP; - способ доставки почты по протоколу SMTP
    // $g_smtp_host = 'localhost'; - адрес доставки почты
    // (если веб-сервер на удаленной машине, указываем сетевой адрес машины, на которой работают тесты и почтовый сервер)
    // этот файл в начале тестов загружается по FTP на удаленную машину
    @BeforeMethod
    //добавляем запуск почтового сервера перед методом, чтоб пропадала старая почта
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testRegistration() throws IOException {
        long now = System.currentTimeMillis();
        String email = String.format("user%s@localhost.localdomain", now);
        String user = String.format("user%s", now);
        String password = "password";

        app.registration().start(user, email);
        //ожидаем 2 письма в течении 10 сек
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        //извлекаем конфирм ссылку из письма
        String confirmationLink = findConfirmationLink(mailMessages, email);
        //заканчиваем регистрацию с переходом по линке
        app.registration().finish(confirmationLink, password);
        //проверяем логин юзером
        assertTrue(app.newSession().login(user, password));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        //из потока извлекаем объект письмо, у которого получатель - имейл юзера
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        //для получения регулярного выражения подключаем зависимость от библиотеки verbalregex
        //строим выражение, которое содержит "http://".а после него непробельные символы.один или больше
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        //выбираем регулярным выражением ссылку из письма
        return regex.getText(mailMessage.text);
    }


    //останавливаем сервер в любом лучае, если даже тест упал
    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
