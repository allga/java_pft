package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
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

    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;


    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        save(contacts, new File(file));
    }

    private void save(List<ContactData> contacts, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts) {
            writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstname(), contact.getLastname(),
                    contact.getCompany(), contact.getAddress(),
                    contact.getHomephone(), contact.getMobilephone(), contact.getWorkphone(),
                    contact.getEmail1(), contact.getEmail2(), contact.getEmail3()));
        }
        writer.close();
    }

    private List<ContactData> generateContacts(int count) {
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
