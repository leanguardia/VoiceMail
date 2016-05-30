package BusinessLogic;

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
    }

    public void removeContact(int contactNumber){
        contacts.remove(contacts.get(contactNumber - 1));
    }
    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public int getContactsCount(){
        return contacts.size();
    }
}
