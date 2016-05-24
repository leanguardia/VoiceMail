package UserInterfaces;
import BusinessLogic.Connection;
import UserInterfaces.UserInterface;

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
   public void setConnection(Connection c) {
      connection = c;
   }

   @Override
   public void run()
   {
      boolean more = true;
      while (more)
      {
         String input = scanner.nextLine();
         if (input == null) return;
         if (input.equalsIgnoreCase("H"))
            connection.hangup();
         else if (input.equalsIgnoreCase("Q"))
            more = false;
         else if (input.length() == 1
                 && "1234567890#".indexOf(input) >= 0)
            connection.dial(input);
         else
            connection.record(input);
      }
   }
}
