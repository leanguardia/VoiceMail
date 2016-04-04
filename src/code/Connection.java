package code;

/**
   Connects a phone to the mail system. The purpose of this
   class is to keep track of the state of a connection, since
   the phone itself is just a source of individual key presses.
*/
public class Connection {
   private MailSystem system;
   private Mailbox currentMailbox;
   private String currentRecording;
   private String accumulatedKeys;
   private int state;

   private static final int CONNECTED = 1;
   private static final int RECORDING = 2;
   private static final int MAILBOX_MENU = 3;
   private static final int MESSAGE_MENU = 4;
   private static final int CHANGE_PASSCODE = 5;
   private static final int CHANGE_GREETING = 6;

   private static final String INITIAL_PROMPT =
           "Enter mailbox number followed by #";
   private static final String MAILBOX_MENU_TEXT =
           "Enter 1 to listen to your messages\n"
                   + "Enter 2 to change your passcode\n"
                   + "Enter 3 to change your greeting";
   private static final String MESSAGE_MENU_TEXT =
           "Enter 1 to listen to the current message\n"
                   + "Enter 2 to save the current message\n"
                   + "Enter 3 to delete the current message\n"
                   + "Enter 4 to return to the main menu";

   // Construct a Connection object.
   public Connection(MailSystem s) {
      system = s;
      resetConnection();
   }

    // Reset the connection to the initial state and prompt for mailbox number
   private String resetConnection() {
      currentRecording = "";
      accumulatedKeys = "";
      state = CONNECTED;
      return INITIAL_PROMPT;
   }

   //The user hangs up the phone.
   public String hangup() {
      if (state == RECORDING) {
         currentMailbox.addMessage(new Message(currentRecording));
      }
      return resetConnection();
   }

    // Respond to the user's pressing a key on the phone touchpad
   public String dial(String key) {
      if (state == CONNECTED)
         return connect(key);
      else if (state == RECORDING)
         return login(key);
      else if (state == CHANGE_PASSCODE)
         return changePasscode(key);
      else if (state == CHANGE_GREETING)
         return changeGreeting(key);
      else if (state == MAILBOX_MENU)
         return mailboxMenu(key);
      else if (state == MESSAGE_MENU)
         return messageMenu(key);
      return "lalala";
   }

    // Try to connect the user with the specified mailbox.
   private String connect(String key) {
      String resp="";
      if (key.equals("#")) {
         currentMailbox = system.findMailbox(accumulatedKeys);
         if (currentMailbox != null) {
            state = RECORDING;
            resp = currentMailbox.getGreeting();
         } else
            resp = "Incorrect mailbox number. Try again!";
         accumulatedKeys = "";
      } else
         accumulatedKeys += key;
      return resp;
   }

    // Try to log in the user.
   private String login(String key) {
      String resp = "";
      if (key.equals("#")) {
         if (currentMailbox.checkPasscode(accumulatedKeys)) {
            state = MAILBOX_MENU;
            resp =  MAILBOX_MENU_TEXT;
         } else
            resp = "Incorrect passcode. Try again!";
         accumulatedKeys = "";
      } else
         accumulatedKeys += key;
      return resp;
   }


    // Respond to the user's selection from mailbox menu.
   private String mailboxMenu(String key) {
      if (key.equals("1")) {
         state = MESSAGE_MENU;
         return MESSAGE_MENU_TEXT;
      } else if (key.equals("2")) {
         state = CHANGE_PASSCODE;
         return "Enter new passcode followed by the # key";
      } else if (key.equals("3")) {
         state = CHANGE_GREETING;
         return "Record your greeting, then press the # key";
      }
      return "";
   }

    //Change passcode.
   private String changePasscode(String key) {
      if (key.equals("#")) {
         currentMailbox.setPasscode(accumulatedKeys);
         state = MAILBOX_MENU;
         accumulatedKeys = "";
         return MAILBOX_MENU_TEXT;
      } else
         accumulatedKeys += key;
      return "";
   }

    // Change greeting.
   private String changeGreeting(String key) {
      if (key.equals("#")) {
         currentMailbox.setGreeting(currentRecording);
         currentRecording = "";
         state = MAILBOX_MENU;
         return MAILBOX_MENU_TEXT;
      }
      return "";
   }


    // Record voice.
   public void record(String voice) {
      if (isAbleToRecord())
         currentRecording += voice;
   }

    //Respond to the user's selection from message menu.
   private String messageMenu(String key) {
      if (key.equals("1")) {
         String output = "";
         Message m = currentMailbox.getCurrentMessage();
         if (m == null) output += "No messages." + "\n";
         else output += m.getText() + "\n";
         output += MESSAGE_MENU_TEXT;
         return output;
      } else if (key.equals("2")) {
         currentMailbox.saveCurrentMessage();
         return MESSAGE_MENU_TEXT;
      } else if (key.equals("3")) {
         currentMailbox.removeCurrentMessage();
         return MESSAGE_MENU_TEXT;
      } else if (key.equals("4")) {
         state = MAILBOX_MENU;
         return MAILBOX_MENU_TEXT;
      }
      return "";
   }

   public boolean isAbleToRecord() {
      return state == RECORDING || state == CHANGE_GREETING;
   }
}