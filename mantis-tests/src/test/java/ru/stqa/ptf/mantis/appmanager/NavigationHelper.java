package ru.stqa.ptf.mantis.appmanager;

import org.openqa.selenium.By;

/**
 * Created by Olga on 15.04.2016.
 */
public class NavigationHelper extends HelperBase{

    public NavigationHelper(ApplicationManager app) {
        super(app);
    }


    public void goToLoginPage() {
        driver.get(app.getProperty("web.baseUrl") + "/login_page.php");
    }

    public void goToManageUserPage() {
        driver.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
    }

    public void goToUserPage(int id) {
        click(By.cssSelector("a[href=\"manage_user_edit_page.php?user_id=" + id +"\"]"));
    }
}
