package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.apache.http.client.fluent.Request.*;
import static org.testng.Assert.assertEquals;

/**
 * Created by Olga on 22.04.2016.
 */

// подключаем библиотеки:
// compile 'com.google.code.gson:gson:2.6.1'
// compile 'org.apache.httpcomponents:httpclient:4.5.1'
// compile 'org.apache.httpcomponents:fluent-hc:4.5.1'
// tested application http://demo.bugify.com/login
// remote API https://bugify.com/api


public class RestTests {

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
        //выполняем get запрос с парамертами с api
        String json = getExecutor().
                execute(Get("http://demo.bugify.com/api/issues.json")).returnContent().asString();
        // нам нужен кусок ответа, соответствующий ключу "issues"
        //напрямую преобразовать ответ не  получится, поэтому парсим JsonElement,
        JsonElement parsed = new JsonParser().parse(json);
        //вытягиваем кусок соответствующий ключу "issues"
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        //преобразовываем полученный кусок ответа в множество issues (первый параметр).
        //Тип данных указываем как для классов с угловыми скобками через new TypeToken<наше множество>(){}.getType() (второй параметр)
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }

    //для авторизации нужно использовать экзекьютор, в который передается запрос.
    //В соответствии с документацией в качестве имени пользователя указываем API key, пароль пустой
    private Executor getExecutor() {
        return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA==", "");
    }

    private int createIssue(Issue newIssue) throws IOException {
        //выполняем POST запрос на создание нового багрепорта
        String json = getExecutor().
                execute(Post("http://demo.bugify.com/api/issues.json").
                        //передаем параметры запроса
                        bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                                 new BasicNameValuePair("description", newIssue.getDescription()))).
                        returnContent().asString();
        //парсером анализируем Response Body
        JsonElement parsed = new JsonParser().parse(json);
        //получаем issue_id из Response Body, преобразуем в целое число
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }
}
