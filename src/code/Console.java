package code;
import java.util.Scanner;

/**
   A telephone that takes simulated keystrokes and voice input
   from the user and simulates spoken text.
*/
public class Console implements Observer {
   private Scanner scanner;
   private Connection connection;

   public Console(Connection c)
   {
      scanner = new Scanner(System.in);
      connection = c;
   }

   public void speak(String output)
   {
      System.out.println(output);
   }

   @Override
   public void update() {
      speak(connection.getDisplayText());
      String input = scanner.nextLine();
      connection.run(input);
      connection.updateObservers();
   }
}
