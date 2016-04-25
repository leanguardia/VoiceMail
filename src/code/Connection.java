package code;

import java.util.ArrayList;

/**
   Connects a phone to the mail system. The purpose of this
   class is to keep track of the state of a connection, since
   the phone itself is just a source of individual key presses.
*/
public class Connection{
   public MailSystem system;
   public Mailbox currentMailbox;
   public String currentRecording;
   public String accumulatedKeys;
   public ContactSystem contactSystem;
   ArrayList<UserInterface> observers;

   public int state;

   public static final int CONNECTED = 1;
   public static final int RECORDING = 2;
   public static final int MAILBOX_MENU = 3;
   public static final int MESSAGE_MENU = 4;
   public static final int CHANGE_PASSCODE = 5;
   public static final int CHANGE_GREETING = 6;
   public static final int CONTACT_MENU = 7;
   public static final int ADD_CONTACT = 8;

   private static final String INITIAL_PROMPT =
           "Enter mailbox number followed by #";
   public static final String MESSAGE_MENU_TEXT =
           "Enter 1 to listen to the current message\n"
                   + "Enter 2 to save the current message\n"
                   + "Enter 3 to delete the current message\n"
                   + "Enter 4 to return to the main menu";
   public static final String MAILBOX_MENU_TEXT =
           "Enter 1 to listen to your messages\n"
                   + "Enter 2 to change your passcode\n"
                   + "Enter 3 to change your greeting\n"
                   + "Enter 4 to watch your contacts";
   public static final String CONTACT_MENU_TEXT =
           "Enter 1 to watch your contacts\n"
                   + "Enter 2 to add a contact\n"
                   + "Enter 3 to delete a contact\n"
                   + "Enter 4 to return to main menu";

   // Construct a Connection object.
   public Connection(MailSystem s,ContactSystem c) {
      system = s;
      contactSystem = c;
      observers = new ArrayList<UserInterface>();
      currentRecording = "";
      accumulatedKeys = "";
      state = CONNECTED;
   }

   // Respond to the user's pressing a key on the phone touchpad
   public void dial(String key) {
      if (state == CONNECTED)
         new ConnectionState().connect(key, this);
      else if (state == RECORDING)
         new LoginState().login(key, this);
      else if (state == CHANGE_PASSCODE)
         new ChangePasscodeState().changePasscode(key, this);
      else if (state == CHANGE_GREETING)
         new ChangeGreetingState().changeGreeting(key, this);
      else if (state == MAILBOX_MENU)
         new MailboxMenuState().mailboxMenu(key, this);
      else if (state == MESSAGE_MENU)
         new MessageMenuState().messageMenu(key, this);
      else if (state == CONTACT_MENU)
         new ContactMenuState().contactMenu(key, this);
      else if (state == ADD_CONTACT)
         new AddContactState().addContact(key, this);
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
      speakToAll(INITIAL_PROMPT);
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

}