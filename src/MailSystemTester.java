import Databases.DBConnection;
import Databases.MySQLConnection;

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

   public static void main(String[] args) {
      DBConnection mysql = new MySQLConnection();
      mysql.saveContact("Que","Raro","3");
   }
}
