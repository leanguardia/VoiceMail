package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class MailboxMenuState {

    // Respond to the user's selection from mailbox menu.
    public void mailboxMenu(String key, Connection connection) {
       if (key.equals("1")) {
          connection.state = Connection.MESSAGE_MENU;
          connection.speakToAll(connection.MESSAGE_MENU_TEXT);;
       } else if (key.equals("2")) {
          connection.state = Connection.CHANGE_PASSCODE;
          connection.speakToAll("Enter new passcode followed by the # key");
       } else if (key.equals("3")) {
          connection.state = Connection.CHANGE_GREETING;
          connection.speakToAll("Record your greeting, then press the # key");
       } else if (key.equals("4")) {
          connection.state = Connection.CONTACT_MENU;
          connection.speakToAll(connection.CONTACT_MENU_TEXT);
       }
    }
}
