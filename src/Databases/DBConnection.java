package Databases;

import BusinessLogic.Contact;
import BusinessLogic.Mailbox;

import java.util.ArrayList;


public interface DBConnection {
    void getDataFromDB();

    ArrayList<Mailbox> getMailboxes();
    ArrayList<Contact> getContacts();
    void saveNewMessage(String txt, int mailbox_id);
    void saveKeptMessage(String txt, int mailbox_id);
    void saveContact(String fn,String ln,String number);

    void updateMailboxGreeting(int mailbox_id, String greeting);
    void updateMailboxPassword(int mailbox_id, String password);
}
