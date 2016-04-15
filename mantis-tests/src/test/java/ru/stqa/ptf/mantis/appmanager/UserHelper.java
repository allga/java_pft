package ru.stqa.ptf.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.ptf.mantis.model.User;
import ru.stqa.ptf.mantis.model.Users;

/**
 * Created by Olga on 15.04.2016.
 */
public class UserHelper extends HelperBase{

    public UserHelper(ApplicationManager app) {
        super(app);
    }


    public User getAnyUserFromBD() {
        Users users = app.db().getUsersFromBD();
        return users.stream().filter((u) -> u.getAccessLevel() != 90).iterator().next();
    }

    public void startResetPassword() {
        click(By.cssSelector("input[value=\"Reset Password\"]"));
    }

    public void login(String username, String password) {
        type(By.name("username"), username);
        type(By.name("password"), password);
        click(By.cssSelector("input[value=\"Login\"]"));
    }

    public User getUserByIdFromBD(int id) {
        Users users = app.db().getUsersFromBD();
        return users.stream().filter((u) -> u.getId() == id).findFirst().get();
    }
}
