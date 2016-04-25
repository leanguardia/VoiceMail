package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class MessageMenuState {

    //Respond to the user's selection from message menu.
    public void messageMenu(String key, Connection connection) {
       if (key.equals("1")) {
          String output = "";
          Message m = connection.currentMailbox.getCurrentMessage();
          if (m == null) output += "No messages." + "\n";
          else output += m.getText() + "\n";
          output += connection.MESSAGE_MENU_TEXT;
          connection.speakToAll(output);
       } else if (key.equals("2")) {
          connection.currentMailbox.saveCurrentMessage();
          connection.speakToAll(connection.MESSAGE_MENU_TEXT);
       } else if (key.equals("3")) {
          connection.currentMailbox.removeCurrentMessage();
          connection.speakToAll(connection.MESSAGE_MENU_TEXT);
       } else if (key.equals("4")) {
          connection.state = Connection.MAILBOX_MENU;
          connection.speakToAll(connection.MAILBOX_MENU_TEXT);
       }
    }
}
