package BusinessLogic;

import BusinessLogic.ConnectionStates.*;
import UserInterfaces.UserInterface;
import javafx.application.Platform;

import java.util.ArrayList;

public class Connection{

   public MailSystem system;
   public Mailbox currentMailbox;
   public String currentRecording;
   public String accumulatedKeys;
   public ContactSystem contactSystem;
   ArrayList<UserInterface> observers;

   public ConnectionState state;

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
      Platform.runLater(new Runnable() {  // Needed to update
         @Override                        // the GUI from a different
         public void run() {              // thread
            for (UserInterface observer : observers)
               observer.speak(s);
         }
      });

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

   public void setContacts(ArrayList<Contact> contacts) {
      contactSystem.setContacts(contacts);
   }
}