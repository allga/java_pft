package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga on 01.04.2016.
 */
public class ContactDataGenerator {

    public static void main(String[] args) throws IOException {
        int count = Integer.parseInt(args[0]);
        File file = new File(args[1]);

        List<ContactData> contacts = generateContacts(count);
        save(contacts, file);
    }

    private static void save(List<ContactData> contacts, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts) {
            writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstname(), contact.getLastname(),
                    contact.getCompany(), contact.getAddress(),
                    contact.getHomephone(), contact.getMobilephone(), contact.getWorkphone(),
                    contact.getEmail1(), contact.getEmail2(), contact.getEmail3()));
        }
        writer.close();
    }

    private static List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData().
                    setFirstname(String.format("Leonid%s", i)).setLastname(String.format("Ivanov%s", i)).
                    setCompany(String.format("Noosphere%s", i)).setAddress(String.format("Shevchenko, 5%s", i)).
                    setHomephone(String.format("56-373-22-7%s", i)).
                    setMobilephone(String.format("50-362-88-8%s", i)).
                    setWorkphone(String.format("56-744-99-9%s", i)).
                    setEmail1(String.format("email1_%s.gmail.com", i)).
                    setEmail2(String.format("email2_%s.hotmail.com", i)).
                    setEmail3(String.format("email3_%s.yahoo.com", i)));
        }
        return contacts;
    }
}
