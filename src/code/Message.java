package code;
/**
   A message left by the caller.
*/
public class Message
{
   private String text;

   public Message(String messageText)
   {
      text = messageText;
   }

   public String getText()
   {
      return text;
   }
}
