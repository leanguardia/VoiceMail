package BusinessLogic.ConnectionStates;

import BusinessLogic.Connection;
import BusinessLogic.Texts;

public class RemoveContactState implements ConnectionState {
    private boolean isValidContact(String currentRecording) {
        return currentRecording.split(" ").length >= 3;
    }

    public void handle(String key, Connection connection) {
        if (key.equals("#")) {
            int contactNumber = Integer.parseInt(connection.accumulatedKeys);
            if ( contactNumber <= connection.getContactsCount()) {
                connection.contactSystem.removeContact(contactNumber);
                connection.state = new ContactMenuState();
                connection.speakToAll(Texts.CONTACT_MENU_TEXT);
            } else{
                connection.speakToAll("Invalid contact number, try again!");
                connection.accumulatedKeys = "";
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
