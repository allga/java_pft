package ru.stqa.ptf.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga on 13.04.2016.
 */
public class HttpSession {

    private final ApplicationManager appManager;
    private final CloseableHttpClient httpclient;

    public HttpSession(ApplicationManager applicationManager) {
        this.appManager = applicationManager;
        //создание нового клиента - новой сессии для работы по протоколу http, он будет отправлять запросы к серверу
        httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
        // setRedirectStrategy - установка стратегии перенаправлений,
        // без нее ответ будет 302 - и нужно будет обрабатывать перенаправление на другую страницу самостоятельно
        //LaxRedirectStrategy - стратегия прикоторой перенаправления выполняются автоматически
    }

    public boolean login(String username, String password) throws IOException {
        HttpPost post = new HttpPost(appManager.getProperty("web.baseUrl") + "/login.php"); //создаем post запрос

        //формируем набор параметров запросаб см. параметры запроса в фаэрбаге
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("secure_session", "on"));
        params.add(new BasicNameValuePair("return", "index.php"));

        //упаковываем параметры запроса и помещаем в запрос
        post.setEntity(new UrlEncodedFormEntity(params));

        //выполнение запроса и получение ответа от сервера в переменную response
        CloseableHttpResponse response = httpclient.execute(post);

        //получение текста ответа на языке html
        String body = getTextFrom(response);
        System.out.println(body);

        // проверка, залогинился ли пользователь
        return body.contains(String.format("<span class=\"italic\">%s</spain>", username));

    }

    private String getTextFrom(CloseableHttpResponse response) throws IOException {
        // читаем тело запроса, после чего закрываем его
        try {
            return EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
    }

    public boolean isLoggedInAs(String username) throws IOException {
        //отправляем get запрос, чтоб перейти на /index.php
        HttpGet get = new HttpGet(appManager.getProperty("web.baseUrl") + "/index.php");

        //получает ответ с html кодом страницы
        CloseableHttpResponse response = httpclient.execute(get);

        //html код заносим в переменную body
        String body = getTextFrom(response);

        //проверяем что мы залогинены юзером username
        return body.contains(String.format("<span class=\"italic\">%s</spain>", username));
    }

}
