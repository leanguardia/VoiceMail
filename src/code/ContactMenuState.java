package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class ContactMenuState {

    public void contactMenu(String key, Connection connection) {
       if(key.equals("1")){
          connection.speakToAll(connection.contactSystem.getAllContacts());
          connection.speakToAll(Connection.CONTACT_MENU_TEXT);
       } else if (key.equals("2")) {
          connection.state = Connection.ADD_CONTACT;
          connection.speakToAll("Format: <first name> <last name> <phone number>, then '#'.");
       } else if (key.equals("3")) {
          connection.speakToAll("Not Implemented");
          connection.state = Connection.MAILBOX_MENU;
          connection.speakToAll(connection.MESSAGE_MENU_TEXT);
       } else if (key.equals("4")) {
          connection.state = Connection.MAILBOX_MENU;
          connection.speakToAll(connection.MAILBOX_MENU_TEXT);
       }
    }
}
