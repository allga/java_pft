package ru.stqa.ptf.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.ptf.mantis.model.Issue;
import ru.stqa.ptf.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Olga on 20.04.2016.
 */

//подключаем библиотеку compile 'biz.futureware.mantis:mantis-axis-soap-client:1.2.19'
public class SoapHelper {
    private final ApplicationManager app;

    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        //получаем список проектов
        return new MantisConnectLocator().
                getMantisConnectPort(new URL(app.getProperty("soap.url")));
    }

    public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
        //открываем соединение
        MantisConnectPortType mantisConnectPort = getMantisConnect();
        //получаем список доступных проектов
        ProjectData[] projects = mantisConnectPort.mc_projects_get_user_accessible("administrator", "root");
        //преобразуем полученные данные в модельные объекты
        return Arrays.asList(projects).stream().map((p) -> new Project().setId(p.getId().intValue()).
                setName(p.getName())).collect(Collectors.toSet());
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        //открываем соединение
        MantisConnectPortType mantisConnectPort = getMantisConnect();
        //запрашиваем список возможных категорий багрепортов
        String[] categories = mantisConnectPort.mc_project_get_categories("administrator", "root",
                BigInteger.valueOf(issue.getProject().getId()));
        //из своего модельного объекта строим объект требуемой структуры
        IssueData issueData = new IssueData();
        //заполняем объект issueData
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        //ObjectRef - ссылка на проект, у него два параметра: ид и имя проекта
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        // устанавливаем обязательное поле Category, первую попавшуюся из списка категорий
        issueData.setCategory(categories[0]);
        // передаем объект issueData в метод удаленного интерфейса
        BigInteger issueId = mantisConnectPort.mc_issue_add("administrator", "root", issueData);
        //получаем объект типа IssueData, выполнив запрос:
        IssueData createdIssueData = mantisConnectPort.mc_issue_get("administrator", "root", issueId);
        //преобразуем объект типа IssueData в модельный объект
        return new Issue().setId(createdIssueData.getId().intValue()).setSummary(createdIssueData.getSummary()).
                setDescription(createdIssueData.getDescription()).
                setProject(new Project().
                        setId(createdIssueData.getProject().getId().intValue()).
                        setName(createdIssueData.getProject().getName()));
    }

    public Issue getIssueById(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        //открываем соединение
        MantisConnectPortType mantisConnectPort = getMantisConnect();
        IssueData issue = mantisConnectPort.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
        ObjectRef status = issue.getStatus();
        status.getName();
        return  new Issue().setId(issue.getId().intValue()).setSummary(issue.getSummary()).
                setDescription(issue.getDescription()).setStatus(issue.getStatus().getName()).
                setResolution(issue.getResolution().getName()).setProject(new Project().
                setId(issue.getProject().getId().intValue()).
                setName(issue.getProject().getName()));
    }
}
