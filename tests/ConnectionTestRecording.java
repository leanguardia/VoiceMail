import code.*;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ConnectionTestRecording {

    Mailbox currentMailbox;
    MailSystem mailSystem;
    Console phone;
    Connection connection;
    ContactSystem contactSystem;

    private static String MAILBOX_MENU_TEXT = "Enter 1 to listen to your messages\n"
            + "Enter 2 to change your passcode\n"
            + "Enter 3 to change your greeting";

    @Before
    public void setup() {
        currentMailbox = mock(Mailbox.class);
        mailSystem = mock(MailSystem.class);
        phone = mock(Console.class);
        contactSystem = mock(ContactSystem.class);
        connection = new Connection(mailSystem, contactSystem);
        connection.addObserver(phone);
        when(mailSystem.findMailbox("1")).thenReturn(currentMailbox);
    }

    @Test
    public void inLoginShouldVerifyPassShowMessageAndSetStateToMailBoxMenu() {
        when(currentMailbox.checkPasscode("1")).thenReturn(true);

        connection.dial("1");
        connection.dial("#");
        connection.dial("1");
        connection.dial("#");
        verify(phone).speak(MAILBOX_MENU_TEXT);
        assert (connection.isInMailBoxMenu());
    }

    @Test
    public void inLoginShouldVerifyPassReturnFalseAndShowErrorMessage() {
        when(currentMailbox.checkPasscode("2")).thenReturn(false);
        connection.dial("1");
        connection.dial("#");
        connection.dial("2");
        connection.dial("#");
        verify(phone).speak("Incorrect passcode. Try again!");
    }
}