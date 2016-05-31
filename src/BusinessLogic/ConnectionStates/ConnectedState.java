package BusinessLogic.ConnectionStates;

import BusinessLogic.Connection;

/**
 * Created by Deleguard on 4/22/16.
 */
public class ConnectedState implements ConnectionState {

    // Try to handle the user with the specified mailbox.
    @Override
    public void handle(String key, Connection connection) {
      if (key.equals("#")) {
         connection.currentMailbox = connection.system.findMailbox(connection.accumulatedKeys);
         if (connection.currentMailbox != null) {
            connection.state = new RecordingState();
            connection.speakToAll(connection.currentMailbox.getGreeting());
         } else{
            connection.speakToAll("Incorrect mailbox number. Try again!");
         }
         connection.accumulatedKeys = "";
      } else
         connection.accumulatedKeys += key;
   }

    @Override
    public void record(String voice, Connection connection) {
    }

    @Override
    public void hangup(Connection connection) {

    }


}
