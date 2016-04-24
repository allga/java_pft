package ru.stqa.ptf.mantis.appmanager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;

import java.io.IOException;

/**
 * Created by Olga on 24.04.2016.
 */
public class BugifyHelper {

    private ApplicationManager app;

    public BugifyHelper(ApplicationManager app) {
        this.app = app;
    }

    public String getIssueStatusByID(int issueId) throws IOException {
        //делаем авторизацию для rest-assured
        //В соответствии с документацией в качестве имени пользователя указываем API key, пароль пустой
        RestAssured.authentication = RestAssured.basic("LSGjeU4yP1X493ud1hNniA==", "");
        //выполняем запрос с использованием библиотеки rest-assured, который возвращает инфо по багрепорте по ИД
        String json = RestAssured.get("http://demo.bugify.com/api/issues/" + issueId + ".json").asString();
        // нам нужен кусок ответа, соответствующий ключу "issues"
        //напрямую преобразовать ответ не  получится, поэтому парсим JsonElement,
        JsonElement parsed = new JsonParser().parse(json);
        //вытягиваем кусок соответствующий ключу "issues"
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        //вытягиваем первый элемент массива, преобразовываем его в JsonObject
        JsonObject issue = issues.getAsJsonArray().get(0).getAsJsonObject();
        //из JsonObject получаем значение статуса, преобразуем его в строку и возвращаем
        return issue.get("state_name").getAsString();
    }


}
