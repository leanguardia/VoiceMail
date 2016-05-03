package code;

import java.util.ArrayList;

public class Connection{

   public MailSystem system;
   public Mailbox currentMailbox;
   public String currentRecording;
   public String accumulatedKeys;
   public ContactSystem contactSystem;
   ArrayList<UserInterface> observers;

   public ConnectionState state;

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
      state = new ConnectedState();
   }

   // Respond to the user's pressing a key on the phone touchpad
   public void dial(String key) {
      state.handle(key,this);
   }

   //The user hangs up the phone.
   public void hangup() {
      state.hangup(this);
      resetConnection();
   }

   // Record voice.
   public void record(String voice) {
      state.record(voice, this);
   }

   public void addObserver(UserInterface o) {
      observers.add(o);
      resetConnection();
   }

   public void speakToAll(String s) {
      for (UserInterface observer : observers)
         observer.speak(s);
   }

    // Reset the connection to the initial state and prompt for mailbox number
   private void resetConnection() {
      currentRecording = "";
      accumulatedKeys = "";
      state = new ConnectedState();
      speakToAll("Enter mailbox number followed by #");
   }

   public boolean isConnected() {
      return state instanceof ConnectedState;
   }

   public boolean isRecording() {
      return state instanceof RecordingState;
   }

   public boolean isInMailBoxMenu() {
      return state instanceof MailboxMenuState;
   }

   public boolean isInMessageMenu() {
      return state instanceof MessageMenuState;
   }

   public boolean isInChangePassword() {
      return state instanceof ChangePasscodeState;
   }

   public boolean isInChangeGreeting() {
      return state instanceof ChangeGreetingState;
   }

}