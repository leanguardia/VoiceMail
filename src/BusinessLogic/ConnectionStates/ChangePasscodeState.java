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
         connection.currentMailbox.setPasscode(getNewPassword(connection));
         connection.state = new MailboxMenuState();
         connection.speakToAll(Texts.MAILBOX_MENU_TEXT);
         connection.accumulatedKeys = "";
          connection.currentRecording = "";
      } else
         connection.accumulatedKeys += key;
   }

    private String getNewPassword(Connection c){
        if (!c.accumulatedKeys.isEmpty())
            return c.accumulatedKeys;
        else if (!c.currentRecording.isEmpty()){
            return c.currentRecording;
        }
        return "";
    }

    @Override
    public void record(String voice, Connection connection) {
        connection.currentRecording = voice;
    }

    @Override
    public void hangup(Connection connection) {

    }
}
