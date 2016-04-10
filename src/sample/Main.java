package sample;

import code.Connection;
import code.MailSystem;
import code.Observer;
import code.Console;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("VoiceMail | Leandro Guardia");

        Scene myScene  = new Scene (root);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    @Override
    public void init(){
        System.out.print("Bienvenido al programa :P\n");
        MailSystem mailSystem = new MailSystem(10);
        Connection connection = new Connection(mailSystem);
        Observer console = new Console(connection);
        connection.addObserver(console);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
