package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class AddContactState implements ConnectionState{

    private boolean isValidContact(String currentRecording) {
        return currentRecording.split(" ").length >= 3;
    }

    public void handle(String key, Connection connection) {
       if (key.equals("#")) {
          if (isValidContact(connection.currentRecording)) {
             String[] array = connection.currentRecording.split(" ");
             connection.contactSystem.addContact(array[0],array[1],array[2]);
             connection.state = new ContactMenuState();
             connection.speakToAll(Texts.CONTACT_MENU_TEXT);
          } else{
             connection.speakToAll("Invalid Input, try again!");
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
    public void hangup(Connection connection) {

    }
}
