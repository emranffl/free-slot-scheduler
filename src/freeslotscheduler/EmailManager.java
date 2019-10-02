/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freeslotscheduler;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author emran
 */
public class EmailManager {

    // Class to handle password reset email and OTP generation
    private final Session session;
    private final String user;
    /*
    The "user" should be the email from which password reset OTP is to be sent from. In this case admin 
    email should be set.
    
    NB: The email should be of only "google.com" domain and "Less secure app access" should be turned "ON" from Google Account Settings
     */
    private final String password;
    private final String subject;
    private final String OTP;
    private String emailMessage;
    private String emailTo;

    public EmailManager() {
        // Initializes all the required variables and connects to the host server
        user = "XXXX@gmail.com";
        password = "XXXX"; // User email password
        subject = "Reset Free Slot Scheduler user password"; // Email subject
        OTP = generateOTP(); // Stores OTP after generation
        try {
            emailMessage = "Hi " + DataManager.rset.getString("Name")
                    + ",\nWe received a request to reset your Free Slot Scheduler user password of ID: "
                    + DataManager.rset.getString("ID") + ".\nEnter the following password reset code to continue: \n\n"
                    + OTP; // Custom email message with generated OTP
            emailTo = DataManager.rset.getString("email"); // Gets the email address from database using ResultSet
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", 2);
        }

        Properties props = new Properties();

        /*
        The "put(String, String)" method sets the host, port, credential authenticator to true and the email encryptor (starttls) to true
        using the pre-initialized "Properties" object instantiated from "Properties" class
         */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        /*
        The "session" object provides access to the protocol providers using the "props" object and an instance of validated credentials
         */
        session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
    }

    public String sendEmail() {
        // Sends composed email and returns a String containing OTP
        try {
            Message email = new MimeMessage(session);

            email.setFrom(new InternetAddress(user));
            email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo)); // Recipient
            email.setSubject(subject);
            email.setText(emailMessage);

            Transport.send(email); // The "send(Message)" method sends the composed message

            return OTP;
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", 2);
        }
        return null;
    }

    private String generateOTP() {
        // Generates and returns OTP

        /*
        String of "numbers" also can be modified with characters such as 'AbcDEf....XyZ' or special characters. In that 
        case the generated OTP will be of mixed or only of characters
         */
        String otpchars = "0ABC1DEF2GHI3JKL4MN5OP6QR7STU8VWX9YZ";
        char[] otp = new char[6]; // The size of OTP is 6, can be modified to meet requirements

        for (int i = 0; i < otp.length; i++) {
            // The "nextInt(int)" of the "Random" class does the heavy work of randomizing the String "otpchars"
            otp[i] = otpchars.charAt(new Random().nextInt(otpchars.length()));
        }
        return new String(otp);
    }
}
