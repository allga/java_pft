package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private void selectContactByID(int id) {
        wd.findElement(By.xpath(".//*[@id='maintable']/tbody//input[@id='" + id + "']")).click();
    }

    public void deleteContact(ContactData contact) {
        selectContactByID(contact.getId());
        deleteSelectedContact();
        submitContactDeletion();
        navigation.gotoHome();
    }

    public void deleteSelectedContact() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void submitContactDeletion() {
        wd.switchTo().alert().accept();
    }

    public void submitContactModification() {
        click(By.xpath("//div[@id='content']/form[1]/input[22]"));
    }

    public void selectContactModificationById(int id) {
        wd.findElement(By.xpath(".//a[@href=\"edit.php?id=" + id + "\"]/img")).click();
    }

    public void modifyContact(int id, ContactData contact) {
        selectContactModificationById(id);
        fillContactForm(contact, false);
        submitContactModification();
        navigation.gotoHome();
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

    public Set<ContactData> getAllContacts() {
        Set<ContactData> contacts = new HashSet<ContactData>();
        List<WebElement> elements = wd.findElements(By.name("entry"));

        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.xpath(".//input")).getAttribute("id"));
            String firstname = element.findElement(By.xpath(".//td[3]")).getText();
            String lastname = element.findElement(By.xpath(".//td[2]")).getText();
            String address = element.findElement(By.xpath(".//td[4]")).getText();
            contacts.add(new ContactData().setId(id).setFirstname(firstname).setLastname(lastname).setAddress(address));
        }
        return contacts;
    }
}
