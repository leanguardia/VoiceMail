package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class MailboxMenuState implements ConnectionState {

    // Respond to the user's selection from mailbox menu.
    public void handle(String key, Connection connection) {
       if (key.equals("1")) {
          connection.state = new MessageMenuState();
          connection.speakToAll(connection.MESSAGE_MENU_TEXT);;
       } else if (key.equals("2")) {
          connection.state = new ChangePasscodeState();
          connection.speakToAll("Enter new passcode followed by the # key");
       } else if (key.equals("3")) {
          connection.state = new ChangeGreetingState();
          connection.speakToAll("Record your greeting, then press the # key");
       } else if (key.equals("4")) {
          connection.state = new ContactMenuState();
          connection.speakToAll(connection.CONTACT_MENU_TEXT);
       }
    }

   @Override
   public void record(String voice, Connection connection) {

   }

   @Override
   public void hangup(Connection connection) {

   }
}
