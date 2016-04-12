package code;
import java.util.Scanner;

/**
   This program tests the mail system. A single phone
   communicates with the program through System.in/System.out.
*/
public class MailSystemTester
{
   private static final int MAILBOX_COUNT = 20;

   public static void main(String[] args)
   {
      MailSystem system = new MailSystem(MAILBOX_COUNT);
      Scanner scanner = new Scanner(System.in);
      UserInterface console = new Console(scanner);
      Connection connection = new Connection(system);
      connection.addObserver(console);
      console.run(connection);
   }
}
