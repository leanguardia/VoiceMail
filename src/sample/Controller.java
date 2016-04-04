package sample;

import code.Connection;
import code.MailSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Controller {
    private MailSystem mailSystem;
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

    public Controller(){
        mailSystem = new MailSystem(10);
        connection = new Connection(mailSystem);
    }

    public void dial(ActionEvent event){
        String str = event.getSource().toString(), input="";
        input += str.charAt(str.length()-2);
        System.out.print(input+"\n");
        if (input.equals("p")) {
            out = connection.hangup();
        }
        else if (input.length() == 1 && "1234567890#".indexOf(input) >= 0) {
            textArea.clear();
            out = connection.dial(input);
        }
        else{
            connection.record(input);
        }
        if (out!="")
            setDisplay(out);
//        System.out.print("1");
    }

    public void setDisplay(String str){
        display.setText(str);
    }
}

