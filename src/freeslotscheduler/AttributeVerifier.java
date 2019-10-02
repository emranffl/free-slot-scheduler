/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freeslotscheduler;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author emran
 */
public class AttributeVerifier {

    // Class to verify input attributes
    public boolean checkId(String id) {
        // Checks if the ID exists in database or not
        try {
            while (DataManager.rset.next()) {
                if (DataManager.rset.getString("ID").equals(id)) {
                    // If ID matches, returns true
                    return true;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", 2);
        }
        return false;
    }

    public boolean checkLoginInfo() {
        // Checks if the credentials match from the ResultSet
        try {
            while (DataManager.rset.next()) {
                if (DataManager.rset.getString("ID").equals(FreeSlotScheduler.lgnid.getText().trim())
                        && DataManager.rset.getString("pass").equals(FreeSlotScheduler.lgnpassword.getText())) {
                    // If credentials match, returns true
                    return true;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", 2);
        }
        return false;
    }

    public boolean checkSearchTableKeyCode(java.awt.event.KeyEvent key) {
        // Checks if the KeyCode matches the following KeyCodes and returns a boolean
        return key.getKeyCode() != KeyEvent.VK_ALT && key.getKeyCode() != KeyEvent.VK_BACK_SLASH
                && key.getKeyCode() != KeyEvent.VK_CAPS_LOCK && key.getKeyCode() != KeyEvent.VK_COLON
                && key.getKeyCode() != KeyEvent.VK_COMMA && key.getKeyCode() != KeyEvent.VK_CONTROL
                && key.getKeyCode() != KeyEvent.VK_DOWN && key.getKeyCode() != KeyEvent.VK_END
                && key.getKeyCode() != KeyEvent.VK_ENTER && key.getKeyCode() != KeyEvent.VK_ESCAPE
                && key.getKeyCode() != KeyEvent.VK_HOME && key.getKeyCode() != KeyEvent.VK_INSERT
                && key.getKeyCode() != KeyEvent.VK_LEFT && key.getKeyCode() != KeyEvent.VK_NUM_LOCK
                && key.getKeyCode() != KeyEvent.VK_PAGE_DOWN && key.getKeyCode() != KeyEvent.VK_PAGE_UP
                && key.getKeyCode() != KeyEvent.VK_PRINTSCREEN && key.getKeyCode() != KeyEvent.VK_RIGHT
                && key.getKeyCode() != KeyEvent.VK_SCROLL_LOCK && key.getKeyCode() != KeyEvent.VK_SEMICOLON
                && key.getKeyCode() != KeyEvent.VK_SHIFT && key.getKeyCode() != KeyEvent.VK_SLASH
                && key.getKeyCode() != KeyEvent.VK_SPACE && key.getKeyCode() != KeyEvent.VK_TAB
                && key.getKeyCode() != KeyEvent.VK_UP && key.getKeyCode() != KeyEvent.VK_WINDOWS;
    }

    public boolean checkPasswordResetInfo(String id, String email) {
        // Checks if the email entered during password reset matches with the one from the ResultSet with corresponding ID
        try {
            if (DataManager.rset.getString("ID").equals(id) && DataManager.rset.getString("email").equals(email)) {
                // If email matches, returns true
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", 2);
        }
        return false;
    }
}
