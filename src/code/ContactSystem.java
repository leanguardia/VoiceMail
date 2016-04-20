package code;

import java.util.ArrayList;

/**
 * Created by Deleguard on 4/19/16.
 */
public class ContactSystem {
    private ArrayList<Contact> contacts;

    public ContactSystem() {
        contacts = new ArrayList<Contact>();
    }

    public Contact getContact(int index) {
        return contacts.get(index);
    }

    public String getAllContacts() {
        String allContacts="";
        for(Contact c: contacts){
            allContacts += c.getString() + "\n";
        }
        return allContacts;
    }

    public void addContact(String fn, String ls, String num) {
        Contact c = new Contact(fn,ls);
        c.addNumber(num);
        contacts.add(c);
    }

    public void readContact() {
        System.out.printf("Read the Contact");
    }
}
