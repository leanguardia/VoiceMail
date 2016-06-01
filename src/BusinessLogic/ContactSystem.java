package BusinessLogic;

import Databases.DBConnection;
import Databases.MySQLConnection;

import java.util.ArrayList;

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
        int cont = 1;
        if (contacts.isEmpty())
            return "No Contacts\n";
        for(Contact c: contacts){
            allContacts += cont + ". ";
            allContacts += c.getString() + "\n";
            cont += 1;
        }
        return allContacts;
    }

    public void addContact(String fn, String ls, String num) {
        Contact c = new Contact(fn,ls);
        c.addNumber(num);
        contacts.add(c);
        DBConnection mysql = new MySQLConnection();
        mysql.saveContact(fn,ls,num);
    }

    public void removeContact(int contactNumber){
        DBConnection mysql = new MySQLConnection();
        Contact c = contacts.get(contactNumber - 1);
        mysql.deleteContact(c.getName(),c.getLastName(),c.getNumber());
        contacts.remove(c);
    }
    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public int getContactsCount(){
        return contacts.size();
    }
}
