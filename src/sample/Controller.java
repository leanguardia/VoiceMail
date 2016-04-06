package sample;

import java.util.Scanner;
import code.Connection;
import code.MailSystem;
import code.Telephone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Controller {
    private MailSystem mailSystem;
    private Telephone phone;
    private Connection connection;
    private String out;

    @FXML private Label display;
    @FXML private TextArea textArea;
    @FXML private Button one;
    @FXML private Button two;
    @FXML private Button three;
    @FXML private Button four;
    @FXML private Button five;
    @FXML private Button six;
    @FXML private Button seven;
    @FXML private Button eight;
    @FXML private Button nine;
    @FXML private Button zero;
    @FXML private Button asterisk;
    @FXML private Button hash;
    @FXML private Button hangup;
    @FXML private Button recordBtn;

    public Controller(){
        mailSystem = new MailSystem(10);
        phone = new Telephone(new Scanner(System.in));
        connection = new Connection(mailSystem,phone);
    }

    public void run(ActionEvent event)
   {
         String input = getInput(event.getSource().toString()), recordedText;
         if (input.equalsIgnoreCase("hangup"))
            connection.hangup();
         else if (input.length() == 1
            && "1234567890#".indexOf(input) >= 0){
             phone.speak(input);
             connection.dial(input);
         }
         else{
             recordedText = textArea.getText();
             phone.speak(recordedText);
             connection.record(recordedText);
             textArea.clear();
         }
       display.setText(connection.getCurrentMessage());
   }

    private String getInput(String s) {
        return s.split("'")[1];
    }
}

