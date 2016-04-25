package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class AddContactState {

    private boolean isValidContact(String currentRecording) {
        return currentRecording.split(" ").length >= 3;
    }

    public void addContact(String key, Connection connection) {
       if (key.equals("#")) {
          if (isValidContact(connection.currentRecording)) {
             String[] array = connection.currentRecording.split(" ");
             connection.contactSystem.addContact(array[0],array[1],array[2]);
             connection.state = Connection.CONTACT_MENU;
             connection.speakToAll(Connection.CONTACT_MENU_TEXT);
          } else{
             connection.speakToAll("Invalid Input, try again!");
          }
          connection.accumulatedKeys = "";
       } else
          connection.accumulatedKeys += key;
    }
}
