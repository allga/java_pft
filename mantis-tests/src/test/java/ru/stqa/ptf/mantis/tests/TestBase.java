package ru.stqa.ptf.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.ptf.mantis.appmanager.ApplicationManager;
import ru.stqa.ptf.mantis.model.Issue;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Created by Olga on 28.02.2016.
 */
public class TestBase {

    protected static final ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
        //вызываем FtpHelper
        app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");

    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws IOException {
        app.ftp().restore("config_inc.php.bak", "config_inc.php");
        app.stop();
    }

    public boolean isIssueOpen(int issueId) throws RemoteException, ServiceException, MalformedURLException {
        Issue issue = app.getSoapHelper().getIssueById(issueId);
        System.out.println(issue.toString());
        if ((issue.getStatus().equals("resolved")) || (issue.getStatus().equals("closed")) ||
                (issue.getResolution().equals("fixed"))) {
            return false;
        }
        return true;
    }

    public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}
