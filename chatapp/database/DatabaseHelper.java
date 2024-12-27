package chatapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import chatapp.models.Message;

public class DatabaseHelper {
    Connection conn = null;
    final String getAllMessages = "SELECT * FROM messages";
    final String insertMessage = "INSERT INTO messages (sender, message, date) VALUES (?, ?, ?)";
    final String createDatabase = "CREATE DATABASE IF NOT EXISTS __chatapp___java___messages";
    final String useDatabase = "USE __chatapp___java___messages";
    final String createTable = "CREATE TABLE IF NOT EXISTS messages (id INT AUTO_INCREMENT PRIMARY KEY, sender VARCHAR(255), message TEXT, date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    public DatabaseHelper(String mysqlhost, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/" +
                                   "?user="+user+"&password="+password);
        conn.createStatement().execute(createDatabase);
        conn.createStatement().execute(useDatabase);
        conn.createStatement().execute(createTable);
    }
    public Vector<Message> getMessages() throws SQLException {
        PreparedStatement ps = conn.prepareStatement(getAllMessages);
        ResultSet results = ps.executeQuery();
        Vector<Message> messages = new Vector<>();
        while(results.next()){
            messages.add(new Message(results.getString("sender"), results.getString("message"), results.getDate("date")));
        }
        return messages;
    }
    public void addMessageIntoDatabase(Message m) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(insertMessage);
        ps.setString(1, m.username);
        ps.setString(2, m.message);
        ps.setDate(3, new java.sql.Date(m.time.getTime()));
        ps.execute();
    }
}
