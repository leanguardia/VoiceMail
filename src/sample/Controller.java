package sample;

import code.Connection;
import code.UserInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Controller implements UserInterface {
    Connection connection;
    private String input;
    private String recordedText;

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

    public Controller(Connection c){
        input = "";
        connection = c;
    }

    public void runEvent(ActionEvent event)
   {
        input = getInput(event.getSource().toString());
        run(connection);
   }

    @Override
    public void speak(String s) {
        display.setText(s);
    }

    @Override
    public void run(Connection c){
        if(!input.equals("")){
            if (input.equalsIgnoreCase("hangup"))
                connection.hangup();
            else if (input.length() == 1
                    && "1234567890#".indexOf(input) >= 0){
                connection.dial(input);
            }
            else{
                recordedText = textArea.getText();
                connection.record(recordedText);
                textArea.clear();
            }
        }

    }

    private String getInput(String s) {
        return s.split("'")[1];
    }
}

