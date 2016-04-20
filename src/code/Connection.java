package code;

import java.util.ArrayList;

/**
   Connects a phone to the mail system. The purpose of this
   class is to keep track of the state of a connection, since
   the phone itself is just a source of individual key presses.
*/
public class Connection{
   private MailSystem system;
   private Mailbox currentMailbox;
   private String currentRecording;
   private String accumulatedKeys;
   private ContactSystem contactSystem;
   ArrayList<UserInterface> observers;

   private int state;

   private static final int CONNECTED = 1;
   private static final int RECORDING = 2;
   private static final int MAILBOX_MENU = 3;
   private static final int MESSAGE_MENU = 4;
   private static final int CHANGE_PASSCODE = 5;
   private static final int CHANGE_GREETING = 6;
   private static final int CONTACT_MENU = 7;
   private static final int ADD_CONTACT = 8;

   private static final String INITIAL_PROMPT =
           "Enter mailbox number followed by #";
   private static final String MAILBOX_MENU_TEXT =
           "Enter 1 to listen to your messages\n"
                   + "Enter 2 to change your passcode\n"
                   + "Enter 3 to change your greeting\n"
                   + "Enter 4 to watch your contacts";
   private static final String MESSAGE_MENU_TEXT =
           "Enter 1 to listen to the current message\n"
                   + "Enter 2 to save the current message\n"
                   + "Enter 3 to delete the current message\n"
                   + "Enter 4 to return to the main menu";
   private static final String CONTACT_MENU_TEXT =
           "Enter 1 to watch your contacts\n"
                   + "Enter 2 to add a contact\n"
                   + "Enter 3 to delete a contact\n"
                   + "Enter 4 to return to main menu";

   // Construct a Connection object.
   public Connection(MailSystem s,ContactSystem c) {
      system = s;
      contactSystem = c;
      observers = new ArrayList<UserInterface>();
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
      else if (state == CONTACT_MENU)
         contactMenu(key);
      else if (state == ADD_CONTACT)
         addContact(key);
   }

   private void addContact(String key) {
      if (key.equals("#")) {
         System.out.println("ENTROO!");
         if (isValidContact(currentRecording)) {
            String[] array = currentRecording.split(" ");
            contactSystem.addContact(array[0],array[1],array[2]);
            state = CONTACT_MENU;
            speakToAll(CONTACT_MENU_TEXT);
         } else{
            speakToAll("Invalid Input, try again!");
         }
         accumulatedKeys = "";
      } else
         accumulatedKeys += key;
   }

   private boolean isValidContact(String currentRecording) {
      return currentRecording.split(" ").length >= 3;
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
      if (state == RECORDING || state == CHANGE_GREETING || state == ADD_CONTACT)
         currentRecording += voice;
   }

   public void addObserver(UserInterface o) {
      observers.add(o);
      resetConnection();
   }

   public void removeObserver(UserInterface o) {
      observers.remove(o);
   }

   public void speakToAll(String s) {
      for (UserInterface observer : observers)
         observer.speak(s);
   }

    // Reset the connection to the initial state and prompt for mailbox number
   private void resetConnection() {
      currentRecording = "";
      accumulatedKeys = "";
      state = CONNECTED;
      speakToAll(INITIAL_PROMPT);;
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

    // Try to connect the user with the specified mailbox.
   private void connect(String key) {
      if (key.equals("#")) {
         currentMailbox = system.findMailbox(accumulatedKeys);
         if (currentMailbox != null) {
            state = RECORDING;
            speakToAll(currentMailbox.getGreeting());
         } else{
            speakToAll("Incorrect mailbox number. Try again!");
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
            speakToAll(MAILBOX_MENU_TEXT);
         } else{
            speakToAll("Incorrect passcode. Try again!");
         }
         accumulatedKeys = "";
      } else
         accumulatedKeys += key;
   }


    // Respond to the user's selection from mailbox menu.
   private void mailboxMenu(String key) {
      if (key.equals("1")) {
         state = MESSAGE_MENU;
         speakToAll(MESSAGE_MENU_TEXT);;
      } else if (key.equals("2")) {
         state = CHANGE_PASSCODE;
         speakToAll("Enter new passcode followed by the # key");
      } else if (key.equals("3")) {
         state = CHANGE_GREETING;
         speakToAll("Record your greeting, then press the # key");
      } else if (key.equals("4")) {
         state = CONTACT_MENU;
         speakToAll(CONTACT_MENU_TEXT);
      }
   }

    //Change passcode.
   private void changePasscode(String key) {
      if (key.equals("#")) {
         currentMailbox.setPasscode(accumulatedKeys);
         state = MAILBOX_MENU;
         speakToAll(MAILBOX_MENU_TEXT);
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
         speakToAll(MAILBOX_MENU_TEXT);
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
         speakToAll(output);
      } else if (key.equals("2")) {
         currentMailbox.saveCurrentMessage();
         speakToAll(MESSAGE_MENU_TEXT);
      } else if (key.equals("3")) {
         currentMailbox.removeCurrentMessage();
         speakToAll(MESSAGE_MENU_TEXT);
      } else if (key.equals("4")) {
         state = MAILBOX_MENU;
         speakToAll(MAILBOX_MENU_TEXT);
      }
   }

   private void contactMenu(String key) {
      if(key.equals("1")){
         speakToAll(contactSystem.getAllContacts());
         speakToAll(CONTACT_MENU_TEXT);
      } else if (key.equals("2")) {
         state = ADD_CONTACT;
         speakToAll("Format: <first name> <last name> <phone number>, then '#'.");
      } else if (key.equals("3")) {
         speakToAll("Not Implemented");
         state = MAILBOX_MENU;
         speakToAll(MESSAGE_MENU_TEXT);
      } else if (key.equals("4")) {
         state = MAILBOX_MENU;
         speakToAll(MAILBOX_MENU_TEXT);
      }
   }
}