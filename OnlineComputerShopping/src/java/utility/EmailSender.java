package utility;

import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class is responsible for sending emails.
 * @author krishna
 */
public class EmailSender {

    //gmail SMTP server
    private String SMTPServer = "smtp.gmail.com";
    //SMTP server username, in our case, it is the sender also
    private String from = "ocs.noreply@gmail.com";
    //SMTP password
    private String password = "@Uppsala";

    /**
     * Constructor
     */
    public EmailSender() {
    }
    /**
     * Sends a message email
     * @param to To address
     * @param subject Subject
     * @param content message content
     * @return result:true or false
     * @throws MessagingException
     */
    public boolean SendMail(String to, String subject, String content)
            throws MessagingException {

        try {

            //Create Session
            Properties props = new Properties();
            props.put("mail.smtp.host", this.SMTPServer);
            //Port 465 is SSL port for gmail smtp server
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
            
            Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from,password);
				}
			});
                   
            //Create Message
            Message message = new MimeMessage(session);
            message.setSubject(subject);
            message.setText(content);


            //Set Addresses
            Address fromAddress = new InternetAddress(this.from);
            Address toAddress = new InternetAddress(to);
            message.setFrom(fromAddress);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            //Send
            Transport.send(message);
        } catch (Exception ex) {
            //
        }
        return true;
    }
}
