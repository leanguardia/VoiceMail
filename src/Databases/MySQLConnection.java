package Databases;
import BusinessLogic.Contact;
import BusinessLogic.Mailbox;
import BusinessLogic.Message;

import java.sql.*;
import java.util.ArrayList;

public class MySQLConnection implements DBConnection {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/VOICEMAIL";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "wurzel";

    private Connection conn = null;

    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private ArrayList<Mailbox> mailboxes = new ArrayList<Mailbox>();

    public MySQLConnection(){
    }

    @Override
    public void getDataFromDB() {
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
        }finally{
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    @Override
    public ArrayList<Mailbox> getMailboxes() {
        return mailboxes;
    }

    @Override
    public void saveNewMessage(String text, int mailbox_id) {
        saveMessage(text,mailbox_id,true);
    }

    @Override
    public void saveKeptMessage(String text, int mailbox_id) {
        saveMessage(text,mailbox_id,false);
    }


    public void saveMessage(String text, int mailbox_id, boolean isNewMessage) {
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            String sql = getSQLstatement(text, mailbox_id, isNewMessage);
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    public void saveContact(String fn, String ln, String number) {
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Contacts (first_name, last_name, number) VALUES (\""
                    + fn + "\",\"" + ln + "\",\"" + number.toString() + "\")";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    public void updateMailboxGreeting(int mailbox_id, String greeting) {

    }

    @Override
    public void updateMailboxPassword(int mailbox_id, String password) {

    }

    private void retrieveMailboxes() throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT id, greeting, password FROM Mailboxes";
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            int id = rs.getInt("id");
            String g = rs.getString("greeting");
            String p = rs.getString("password");
            mailboxes.add(new Mailbox(p,g,id));
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

    private String getSQLstatement(String text, int mailbox_id, boolean isNewMessage) {
        String tableTarget = "Messages";
        if (isNewMessage) {
            tableTarget = "NewMessages";
        }
        return "INSERT INTO " + tableTarget + " (text, mailbox_id) VALUES (\""
                + text + "\"," + mailbox_id + ")";
    }
}

