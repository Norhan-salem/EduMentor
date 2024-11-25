package com.asu.EduMentor.socialMediaNotifier;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import kong.unirest.*;

public class EmailNotificationFacade implements INotificationManager {
    String fromEmail = "";
    String password = "";
    private List<INotificationObserver> emailObservers;

    public EmailNotificationFacade() {
        this.emailObservers = new ArrayList<>();
    }

    @Override
    public boolean addObserver(INotificationObserver observer) {
        if (observer != null && !emailObservers.contains(observer)) {
            emailObservers.add(observer);
            System.out.println("Observer added successfully.");
            return true;
        }
        System.out.println("Observer already exists or is null.");
        return false;
    }

    @Override
    public boolean removeObserver(INotificationObserver observer) {
        if (observer != null && emailObservers.contains(observer)) {
            emailObservers.remove(observer);
            System.out.println("Observer removed successfully.");
            return true;
        }
        System.out.println("Observer does not exist or is null.");
        return false;
    }

    @Override
    public boolean notifyObserver() throws UnirestException {
        if (emailObservers.isEmpty()) {
            System.out.println("No observers to notify.");
            return false;
        }

        for (INotificationObserver observer : emailObservers) {
            observer.update();
        }
        System.out.println("All observers have been notified.");
        return true;
    }

//    public static JsonNode sendEmail() throws UnirestException {
//        // Replace with your Mailgun domain
//        String domain = "sandbox8278d493f4484a1e9d4c67fd6e770067.mailgun.org";
//
//        // Replace with your Mailgun API key
//        String apiKey = "df542c1e69474a65b21fe74b6f6ec38f-c02fd0ba-2a332b04"; // Example: "key-xxxxxxxxxxxxxxxxxxxxxxx"
//
//        // Replace with your sender email
//        String senderEmail = "edumentorsdp@outlook.com"; // Use a valid email address
//
//        // The recipient email
//        String recipientEmail = "minassgerges@gmail.com"; // Change recipient as needed
//
//        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages")
//                .basicAuth("api", apiKey) // Authenticate using Mailgun API key
//                .queryString("from", senderEmail)
//                .queryString("to", recipientEmail)
//                .queryString("subject", "Hello")
//                .queryString("text", "Testing email via Mailgun API")
//                .asJson();
//
//        return request.getBody(); // Return the response body
//    }


    public void sendEmail(String subject, String content, String recipientEmail) {
        // Fetch Gmail credentials
        try {
            fromEmail = "edumentorsdp@gmail.com"; // Replace with your Gmail address
            password = "rpulostfhgythqfa";     // Replace with your Gmail app password
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load credentials.");
            return;
        }

        if (fromEmail == null || password == null) {
            System.err.println("Email credentials are not set.");
            return;
        }

        // Set properties for Gmail SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); // TLS port
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Ensure TLS v1.2

        // Create session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        // Send email
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send email to: " + recipientEmail);
            e.printStackTrace();
        }
    }
}
