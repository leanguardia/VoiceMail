package sample;

import code.Connection;
import code.MailSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
    private MailSystem mailSystem;
    private Connection connection;
    private String out;

    @FXML private Label display;
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

    public Controller(){
        mailSystem = new MailSystem(10);
        connection = new Connection(mailSystem);
    }

    public void dial(ActionEvent event){
        String str = event.getSource().toString(), digit="";
        digit += str.charAt(str.length()-2);
        System.out.print(digit+"\n");
        out = connection.dial(digit);
        if (out!="")
            setDisplay(out);
//        System.out.print("1");
    }

    public void setDisplay(String str){
        display.setText(str);
    }
}

