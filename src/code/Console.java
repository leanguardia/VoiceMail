package code;
import java.util.Scanner;

/**
   A telephone that takes simulated keystrokes and voice input
   from the user and simulates spoken text.
*/
public class Console implements UserInterface {
   private Scanner scanner;
   private Connection connection;

   public Console(Scanner s)
   {
      scanner = s;
   }

   @Override
   public void speak(String output)
   {
      System.out.println(output);
   }

   @Override
   public void run(Connection c) {
      String input = scanner.nextLine();
      if (input == null) return;
      if (input.equalsIgnoreCase("H"))
         c.hangup();
      else if (input.length() == 1
              && "1234567890#".indexOf(input) >= 0)
         c.dial(input);
      else
         c.record(input);
   }
}
