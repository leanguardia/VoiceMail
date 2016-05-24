package BusinessLogic.ConnectionStates;

import BusinessLogic.Connection;
import BusinessLogic.ConnectionStates.ConnectionState;
import BusinessLogic.ConnectionStates.MailboxMenuState;
import BusinessLogic.Message;
import BusinessLogic.Texts;

/**
 * Created by Deleguard on 4/25/16.
 */
public class RecordingState implements ConnectionState {

    // Try to log in the user.
    public void handle(String key, Connection connection) {
       if (key.equals("#")) {
          if (connection.currentMailbox.checkPasscode(connection.accumulatedKeys)) {
             connection.state = new MailboxMenuState();
             connection.speakToAll(Texts.MAILBOX_MENU_TEXT);
          } else{
             connection.speakToAll("Incorrect passcode. Try again!");
          }
          connection.accumulatedKeys = "";
       } else
          connection.accumulatedKeys += key;
    }

    @Override
    public void record(String voice, Connection connection) {
        connection.currentRecording += voice;
    }

    @Override
    public void hangup(Connection c) {
        c.currentMailbox.addMessage(new Message(c.currentRecording));
    }
}
