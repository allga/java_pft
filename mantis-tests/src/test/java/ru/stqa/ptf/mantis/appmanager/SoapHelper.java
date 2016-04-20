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
                getMantisConnectPort(new URL("http://localhost/mantisbt-1.2.19/api/soap/mantisconnect.php"));
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
        //из своего модельного объекта строим объект требуемой структуры
        IssueData issueData = new IssueData();
        //заполняем объект issueData
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        //ObjectRef - ссылка на проект, у него два параметра: ид и имя проекта 
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        // передаем объект issueData в метод удаленного интерфейса
        mantisConnectPort.mc_issue_add("administrator", "root", issueData);

        return null;
    }
}
