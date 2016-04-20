package sample;

import code.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {

    private MailSystem system;
    private Connection connection;
    private UserInterface gui;
    private UserInterface console;

    private FXMLLoader loader;

    public void init(){
        system  = new MailSystem(10);
        connection  = new Connection(system,new ContactSystem());
        console = new Console(new Scanner(System.in));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("sample.fxml").openStream());
        gui = loader.getController();

        connection.addObserver(console);
        connection.addObserver(gui);

        console.setConnection(connection);
        gui.setConnection(connection);

        primaryStage.setTitle("VoiceMail | Leandro Guardia");
        Scene myScene  = new Scene (root);
        primaryStage.setScene(myScene);
        primaryStage.show();
        console.run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
