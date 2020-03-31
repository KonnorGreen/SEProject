package softwareengineering;

// JavaMail
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class textsend {

    public static int sendSMS(String phoneNumber, String carrier) {
        //Random number generator for customer code
        Random rand = new Random();
        int customercode = 1000 + rand.nextInt(1000);
        //Email and password of account sending email
        final String username = "helloWorld4949";
        final String password = "6MQcQEL2Ccgw6SZ";

        //Phone Carriers
        final String tmobile = "@tmomail.net";
        final String sprint = "@messaging.sprintpcs.com";
        final String verizon = "@vtext.com";
        final String att = "@txt.att.net";
        
        final String customerCarrier;
        
        switch(carrier) {
            case "tmobile":
                customerCarrier = tmobile;
                break;
            case "sprint":
                customerCarrier = sprint;
                break;
            case "verizon":
                customerCarrier = verizon;
                break;
            case "att":
                customerCarrier = att;
                break;
            default:
                System.out.println(carrier + " is not a valid carrier!");
                return -1;
        }

        // Phone number you want to send message too
        String phonenumber = phoneNumber;

        Properties prop = new Properties();
        //mail server properties
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
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
            System.out.println("Sending SMS to "+phonenumber+" on carrier "+carrier);
            
            Message message = new MimeMessage(session);
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(phonenumber + customerCarrier)
            );
            //Email Subject
            message.setSubject("Customer Security Code");
            //Email Contents
            message.setText(Integer.toString(customercode));

            Transport.send(message);

            System.out.println("Text Sent!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
        return customercode;
    }

}
