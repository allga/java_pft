package ru.stqa.ptf.mantis.appmanager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.stqa.ptf.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olga on 14.04.2016.
 */
public class MailHelper {

    private final ApplicationManager app;
    private final Wiser wiser;

    public MailHelper(ApplicationManager app) {
        this.app = app;
        //создаем новый почтовый сервер
        wiser = new Wiser();
    }

    // так как почта ходит не быстро, добавляем ожидание
    //count - кол-во писем, которые должны прийти, timeout - задаем время ожидания
    public List<MailMessage> waitForMail(int count, long timeout) {
        //запоминаем текущее время
        long start = System.currentTimeMillis();
        //пока время ожидания не истекло
        while (System.currentTimeMillis() < start + timeout) {
            // если почты пришло не меньше count
            if (wiser.getMessages().size() >= count) {
                //возвращаем список месседжей (преобразованных в объекты нашей модели)
                return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
            }
            //если почты меньше count
            try {
                //ждем 10 сек и возвращаемся на if
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // если почта не пришла за время timeout, то выбрасываем исключение
        throw new Error("No mail :(");
    }

    //преобразование реальных почтовых сообщений с реального почтового ящика в нашу модель
    // мантис отправляет два письма:
    // 1. администратору о том что зарегистрировался новый юзер
    // 2. пользователю с сылкой для продолжения регистрации
    public static MailMessage toModelMail(WiserMessage m) {
        try {
            //получаем реальный объект
            MimeMessage mm = m.getMimeMessage();
            //получаем список получателей, и оставляем только первого [0], потому что мы знаем что он единственный
            //а так как контент текстовый - преобразуем его в строку
            return new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent());
        // перехват ошибок, которые могут возникнуть при чтении письма,
        // с выводом сообщений на консоль, тогда MailMessage = null
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //запуск почтового сервера
    public void start() {
        wiser.start();
    }

    //останов почтового сервера
    public void stop() {
        wiser.stop();
    }
}
