package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class ContactMenuState implements ConnectionState {

    public void handle(String key, Connection connection) {
       if(key.equals("1")){
          connection.speakToAll(connection.contactSystem.getAllContacts());
          connection.speakToAll(Connection.CONTACT_MENU_TEXT);
       } else if (key.equals("2")) {
          connection.state = new AddContactState();
          connection.speakToAll("Format: <first name> <last name> <phone number>, then '#'.");
       } else if (key.equals("3")) {
          connection.speakToAll("Not Implemented");
          connection.state = new MailboxMenuState();
          connection.speakToAll(connection.MAILBOX_MENU_TEXT);
       } else if (key.equals("4")) {
          connection.state = new MailboxMenuState();
          connection.speakToAll(connection.MAILBOX_MENU_TEXT);
       }
    }

   @Override
   public void record(String voice, Connection connection) {

   }

   @Override
   public void hangup(Connection connection) {

   }
}
