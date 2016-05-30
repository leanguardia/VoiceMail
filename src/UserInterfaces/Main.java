package UserInterfaces;

import BusinessLogic.*;
import Databases.MySQLConnection;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Scanner;

public class Main extends Application {

    private MailSystem system;
    private Connection connection;
    private UserInterface gui;
    private Console console;

    private FXMLLoader loader;

    public void init() throws SQLException, ClassNotFoundException {
        MySQLConnection mySQL = new MySQLConnection();

        system  = new MailSystem(0);
        system.setMailboxes(mySQL.getMailboxes());
        connection  = new Connection(system,new ContactSystem());
        connection.setContacts(mySQL.getContacts());
        console = new Console(new Scanner(System.in));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("UI.fxml").openStream());
        gui = loader.getController();

        connection.addObserver(console);
        connection.addObserver(gui);

        console.setConnection(connection);
        gui.setConnection(connection);

        Thread th = new Thread(consoleThread);
        th.setDaemon(true);
        th.start();

        primaryStage.setTitle("VoiceMail | Leandro Guardia");
        Scene myScene  = new Scene (root);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    Task<Void> consoleThread = new Task<Void>() {
        @Override protected Void call() throws Exception {
            console.run();
            System.out.printf("Console Thread is dead");
            return null;
        }
    };
}
