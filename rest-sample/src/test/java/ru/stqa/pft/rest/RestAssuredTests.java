package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.apache.http.client.fluent.Executor;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.apache.http.client.fluent.Request.Get;
import static org.apache.http.client.fluent.Request.Post;
import static org.testng.Assert.assertEquals;

/**
 * Created by Olga on 22.04.2016.
 */

// подключаем библиотеки:
// compile 'com.google.code.gson:gson:2.6.1'
// compile 'org.apache.httpcomponents:fluent-hc:4.5.1'
// tested application http://demo.bugify.com/login
// remote API https://bugify.com/api
// compile 'com.jayway.restassured:rest-assured:2.9.0'



public class RestAssuredTests {

    //делаем авторизацию для rest-assured
    @BeforeClass
    public void init() {
        //В соответствии с документацией в качестве имени пользователя указываем API key, пароль пустой
        RestAssured.authentication = RestAssured.basic("LSGjeU4yP1X493ud1hNniA==", "");
    }

    @Test
    public void testCreateIssue() throws IOException {
        //создаем множество багрепортов до создания нового
        Set<Issue> oldIssues = getIssues();
        //объявляем и инициализируем новый багрепорт
        Issue newIssue = new Issue().setSubject("Test issue").setDescription("New test issue");
        // создаем багрепорт и получаем его ИД
        int issueId = createIssue(newIssue);
        //создаем множество багрепортов после создания нового
        Set<Issue> newIssues = getIssues();
        //в множество добавляем созданный багрепорт
        oldIssues.add(newIssue.setId(issueId));
        //сравниваем множества
        assertEquals(newIssues, oldIssues);
    }

    private Set<Issue> getIssues() throws IOException {
        //выполняем запрос с использованием библиотеки rest-assured
        String json = RestAssured.get("http://demo.bugify.com/api/issues.json").asString();
        // нам нужен кусок ответа, соответствующий ключу "issues"
        //напрямую преобразовать ответ не  получится, поэтому парсим JsonElement,
        JsonElement parsed = new JsonParser().parse(json);
        //вытягиваем кусок соответствующий ключу "issues"
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        //преобразовываем полученный кусок ответа в множество issues (первый параметр).
        //Тип данных указываем как для классов с угловыми скобками через new TypeToken<наше множество>(){}.getType() (второй параметр)
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }

    private int createIssue(Issue newIssue) throws IOException {
        //выполняем POST запрос на создание нового багрепорта с помощью библиотеки rest-assured
        String json = RestAssured.given().
                //передаем параметры запроса
                parameter("subject", newIssue.getSubject()).
                parameter("description", newIssue.getDescription()).
                post("http://demo.bugify.com/api/issues.json").asString();
        //парсером анализируем Response Body
        JsonElement parsed = new JsonParser().parse(json);
        //получаем issue_id из Response Body, преобразуем в целое число
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }
}
