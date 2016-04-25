package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class LoginState {

    // Try to log in the user.
    public void login(String key, Connection connection) {
       if (key.equals("#")) {
          if (connection.currentMailbox.checkPasscode(connection.accumulatedKeys)) {
             connection.state = Connection.MAILBOX_MENU;
             connection.speakToAll(connection.MAILBOX_MENU_TEXT);
          } else{
             connection.speakToAll("Incorrect passcode. Try again!");
          }
          connection.accumulatedKeys = "";
       } else
          connection.accumulatedKeys += key;
    }
}
