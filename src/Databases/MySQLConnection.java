package Databases;
import BusinessLogic.Contact;
import BusinessLogic.Mailbox;
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

    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private ArrayList<Mailbox> mailboxes = new ArrayList<Mailbox>();

    public MySQLConnection() throws SQLException, ClassNotFoundException {
        connectWithDB();
    }

    private void connectWithDB() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            retrieveMailboxes();
            retrieveMessages();
            retrieveContacts();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void retrieveMailboxes() throws SQLException {
        Statement stmt;
        stmt = conn.createStatement();
        String sql = "SELECT greeting, password FROM Mailboxes";
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            String g = rs.getString("greeting");
            String p = rs.getString("password");
            mailboxes.add(new Mailbox(p,g));
        }
        rs.close();
        stmt.close();
    }

    private void retrieveMessages() throws SQLException {
        Statement stmt;
        stmt = conn.createStatement();
        String sql = "SELECT text, mailbox_id FROM Messages";
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            String text = rs.getString("text");
            int mailbox_id = rs.getInt("mailbox_id");
            mailboxes.get(mailbox_id-1).addMessage(new Message(text));
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

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public ArrayList<Mailbox> getMailboxes() {
        return mailboxes;
    }
}

