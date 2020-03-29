package serv;

// JavaMail
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class textsend {

    public static void main(String[] args) {
        //Random number generator for customer code
        Random rand = new Random();
        int customercode = 1000 + rand.nextInt(1000);
        //Email and password of account sending email
        final String username = "xxxx";
        final String password = "xxxx";

        //Phone Carriers
        final String tmobile = "@tmomail.net";
        final String sprint = "@messaging.sprintpcs.com";
        final String verizon = "@vtext.com";
        final String att = "@txt.att.net";

        // Phone number you want to send message too
        String phonenumber = "xxxxxxxxxx";

        Properties prop = new Properties();
        //mail server properties
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        //Authenticate login for email server
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(phonenumber + sprint)
            );
            //Email Subject
            message.setSubject("Customer Security Code");
            //Email Contents
            message.setText(Integer.toString(customercode));

            Transport.send(message);

            System.out.println("Text Sent!");

        } catch (MessagingException e) {
        }
    }

}
