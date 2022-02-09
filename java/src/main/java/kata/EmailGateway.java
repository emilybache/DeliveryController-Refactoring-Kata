package kata;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailGateway {

  private static final String SMTP_HOST_NAME = "smtp.gmail.com";
  private static final String SMTP_PORT = "465";
  private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

  public void send(String contactEmail, String subject, String message) {
    try {
      Properties props = new Properties();
      props.put("mail.smtp.host", SMTP_HOST_NAME);
      props.put("mail.smtp.auth", "true");
      props.put("mail.debug", "true");
      props.put("mail.smtp.port", SMTP_PORT);
      props.put("mail.smtp.socketFactory.port", SMTP_PORT);
      props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
      props.put("mail.smtp.socketFactory.fallback", "false");

      Session session = Session.getDefaultInstance(props,
          new Authenticator() {
            protected PasswordAuthentication

            getPasswordAuthentication() {
              return new
                  PasswordAuthentication("admin", "password");
            }
          });

      Message msg = new MimeMessage(session);
      InternetAddress addressFrom = new InternetAddress("noreply@example.com");
      msg.setFrom(addressFrom);

      InternetAddress[] addressTo = {new InternetAddress(contactEmail)};
      msg.setRecipients(Message.RecipientType.TO, addressTo);

      msg.setSubject(subject);
      msg.setContent(message, "text/plain");
      Transport.send(msg);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
