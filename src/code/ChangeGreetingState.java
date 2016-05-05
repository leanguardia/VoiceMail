package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class ChangeGreetingState implements ConnectionState{

    public void handle(String key, Connection connection) {
       if (key.equals("#")) {
          connection.currentMailbox.setGreeting(connection.currentRecording);
          connection.currentRecording = "";
          connection.state = new MailboxMenuState();
          connection.speakToAll(Texts.MAILBOX_MENU_TEXT);
       }
    }

    @Override
    public void record(String voice, Connection connection) {
        connection.currentRecording += voice;
    }

    @Override
    public void hangup(Connection connection) {

    }
}
