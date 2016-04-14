package ru.stqa.ptf.mantis.model;

/**
 * Created by Olga on 14.04.2016.
 */

//модель нашего формата представления почты
public class MailMessage {

    //кому пришло письмо
    public String to;
    //текст письма
    public String text;

    public MailMessage(String to, String text) {
        this.to = to;
        this.text = text;
    }
}
