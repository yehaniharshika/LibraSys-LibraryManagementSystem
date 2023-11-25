package lk.ijse.LibraSys.controller;


import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.String;
import java.util.Properties;

public class SendingMailFormController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextArea txtMessage;

    @FXML
    private TextField txtReceiverEmail;

    @FXML
    private TextField txtSubject;
    @FXML
    private Label lblStatus;
    @FXML
    void bnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
   void btnMailSendOnAction(ActionEvent event) {
     System.out.println("Start");
        lblStatus.setText("sending...");
    Mail mail = new Mail(); //creating an instance of Mail class
        mail.setMsg(txtMessage.getText());//email message
        mail.setTo(txtReceiverEmail.getText()); //receiver's mail
        mail.setSubject(txtSubject.getText()); //email subject

    Thread thread = new Thread(mail);
        thread.start();

        System.out.println("end");
        lblStatus.setText("sended successfully!!!");

}
public static class Mail implements Runnable {
    private String msg;
    private String to;
    private String subject;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean outMail() throws MessagingException, AddressException {
        String from = "mrgreen6013@gmail.com"; //sender's email address
        String host = "localhost";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("librasys9@gmail.com", "uvek xnjx psfb klpg");  // have to change some settings in SMTP
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(from));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject(this.subject);
        mimeMessage.setText(this.msg);
        Transport.send(mimeMessage);
        return true;
    }

    public void run() {
        if (msg != null) {
            try {
                outMail();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("not sent. empty msg!");
        }
    }
}

}