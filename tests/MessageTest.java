import static org.junit.Assert.*;

import BusinessLogic.Message;

import org.junit.Test;

public class MessageTest{
    @Test
    public void shouldReturnMessageText(){
        Message message = new Message("Message 1");
        assertEquals("Message 1",message.getText());
    }
}
