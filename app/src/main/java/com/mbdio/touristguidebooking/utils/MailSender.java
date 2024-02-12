package com.mbdio.touristguidebooking.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    public static void sendEmail(String recipient, String subject, String body) {
        // Assuming you are sending email from localhost
        String host = "smtp.gmail.com"; // Specify the SMTP host

        // Get system properties
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // SMTP port number
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        // Get the default Session object
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("touristguidebooking@gmail.com", "jueuzajznfxizbro");
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress("touristguidebooking@gmail.com"));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setContent(body, "text/html; charset=utf-8");
//            message.setText(body,"text/html; charset=utf-8");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static String bookingDeniedTemplate(String guideName, String touristName, String date,String Id, String email, String phone) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Booking Request Denied</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; }\n" +
                "        .email-container { max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }\n" +
                "        .email-heading { font-size: 18px; margin-bottom: 20px; }\n" +
                "        .email-body { margin-bottom: 20px; }\n" +
                "        .email-footer { font-size: 16px; text-align: center; margin-top: 20px; color: #888; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"email-container\">\n" +
                "        <div class=\"email-heading\">Booking Request Update</div>\n" +
                "        <div class=\"email-body\">\n" +
                "            Hello Mr./Ms. <strong>"+touristName+"</strong>,<br><br>\n" +
                "            \n" +
                "            We are writing to inform you that your booking request with Mr./Ms. <strong>"+guideName+"</strong> on <strong>"+date+"</strong> cannot be accommodated at this time.<br><br>\n" +
                "            \n" +
                "            This decision is made by the guide based on their availability or other considerations. We understand this may be disappointing and sincerely apologize for any inconvenience this may cause.<br><br>\n" +
                "            \n" +
                "            If you need more information, please contact Mr./Ms. <strong>" + guideName + "</strong> on their phone number <strong>" + phone + "</strong> or at their email <a href=\"mailto:" + email + "\">" + email + "</a>.<br><br>\n" +
                "            \n" +
                "            We value your interest and would like to assist you in finding an alternative guide or exploring other available dates that may suit your needs. Please feel free to reach out to us for further assistance.<br><br>\n" +
                "            \n" +
                "            Thank you for using App. We hope to serve you better in the future.<br><br>\n" +
                "            \n" +
                "            Best regards,<br>\n" +
                "            MBDIoT Team<br>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }
    public static String bookingApprovedTemplate(String guideName, String touristName, String date,String Id, String email, String phone) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Booking Request Approved</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; }\n" +
                "        .email-container { max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }\n" +
                "        .email-heading { font-size: 18px; margin-bottom: 20px; }\n" +
                "        .email-body { margin-bottom: 20px; }\n" +
                "        .email-footer { font-size: 16px; text-align: center; margin-top: 20px; color: #888; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"email-container\">\n" +
                "        <div class=\"email-heading\">Booking Request Approved</div>\n" +
                "        <div class=\"email-body\">\n" +
                "            Hello Mr./Ms. <strong>"+touristName+"</strong>,<br><br>\n" +
                "            \n" +
                "            We are pleased to inform you that your booking request with Mr./Ms. <strong>"+guideName+"</strong> has been approved. We are excited to have you join us and look forward to providing you with an exceptional experience.<br><br>\n" +
                "            \n" +
                "            Details of your booking are as follows:<br>\n" +
                "            - ID: <strong>"+Id+"</strong><br>\n" +
                "            - Date: <strong>"+date+"</strong><br>\n" +
                "            \n" +
                "            Please ensure you arrive on time and have all the necessary documents or items you might need.<br><br>\n" +
                "            \n" +
                "            If you need more information, please contact Mr./Ms. <strong>" + guideName + "</strong> on their phone number <strong>" + phone + "</strong> or at their email <a href=\"mailto:" + email + "\">" + email + "</a>.<br><br>\n" +
                "            \n" +
                "            Or contact us at <strong><a href=\"mailto:touristguidebooking@gmail.com\">TouristGuideBooking@gmail.com</a></strong>.<br><br>\n" +
                "            \n" +
                "            Thank you for choosing us. We look forward to serving you.<br><br>\n" +
                "            \n" +
                "            Best regards,<br>\n" +
                "            MBDIoT Team<br>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }
    public static String bookRequestTemplate(String guideName, String touristName, String date, String email, String phone) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Booking Request</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "        }\n" +
                "        .email-container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 20px auto;\n" +
                "            padding: 20px;\n" +
                "            border: 1px solid #ccc;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "        .email-heading {\n" +
                "            font-size: 18px;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .email-body {\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .email-footer {\n" +
                "            font-size: 16px;\n" +
                "            text-align: center;\n" +
                "            margin-top: 20px;\n" +
                "            color: #888;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"email-container\">\n" +
                "        <div class=\"email-heading\">Booking Request</div>\n" +
                "        <div class=\"email-body\">\n" +
                "            Hello Mr./Ms. <strong>" + guideName + "</strong>,<br><br>\n" +
                "            \n" +
                "            You have received a booking request from Mr./Ms. <strong>" + touristName + "</strong> for the date <strong>" + date + "</strong>. Please log into your account in the app to approve or deny the request.<br><br>\n" +
                "            \n" +
                "            If you need more information, please contact Mr./Ms. <strong>" + touristName + "</strong> on their phone number <strong>" + phone + "</strong> or at their email <a href=\"mailto:" + email + "\">" + email + "</a>.<br><br>\n" +
                "            \n" +
                "            Best regards,<br>\n" +
                "            MBDIoT Team\n" +
                "        </div>\n" +
                "        <div class=\"email-footer\">\n" +
                "            This is an automated message. Please do not reply directly to this email.\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }


}

