package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.tests.ViewingContactDataTests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private void selectContactByID(int id) {
        wd.findElement(By.xpath(".//*[@id='maintable']/tbody//input[@id='" + id + "']")).click();
    }

    private void selectAllContacts() {
        wd.findElement(By.id("MassCB")).click();
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

    public void selectContactDetailsById(int id) {
        wd.findElement(By.xpath(".//a[@href=\"view.php?id=" + id + "\"]")).click();

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

    public void deleteContact(ContactData contact) {
        selectContactByID(contact.getId());
        deleteSelectedContact();
        submitContactDeletion();
        contactCache = null;
        navigation.gotoHomePage();
    }

    public void deleteAllContacts() {
        selectAllContacts();
        deleteSelectedContact();
        submitContactDeletion();
        contactCache = null;
        navigation.gotoHomePage();
    }

    public void modifyContact(int id, ContactData contact) {
        selectContactModificationById(id);
        fillContactForm(contact, false);
        submitContactModification();
        contactCache = null;
        navigation.gotoHomePage();
    }

    public void createContact(ContactData contact, boolean creationFlag) {
        navigation.gotoAddNewPage();
        fillContactForm(contact, creationFlag);
        submitContact();
        contactCache = null;
        navigation.gotoHomePage();
    }

    public boolean isThereAContact() {
       return isElementPresent(By.name("selected[]"));
    }

    private Contacts contactCache = null;

    public Contacts getAllContacts() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));

        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.xpath(".//input")).getAttribute("id"));
            String firstname = element.findElement(By.xpath(".//td[3]")).getText();
            String lastname = element.findElement(By.xpath(".//td[2]")).getText();
            String address = element.findElement(By.xpath(".//td[4]")).getText();
            String allEmails = element.findElement(By.xpath(".//td[5]")).getText();
            String allPhones = element.findElement(By.xpath(".//td[6]")).getText();
            contactCache.add(new ContactData().setId(id).setFirstname(firstname).setLastname(lastname).setAddress(address).
                    setAllPhones(allPhones).setAllEmails(allEmails));
        }
        return contactCache;
    }

    public ContactData infoFromEditForm(ContactData contact) {
        selectContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String company = wd.findElement(By.name("company")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String homephone = wd.findElement(By.name("home")).getAttribute("value");
        String mobilephone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workphone = wd.findElement(By.name("work")).getAttribute("value");
        String email1 = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        navigation.gotoHomePage();

        return new ContactData().setId(contact.getId()).setFirstname(firstname).setLastname(lastname).
                setCompany(company).setAddress(address).
                setHomephone(homephone).setMobilephone(mobilephone).setWorkphone(workphone).
                setEmail1(email1).setEmail2(email2).setEmail3(email3);
    }

    public ContactData infoFromDetailsPage(ContactData contact) {
        selectContactDetailsById(contact.getId());
        String allContent = wd.findElement(By.id("content")).getText();
        String content = "";
        List<WebElement> elements = wd.findElements(By.cssSelector("a[target=\"_new\"]"));
        for (WebElement element : elements) {
            String site = element.getText();
            content = allContent.replaceAll(site, "");
            allContent = content;
            content = "";
        }
        content = allContent.replaceAll("\\(\\)","");
        return new ContactData().setId(contact.getId()).setAllContent(content);
    }


    public void mergePhones(ContactData contact) {
        String phone = "";
        if (!contact.getHomephone().equals("")) {
            phone += String.format("H:%s", contact.getHomephone());
        }
        if (!contact.getMobilephone().equals("")) {
            phone += String.format("M:%s", contact.getMobilephone());
        }
        if (!contact.getWorkphone().equals("")) {
            phone += String.format("W:%s", contact.getWorkphone());
        }
        contact.setAllPhones(phone);
    }


    public void mergeEmails(ContactData contact) {
        String emailString = Arrays.asList(contact.getEmail1(), contact.getEmail2(), contact.getEmail3()).
                stream().filter((s) -> ! s.equals("")).map(ViewingContactDataTests::cleaned).
                collect(Collectors.joining("\n"));
        contact.setAllEmails(emailString);
    }
}
