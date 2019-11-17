package email;

import javax.mail.MessagingException;

public interface IEmail {

    void setSmptUserName(String userName);

    void setSmtpPassword(char[] password);

    void setSmtpHost(String host);

    void setSmtpPort(int port);

    void sendEmail(String sender, String recipient, String subject, String body)
            throws IllegalArgumentException, MessagingException;

}
