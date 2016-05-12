import static org.junit.Assert.*;


import code.Message;
import code.MessageQueue;
import org.junit.Test;

/**
 * Created by Deleguard on 3/23/16.
 */
public class MessageQueueTest {

    @Test
    public void shouldReturnZeroForSizeOfEmptyQueue(){
        MessageQueue empty = new MessageQueue();
        assertEquals(empty.size(),0);
    }

    @Test
    public void shouldReturnOneForSizeOfQueueWithAMessage(){
        MessageQueue queue = new MessageQueue();
        queue.add(new Message("Message 1"));
        assertEquals(queue.size(),1);
    }

    @Test
    public void shouldReturnTwoForSizeOfQueueWithMessages(){
        MessageQueue queue = new MessageQueue();
        queue.add(new Message("Message 1"));
        queue.add(new Message("Message 2"));
        assertEquals(queue.size(),2);
    }

    @Test
    public void shouldReturnOneAfterRemovingAMessageInATheMessagesQueue(){
        MessageQueue queue = new MessageQueue();
        queue.add(new Message("Message 1"));
        queue.add(new Message("Message 2"));
        queue.remove();
        assertEquals(queue.size(),1);
    }

    @Test
    public void shouldReturnNullForGettingPeekFromAnEmptyQueue() {
        MessageQueue queue = new MessageQueue();
        assertEquals(null,queue.peek());
    }

    @Test
    public void shouldReturnFirstMessageAddedInAThreeMessageQueue(){
        MessageQueue queue = new MessageQueue();
        queue.add(new Message("Message 1"));
        queue.add(new Message("Message 2"));
        queue.add(new Message("Message 3"));
        Message message = queue.peek();
        assertEquals("Message 1",message.getText());
    }

    @Test
    public void shouldReturnHeadingMessagesAfterAddingAndRemoving(){
        MessageQueue queue = new MessageQueue();
        queue.add(new Message("Message 1"));
        assertEquals("Message 1",queue.peek().getText());
        queue.add(new Message("Message 2"));
        assertEquals("Message 1",queue.peek().getText());
        queue.remove();
        assertEquals("Message 2",queue.peek().getText());
    }
}