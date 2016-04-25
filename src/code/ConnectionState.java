package code;

/**
 * Created by Deleguard on 4/22/16.
 */
public class ConnectionState {

    // Try to connect the user with the specified mailbox.
    public void connect(String key, Connection connection) {
      if (key.equals("#")) {
         connection.currentMailbox = connection.system.findMailbox(connection.accumulatedKeys);
         if (connection.currentMailbox != null) {
            connection.state = Connection.RECORDING;
            connection.speakToAll(connection.currentMailbox.getGreeting());
         } else{
            connection.speakToAll("Incorrect mailbox number. Try again!");
         }
         connection.accumulatedKeys = "";
      } else
         connection.accumulatedKeys += key;
   }
}
