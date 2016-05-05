package code;

/**
 * Created by Deleguard on 4/25/16.
 */
public class MessageMenuState implements ConnectionState {

    //Respond to the user's selection from message menu.
    public void handle(String key, Connection connection) {
       if (key.equals("1")) {
          String output = "";
          Message m = connection.currentMailbox.getCurrentMessage();
          if (m == null) output += "No messages." + "\n";
          else output += m.getText() + "\n";
          output += Texts.MESSAGE_MENU_TEXT;
          connection.speakToAll(output);
       } else if (key.equals("2")) {
          connection.currentMailbox.saveCurrentMessage();
          connection.speakToAll(Texts.MESSAGE_MENU_TEXT);
       } else if (key.equals("3")) {
          connection.currentMailbox.removeCurrentMessage();
          connection.speakToAll(Texts.MESSAGE_MENU_TEXT);
       } else if (key.equals("4")) {
          connection.state = new MailboxMenuState();
          connection.speakToAll(Texts.MAILBOX_MENU_TEXT);
       }
    }

   @Override
   public void record(String voice, Connection connection) {

   }

   @Override
   public void hangup(Connection connection) {

   }
}
