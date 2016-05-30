import BusinessLogic.*;
import Databases.MySQLConnection;
import UserInterfaces.Console;
import UserInterfaces.UserInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MailSystemTester
{
   private static final int MAILBOX_COUNT = 20;

//   public static void main(String[] args) {
//      MailSystem ms = new MailSystem(MAILBOX_COUNT);
//      Scanner scanner = new Scanner(System.in);
//
//      ContactSystem cs = new ContactSystem();
//      cs.addContact("Leandro","Guardia","70728566");
//      cs.addContact("Juan Carlos","Paniagua","6554433");
//
//      Connection connection = new Connection(ms,cs);
//
//      UserInterface console = new Console(scanner);
////      UserInterfaces window = new GUIController();
//
//      connection.addObserver(console);
////      connection.addObserver(window);
//
//      console.setConnection(connection);
////      window.setConnection(connection);
//
//      console.run();
////      window.run();
//   }

   public static void main(String[] args) throws SQLException, ClassNotFoundException {
      MySQLConnection mysql = new MySQLConnection();
      ArrayList<Mailbox> mailboxes = mysql.getMailboxes();
      for(Mailbox m: mailboxes){
         System.out.println(m.toString());
      }
      ArrayList<Contact> contacts = mysql.getContacts();
      for(Contact c: contacts){
         System.out.println(c.getString());
      }
   }

}
