package ru.stqa.ptf.mantis.tests;

import org.testng.SkipException;
import org.testng.annotations.Test;
import ru.stqa.ptf.mantis.appmanager.HttpSession;

import javax.xml.rpc.ServiceException;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * Created by Olga on 13.04.2016.
 */
public class LoginTests extends TestBase {

    @Test
    public void testLogin() throws IOException, ServiceException {
        try {
            skipIfBugifyIssueNotFixed(5);
        } catch (SkipException e) {
            e.printStackTrace();
        }
        HttpSession session = app.newSession();
        assertTrue(session.login("administrator", "root"));
        assertTrue(session.isLoggedInAs("administrator"));
    }
}
