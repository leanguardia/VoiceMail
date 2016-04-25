package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class ChangeGreetingState {

    public void changeGreeting(String key, Connection connection) {
       if (key.equals("#")) {
          connection.currentMailbox.setGreeting(connection.currentRecording);
          connection.currentRecording = "";
          connection.state = Connection.MAILBOX_MENU;
          connection.speakToAll(connection.MAILBOX_MENU_TEXT);
       }
    }
}
