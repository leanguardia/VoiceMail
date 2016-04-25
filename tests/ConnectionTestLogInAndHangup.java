import code.*;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ConnectionTestLogInAndHangup {

    Mailbox currentMailbox;
    MailSystem mailSystem;
    Console phone;
    Connection connection;
    ContactSystem contactSystem;

    @Before
    public void setup() {
        currentMailbox = mock(Mailbox.class);
        mailSystem = mock(MailSystem.class);
        phone = mock(Console.class);
        contactSystem = mock(ContactSystem.class);
        connection = new Connection(mailSystem,contactSystem);
        connection.addObserver(phone);
    }

    @Test
    public void resetConnectionShouldShowInitialPromotAndSetStateToConnected() {
        verify(phone).speak("Enter mailbox number followed by #");
        assert (connection.isConnected());
    }

    @Test
    public void asConnectedDial1shouldGetMailBoxSpeakGreetingAndSetStateToRecording() {
        when(mailSystem.findMailbox("1")).thenReturn(currentMailbox);
        connection.dial("1");
        connection.dial("#");
        verify(phone).speak(currentMailbox.getGreeting());
        assert (connection.isRecording());
    }

    @Test
    public void asConnectedDial10shouldGetNullSpeakErrorMsjAndSetStateToRecording() {
        when(mailSystem.findMailbox("10")).thenReturn(null);
        connection.dial("1");
        connection.dial("#");
        verify(phone).speak("Incorrect mailbox number. Try again!");
    }

    @Test
    public void afterRecordingHangoutShouldSaveAMessageAndResetConnection(){
        String msgText = "This is a new message.";
        when(mailSystem.findMailbox("1")).thenReturn(currentMailbox);
        when(currentMailbox.checkPasscode("1")).thenReturn(true);
        when(currentMailbox.getCurrentMessage()).thenReturn(new Message(msgText));

        connection.dial("1");
        connection.dial("#");
        connection.dial(msgText);
        connection.hangup();
        connection.dial("1");
        connection.dial("#");
        connection.dial("1");
        connection.dial("#");
        connection.dial("1");
        connection.dial("1");
        String MESSAGE_MENU_TEXT = "Enter 1 to listen to the current message\n"
                + "Enter 2 to save the current message\n"
                + "Enter 3 to delete the current message\n"
                + "Enter 4 to return to the main menu";
        verify(phone).speak(msgText+"\n"+ MESSAGE_MENU_TEXT);
    }
}
