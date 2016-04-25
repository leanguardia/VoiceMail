package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class ChangePasscodeState {

    //Change passcode.
    public void changePasscode(String key, Connection connection) {
      if (key.equals("#")) {
         connection.currentMailbox.setPasscode(connection.accumulatedKeys);
         connection.state = Connection.MAILBOX_MENU;
         connection.speakToAll(connection.MAILBOX_MENU_TEXT);
         connection.accumulatedKeys = "";
      } else
         connection.accumulatedKeys += key;
   }
}
