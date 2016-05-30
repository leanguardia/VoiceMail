package Databases;
import BusinessLogic.Contact;
import BusinessLogic.Message;

import java.sql.*;
import java.util.ArrayList;

public class MySQLConnection {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/VOICEMAIL";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "wurzel";

    private Connection conn = null;

    private ArrayList<Message> messages = new ArrayList<Message>();
    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    public MySQLConnection() throws SQLException, ClassNotFoundException {
        connectWithDB();
    }

    private void connectWithDB() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            retrieveMessages();
            retrieveContacts();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void retrieveMessages() throws SQLException {
        Statement stmt;
        stmt = conn.createStatement();
        String sql = "SELECT text FROM Messages";
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            String text = rs.getString("text");
            messages.add(new Message(text));
        }
        rs.close();
        stmt.close();

    }
    private void retrieveContacts() throws SQLException {
        Statement stmt;
        stmt = conn.createStatement();
        String sql = "SELECT first_name, last_name, number FROM Contacts";
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            String fn = rs.getString("first_name");
            String ln = rs.getString("last_name");
            String num = rs.getString("number");
            Contact c = new Contact(fn,ln);
            c.addNumber(num);
            contacts.add(c);
        }
        rs.close();
        stmt.close();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }
}

