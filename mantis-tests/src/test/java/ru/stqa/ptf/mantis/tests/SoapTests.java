package ru.stqa.ptf.mantis.tests;

import org.testng.SkipException;
import org.testng.annotations.Test;
import ru.stqa.ptf.mantis.model.Issue;
import ru.stqa.ptf.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * Created by Olga on 20.04.2016.
 */
public class SoapTests extends TestBase{

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        try {
            skipIfNotFixed(0000001);
        } catch (SkipException e) {
            e.printStackTrace();
        }
        Set<Project> projects = app.getSoapHelper().getProjects();
        System.out.println(projects.size());
        for (Project project : projects) {
            System.out.println(project.getName());
        }
    }

    @Test
    public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
        //получаем список проектов
        Set<Project> projects = app.getSoapHelper().getProjects();
        //создаем объект багрепорта
        Issue issue = new Issue().setSummary("Test issue3").
                setDescription("Test issue description").setProject(projects.iterator().next());
        Issue created = app.getSoapHelper().addIssue(issue);
        // сравниваем вновь созданный багрепорт с существующим
        assertEquals(issue.getSummary(), created.getSummary());
    }
}
