import static org.junit.Assert.*;

import BusinessLogic.Mailbox;
import BusinessLogic.Message;
import org.junit.Test;


public class MailboxTest {

    @Test
    public void shouldReturnGreeting(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        assertEquals("Greeting",mailbox.getGreeting());
    }

    @Test
    public void shouldReturnTrueForCorrectPassworw(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        assertTrue(mailbox.checkPasscode("Password"));
    }

    @Test
    public void shouldReturnFalseForIncorrectPassworw(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        assertFalse(mailbox.checkPasscode("passnote"));
    }

    @Test
    public void shouldReturnNullCurrentMessageInEmptyMailBox(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        assertNull(mailbox.getCurrentMessage());
    }

    @Test
    public void shouldReturnCurrentMessageInAOneMessageMailBox(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        Message message = new Message("Message 1");
        mailbox.addMessage(message);
        assertEquals(message,mailbox.getCurrentMessage());
    }

    @Test
    public void shouldReturnNullIfMailboxHasNoMessages(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        assertNull(mailbox.removeCurrentMessage());
    }

    @Test
    public void shouldRemoveCurrentMessageFromKeptMessages(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        Message message = new Message("Message 1");
        mailbox.addMessage(message);
        mailbox.saveCurrentMessage();
        assertEquals(message, mailbox.removeCurrentMessage());
    }

    @Test
    public void shouldGetTheCurrentMessageThanRemoveItAndReturnNull(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        Message message = new Message("Message 1");
        mailbox.addMessage(message);
        assertEquals(message,mailbox.getCurrentMessage());
        mailbox.removeCurrentMessage();
        assertNull(mailbox.getCurrentMessage());
    }

    @Test
    public void shouldGetCurrentMessageFromKeptMessagesIfNoMoreNewOnes(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        Message message = new Message("Message 1");
        mailbox.addMessage(message);
        assertEquals(message,mailbox.getCurrentMessage());
        mailbox.saveCurrentMessage();
        assertEquals(message, mailbox.getCurrentMessage());
    }

    @Test
    public void shouldGetCurrentMessageFromNewMessagesIfThereAreNewOnes(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        Message message = new Message("Message 1");
        Message message2 = new Message("Message 2");
        mailbox.addMessage(message);
        mailbox.addMessage(message2);
        mailbox.saveCurrentMessage();
        assertEquals(message2,mailbox.getCurrentMessage());
    }

    @Test
    public void shouldSetANewPassword(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        mailbox.setPasscode("NewPassword");
        assertTrue(mailbox.checkPasscode("NewPassword"));
    }

    @Test
    public void shouldSetANewGreeeting(){
        Mailbox mailbox = new Mailbox("Password","Greeting", 1);
        mailbox.setGreeting("NewGreeting");
        assertEquals("NewGreeting",mailbox.getGreeting());
    }
}
