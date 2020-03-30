package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    @Parameter(names = "-c", description = "Group count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "data format")
    public String format;

    public static void main (String[] args) throws IOException {
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

    private void run () throws IOException {
        List<ContactData> contacts = generateContact(count);
        if (format.equals("csv")) {
            saveAsCsv(contacts, new File(file));
        } else if (format.equals("xml")) {
            saveAsXml(contacts, new File(file));
        } else if (format.equals("json")) {
            saveAsJson(contacts, new File(file));
        } else {
            System.out.println("Unrecognized format" + format);
        }
    }

    private List<ContactData> generateContact (int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            String lastname = "Testovich %s";
            String middlename = "Testov %s";
            String company = "google %s";
            String  firstname = "Test %s";
            String adress = "Moscow-city %s";
            String phoneHome = "111111 %s";
            String phoneMobile = "4444444 %s";
            String  phoneWork = "333333333 %s";
            String  emailOne = "12@gmail.com %s";
            String  emailTwo = "qqq@mail.com %s";
            contacts.add(new ContactData(String.format(lastname, i), String.format(firstname, i
            ),  String.format(middlename, i),  String.format(company, i), String.format(adress, i)
                    , String.format(phoneHome, i), String.format(phoneMobile, i),  String.format(phoneWork, i)
                    , String.format(emailOne, i), String.format(emailTwo, i)));
        }
        return contacts;
    }

    private void saveAsJson (List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        Writer writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }

    private void saveAsXml (List<ContactData> contact, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        String xml = xstream.toXML(contact);
        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();
    }

    private void saveAsCsv (List<ContactData> contacts, File file) throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts) {
            writer.write(String.format("%s;%s;%s\n", contact.getLastName(), contact.getFirstname(), contact.getMiddlename()
                    ,contact.getCompany(), contact.getAdress(), contact.getAllPhones(), contact.getAllEmails()));
        }
        writer.close();
    }

}
