/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freeslotscheduler;

import javax.swing.JOptionPane;

/**
 *
 * @author emran
 */
public class DelayThread extends Thread {

    // Thread class to set "Resend" timer 
    private boolean executeable;

    @Override
    public void run() {
        executeable = true;
        // Flag to execute thread

        for (int i = 60; i > 0; i--) {
            if (executeable) {
                ForgotPassword.buttonarray[1].setEnabled(false);
                ForgotPassword.buttonarray[1].setText("Resend in " + (i));
                try {
                    DelayThread.sleep(1000);
                    // Thread sleeps or stops execution for 1000 millisecond or 1 second 
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, ex, "Error", 2);
                }
            } else {
                break;
            }
        }
        // After the loop breaks the "Resend" button property is restored
        ForgotPassword.buttonarray[1].setEnabled(true);
        ForgotPassword.buttonarray[1].setText("Resend");
    }

    public void stopThread() {
        // Sets the flag to false in order to stop thread
        executeable = false;
    }

    public boolean isActive() {
        // Returns boolean if the thread is executing while this method being called
        return executeable;
    }
}
