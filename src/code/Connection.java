package code;

import java.util.ArrayList;

/**
   Connects a phone to the mail system. The purpose of this
   class is to keep track of the state of a connection, since
   the phone itself is just a source of individual key presses.
*/
public class Connection implements Subject{
   private MailSystem system;
   private Mailbox currentMailbox;
   private String currentRecording;
   private String accumulatedKeys;
   private String displayText;
   ArrayList<Observer> observers;

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

   // Respond to the user's pressing a key on the phone touchpad
   public void dial(String key) {
      if (state == CONNECTED)
         connect(key);
      else if (state == RECORDING)
         login(key);
      else if (state == CHANGE_PASSCODE)
         changePasscode(key);
      else if (state == CHANGE_GREETING)
         changeGreeting(key);
      else if (state == MAILBOX_MENU)
         mailboxMenu(key);
      else if (state == MESSAGE_MENU)
         messageMenu(key);
   }

   public void run(String input) {
      if (input.equalsIgnoreCase("H"))
         hangup();
      else if (input.length() == 1 && "1234567890#".indexOf(input) >= 0)
         dial(input);
      else
         record(input);
   }

   //The user hangs up the phone.
   public void hangup() {
      if (state == RECORDING) {
         currentMailbox.addMessage(new Message(currentRecording));
      }
      resetConnection();
   }

   // Record voice.
   public void record(String voice) {
      if (state == RECORDING || state == CHANGE_GREETING)
         currentRecording += voice;
   }

   @Override
   public void addObserver(Observer o) {
      observers.add(o);
   }

   @Override
   public void removeObserver(Observer o) {
      observers.remove(o);
   }

   @Override
   public void updateObservers() {
      for (Observer observer : observers)
         observer.update();
   }

    // Reset the connection to the initial state and prompt for mailbox number
   private void resetConnection() {
      currentRecording = "";
      accumulatedKeys = "";
      state = CONNECTED;
      displayText = INITIAL_PROMPT;
   }

   public boolean isConnected() {
      return state == CONNECTED;
   }

   public boolean isRecording() {
      return state == RECORDING;
   }

   public boolean isInMailBoxMenu() {
      return state == MAILBOX_MENU;
   }

   public boolean isInMessageMenu() {
      return state == MESSAGE_MENU;
   }

   public boolean isInChangePassword() {
      return state == CHANGE_PASSCODE;
   }

   public boolean isInChangeGreeting() {
      return state == CHANGE_GREETING;
   }

   public String getDisplayText(){
      return displayText;
   }

    // Try to connect the user with the specified mailbox.
   private void connect(String key) {
      if (key.equals("#")) {
         currentMailbox = system.findMailbox(accumulatedKeys);
         if (currentMailbox != null) {
            state = RECORDING;
            displayText = currentMailbox.getGreeting();
         } else{
            displayText = "Incorrect mailbox number. Try again!";
         }
         accumulatedKeys = "";
      } else
         accumulatedKeys += key;
   }

    // Try to log in the user.
   private void login(String key) {
      if (key.equals("#")) {
         if (currentMailbox.checkPasscode(accumulatedKeys)) {
            state = MAILBOX_MENU;
            displayText = MAILBOX_MENU_TEXT;
         } else{
            displayText = "Incorrect passcode. Try again!";
         }
         accumulatedKeys = "";
      } else
         accumulatedKeys += key;
   }


    // Respond to the user's selection from mailbox menu.
   private void mailboxMenu(String key) {
      if (key.equals("1")) {
         state = MESSAGE_MENU;
         displayText = MESSAGE_MENU_TEXT;
      } else if (key.equals("2")) {
         state = CHANGE_PASSCODE;
         displayText = "Enter new passcode followed by the # key";
      } else if (key.equals("3")) {
         state = CHANGE_GREETING;
         displayText = "Record your greeting, then press the # key";
      }
   }

    //Change passcode.
   private void changePasscode(String key) {
      if (key.equals("#")) {
         currentMailbox.setPasscode(accumulatedKeys);
         state = MAILBOX_MENU;
         displayText = MAILBOX_MENU_TEXT;
         accumulatedKeys = "";
      } else
         accumulatedKeys += key;
   }

    // Change greeting.
   private void changeGreeting(String key) {
      if (key.equals("#")) {
         currentMailbox.setGreeting(currentRecording);
         currentRecording = "";
         state = MAILBOX_MENU;
         displayText = MAILBOX_MENU_TEXT;
      }
   }

    //Respond to the user's selection from message menu.
   private void messageMenu(String key) {
      if (key.equals("1")) {
         String output = "";
         Message m = currentMailbox.getCurrentMessage();
         if (m == null) output += "No messages." + "\n";
         else output += m.getText() + "\n";
         output += MESSAGE_MENU_TEXT;
         displayText = output;
      } else if (key.equals("2")) {
         currentMailbox.saveCurrentMessage();
         displayText = MESSAGE_MENU_TEXT;
      } else if (key.equals("3")) {
         currentMailbox.removeCurrentMessage();
         displayText = MESSAGE_MENU_TEXT;
      } else if (key.equals("4")) {
         state = MAILBOX_MENU;
         displayText = MAILBOX_MENU_TEXT;
      }
   }
}