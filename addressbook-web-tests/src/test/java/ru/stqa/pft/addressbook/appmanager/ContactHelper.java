package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by Olga on 29.02.2016.
 */
public class ContactHelper extends HelperBase {

    private NavigationHelper navigation = new NavigationHelper(wd);

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContact() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contact, boolean creation) {
        type(By.name("firstname"), contact.getFirstname());
        type(By.name("lastname"), contact.getLastname());
        type(By.name("company"), contact.getCompany());
        type(By.name("address"), contact.getAddress());
        type(By.name("home"), contact.getHomephone());
        type(By.name("mobile"), contact.getMobilephone());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contact.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
     }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
//        click(By.name("selected[]"));
    }

    public void deleteSelectedContact() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void submitContactDeletion() {
        wd.switchTo().alert().accept();
    }

    public void selectContactModification(int index) {
        wd.findElements(By.xpath(".//*[@id='maintable']/tbody//img[@src='icons/pencil.png']")).get(index).click();
    }

    public void submitContactModification() {
        click(By.xpath("//div[@id='content']/form[1]/input[22]"));
    }

    public void createContact(ContactData contact, boolean creationFlag) {
        navigation.gotoAddNewPage();
        fillContactForm(contact, creationFlag);
        submitContact();
        navigation.gotoHome();
    }


    public boolean isThereAContact() {
       return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }
}
