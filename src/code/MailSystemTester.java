package code;
import sample.GUIController;

import java.util.Scanner;

public class MailSystemTester
{
   private static final int MAILBOX_COUNT = 20;

   public static void main(String[] args)
   {
      MailSystem system = new MailSystem(MAILBOX_COUNT);
      Scanner scanner = new Scanner(System.in);

      Connection connection = new Connection(system);

      UserInterface console = new Console(scanner);
      UserInterface window = new GUIController();

      connection.addObserver(console);
      connection.addObserver(window);

      console.setConnection(connection);
      window.setConnection(connection);

      console.run();
      window.run();
   }
}
