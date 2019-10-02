package freeslotscheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DataManager {

    // Class to interact with the database
    private Connection connector;
    private Statement stmntcreator;
    protected static ResultSet rset;

    public DataManager() {
        // Loads the driver class and obtains the connection to database
        try {
            // Driver classpath used for connecting to database
            Class.forName("com.mysql.jdbc.Driver");
            // Obtains the connection
            connector = DriverManager.getConnection("jdbc:mysql://localhost:3306/free_slot_schedule?zeroDateTimeBehavior=convertToNull", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", 2);
        }
    }

    public void getData(String query) {
        // Gets the data from database using "query" string
        try {
            stmntcreator = connector.createStatement();
            rset = stmntcreator.executeQuery(query); // Stores the data from executed query as data set into ResultSet
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", 2);
        }
    }

    public boolean updateData(String query) {
        // Updates the database using "query" string and returns a boolean
        try {
            stmntcreator = connector.createStatement();
            stmntcreator.executeUpdate(query);
            // If update is executed, returns true
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", 2);
        }
        return false;
    }

    public void close() {
        // Closes the "Connection", "Statement" and "ResultSet" objects 
        if (rset != null) {
            try {
                connector.close();
                stmntcreator.close();
                rset.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex, "Error", 2);
            }
        }
    }
}
