import static org.junit.Assert.*;

import code.Mailbox;
import code.Message;
import org.junit.Test;


public class MailboxTest {

    @Test
    public void shouldReturnGreeting(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        assertEquals("Greeting",mailbox.getGreeting());
    }

    @Test
    public void shouldReturnTrueForCorrectPassworw(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        assertTrue(mailbox.checkPasscode("Password"));
    }

    @Test
    public void shouldReturnFalseForIncorrectPassworw(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        assertFalse(mailbox.checkPasscode("passnote"));
    }

    @Test
    public void shouldReturnNullCurrentMessageInEmptyMailBox(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        assertNull(mailbox.getCurrentMessage());
    }

    @Test
    public void shouldReturnCurrentMessageInAOneMessageMailBox(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        Message message = new Message("Message 1");
        mailbox.addMessage(message);
        assertEquals(message,mailbox.getCurrentMessage());
    }

    @Test
    public void shouldReturnNullIfMailboxHasNoMessages(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        assertNull(mailbox.removeCurrentMessage());
    }

    @Test
    public void shouldRemoveCurrentMessageFromKeptMessages(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        Message message = new Message("Message 1");
        mailbox.addMessage(message);
        mailbox.saveCurrentMessage();
        assertEquals(message, mailbox.removeCurrentMessage());
    }

    @Test
    public void shouldGetTheCurrentMessageThanRemoveItAndReturnNull(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        Message message = new Message("Message 1");
        mailbox.addMessage(message);
        assertEquals(message,mailbox.getCurrentMessage());
        mailbox.removeCurrentMessage();
        assertNull(mailbox.getCurrentMessage());
    }

    @Test
    public void shouldGetCurrentMessageFromKeptMessagesIfNoMoreNewOnes(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        Message message = new Message("Message 1");
        mailbox.addMessage(message);
        assertEquals(message,mailbox.getCurrentMessage());
        mailbox.saveCurrentMessage();
        assertEquals(message, mailbox.getCurrentMessage());
    }

    @Test
    public void shouldGetCurrentMessageFromNewMessagesIfThereAreNewOnes(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        Message message = new Message("Message 1");
        Message message2 = new Message("Message 2");
        mailbox.addMessage(message);
        mailbox.addMessage(message2);
        mailbox.saveCurrentMessage();
        assertEquals(message2,mailbox.getCurrentMessage());
    }

    @Test
    public void shouldSetANewPassword(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        mailbox.setPasscode("NewPassword");
        assertTrue(mailbox.checkPasscode("NewPassword"));
    }

    @Test
    public void shouldSetANewGreeeting(){
        Mailbox mailbox = new Mailbox("Password","Greeting");
        mailbox.setGreeting("NewGreeting");
        assertEquals("NewGreeting",mailbox.getGreeting());
    }
}
