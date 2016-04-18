package ru.stqa.ptf.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.stqa.ptf.mantis.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Olga on 17.04.2016.
 */
public class JamesHelper{

    private ApplicationManager app;

    // для создания юзера используем TelnetClient
    private TelnetClient telnet;
    private InputStream in;
    private PrintStream out;
    private String mailserver;
    private Session mailSession;
    private Store store;

    public JamesHelper(ApplicationManager app) {
        this.app = app;
        this.telnet = new TelnetClient();
        //создаем почтовую сессию
        mailSession = Session.getDefaultInstance(System.getProperties());
    }

    public void createUser(String name, String password) {
        //устанавливаем соединение по telnet
        initTelnetSession();
        //пишем команду добавления юзера
        write("adduser " + name + " " + password);
        //ждем появления текста на консоли
        String result = readUntil("User " + name + " added");
        //разрываем соединение
        closeTelnetSession();
    }

    private void initTelnetSession() {
        //получаем св-ва привелегированного юзера из конфига
        mailserver = app.getProperty("mailserver.host");
        int port = Integer.parseInt(app.getProperty("mailserver.port"));
        String login = app.getProperty("mailserver.adminlogin");
        String password = app.getProperty("mailserver.adminpassword");

        try {
            //устанавливаем соединение с почтовым сервером
            telnet.connect(mailserver, port);
            //получаем входной поток соединения для чтения данных, которые нам отправляет телнет клиент
            in = telnet.getInputStream();
            //получаем выходной поток соединения для отправки команд телнет клиенту
            out = new PrintStream(telnet.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //логин не проходит с первой попытки
        readUntil("Login id:");
        write("");
        readUntil("Password:");
        write("");

        //успешная попытка логина
        readUntil("Login id:");
        write(login);
        readUntil("Password:");
        write(password);

        //Read welcome message
        readUntil("Welcome " + login + ". HELP for a list of commands");
    }

    private String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) in.read();
            //посимвольно читаем данные из входного потока (то что на консоль выводит сервер)
            while (true) {
                System.out.print(ch);
                sb.append(ch);
                if (ch == lastChar) {
                    // сравниваем с заданным шаблоном, если фрагмент прочитан - выходим из ожидания
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(String value) {
        try {
            out.println(value);
            out.flush();
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeTelnetSession() {
        write("quit");
    }

    public List<MailMessage> waitForMail(String username, String password, long timeout) throws MessagingException {
        long now = System.currentTimeMillis();
        //проверяем что текущее время не превышает время ожидания с момента старта
        while (System.currentTimeMillis() < (now + timeout)) {
            //получаем всю почту
            List<MailMessage> allMail = getAllMail(username, password);
            //если есть хоть одно письмо - возвращаем список писем
            if (allMail.size() > 0) {
                //возвращаем полученную почту в тесты
                return allMail;
            }
            //если почты нет - ждем секунду и повторяем цикл
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new Error("No mail :(");
    }

    //извлекаем сообщения из почтового ящика и превращаем их в модельные объекты
    private List<MailMessage> getAllMail(String username, String password) throws MessagingException {
        //РОР3 требует сначала открыть почтовый ящик
        Folder inbox = openInbox(username, password);
        //берем список писем, превращаем в поток, применяем ф-ю, которая превращает их в модельные объекты
        // и собираем поток обратно в список
        List<MailMessage> messages = Arrays.asList(inbox.getMessages()).stream().map((m) -> toModelMail(m)).
                // и собираем поток обратно в список
                collect(Collectors.toList());
        //закрываем почтовый ящик
        closeFolder(inbox);
        return messages;
    }

    //закрываем INBOX и сессию
    private void closeFolder(Folder folder) throws MessagingException {
        //закрываем папку. параметр true - значит что нужно удалить все письма помеченные на удаление (нам он не нужен)
        folder.close(true);
        //закрываем соединение с почтовым сервером
        store.close();
    }

    //открываем почтовый ящик
    private Folder openInbox(String username, String password) throws MessagingException {
        //созданной в конструкторе сессии сообщаем что мы хотим использовать протокол рор3
        store = mailSession.getStore("pop3");
        //устанавливаем соединение по протоколу рор3
        store.connect(mailserver, username, password);
        //получаем доступ к папке INBOX
        Folder folder = store.getDefaultFolder().getFolder("INBOX");
        //открываем INBOX на чтение и на запись
        folder.open(Folder.READ_WRITE);
        //возвращаем открытую папку
        return folder;
    }

    //преобразование реальных писем в модельные
    public static MailMessage toModelMail(Message m) {
        try {
            return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
