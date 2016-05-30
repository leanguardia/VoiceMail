package BusinessLogic;

import Databases.DBConnection;
import Databases.MySQLConnection;

/**
   A mailbox contains messages that can be listed, kept or discarded.
*/
public class Mailbox
{
   private MessageQueue newMessages;
   private MessageQueue keptMessages;
   private String greeting;
   private String passcode;
   private int mailboxID;

   /**
      Creates Mailbox object.
    * @param aPasscode passcode number
    * @param aGreeting greeting string
    * @param id
    */
   public Mailbox(String aPasscode, String aGreeting, int id)
   {
      passcode = aPasscode;
      greeting = aGreeting;
      newMessages = new MessageQueue();
      keptMessages = new MessageQueue();
      mailboxID = id;
   }

   /**
      Check if the passcode is correct.
      @param aPasscode a passcode to check
      @return true if the supplied passcode matches the mailbox passcode
   */
   public boolean checkPasscode(String aPasscode)
   {
      return aPasscode.equals(passcode);
   }

   /**
      Add a message to the mailbox.
      @param aMessage the message to be added
   */
   public void addMessage(Message aMessage) {
      newMessages.add(aMessage);
      DBConnection mysql = new MySQLConnection();
      mysql.saveNewMessage(aMessage.getText(),mailboxID);
   }

   public void justAddNewMessage(Message aMessage) {
      newMessages.add(aMessage);
   }

   public void justAddKeptMessage(Message aMessage) {
      keptMessages.add(aMessage);
   }

   /**
      Get the current message.
      @return the current message
   */
   public Message getCurrentMessage()
   {
      if (newMessages.size() > 0)
         return newMessages.peek();
      else if (keptMessages.size() > 0)
         return keptMessages.peek();
      else
         return null;
   }

   /**
      Remove the current message from the mailbox.
      @return the message that has just been removed
   */
   public Message removeCurrentMessage()
   {
      if (newMessages.size() > 0){
         return deleteCurrentMessage(newMessages.remove(),true);
      }
      else if (keptMessages.size() > 0){
         return deleteCurrentMessage(keptMessages.remove(), false);
      }
      else
         return null;
   }

   private Message deleteCurrentMessage(Message m, boolean isNewMessage) {
      DBConnection mysql = new MySQLConnection();
      if (isNewMessage) mysql.deleteNewMessage(m.getText(),mailboxID);
      else mysql.deleteKeptMessage(m.getText(),mailboxID);
      return m;
   }

   /**
      Save the current message
   */
   public void saveCurrentMessage() {
      Message m = removeCurrentMessage();
      if (m != null){
         keptMessages.add(m);
         DBConnection mysql = new MySQLConnection();
         mysql.deleteNewMessage(m.getText(),mailboxID);
         mysql.saveKeptMessage(m.getText(),mailboxID);
      }
   }

   /**
      Change mailbox's greeting.
      @param newGreeting the new greeting string
   */
   public void setGreeting(String newGreeting)
   {
      DBConnection mysql = new MySQLConnection();
      mysql.updateMailboxGreeting(mailboxID,newGreeting);
      greeting = newGreeting;


   }



   /**
      Change mailbox's passcode.
      @param newPasscode the new passcode
   */
   public void setPasscode(String newPasscode)
   {
      DBConnection mysql = new MySQLConnection();
      mysql.updateMailboxPassword(mailboxID,newPasscode);
      passcode = newPasscode;
   }

   /**
      Get the mailbox's greeting.
      @return the greeting
   */
   public String getGreeting()
   {
      return greeting;
   }

   public void setNewMessages(MessageQueue newMessages) {
      this.newMessages = newMessages;
   }
   public void setKeptMessages(MessageQueue keptMessages) {
      this.keptMessages = keptMessages;
   }

}
