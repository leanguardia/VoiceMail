package BusinessLogic.ConnectionStates;

import BusinessLogic.Connection;
import BusinessLogic.Texts;

/**
 * Created by Deleguard on 4/25/16.
 */
public class ChangePasscodeState implements ConnectionState {

    //Change passcode.
    public void handle(String key, Connection connection) {
      if (key.equals("#")) {
         connection.currentMailbox.setPasscode(connection.accumulatedKeys);
         connection.state = new MailboxMenuState();
         connection.speakToAll(Texts.MAILBOX_MENU_TEXT);
         connection.accumulatedKeys = "";
      } else
         connection.accumulatedKeys += key;
   }

    @Override
    public void record(String voice, Connection connection) {
        connection.currentRecording += voice;
    }

    @Override
    public void hangup(Connection connection) {

    }
}
