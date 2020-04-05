package email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email implements IEmail {

    private String username;
    private char[] password;
    private String host = "smtp.gmail.com";
    private int    port = 465;

    private static final Pattern emailPattern = Pattern.compile("\\w+@\\w+");

    @Override
    public void setSmptUserName(String userName) {
        username = userName;
    }

    @Override
    public void setSmtpPassword(char[] password) {
        this.password = password;
    }

    @Override
    public void setSmtpHost(String host) {
        this.host = host;
    }

    @Override
    public void setSmtpPort(int port) {
        this.port = port;
    }

    @Override
    public void sendEmail(String sender, String recipient, String subject, String body)
            throws MessagingException, IllegalArgumentException {
        if (!checkSmtpParameters(username, password, host, port)) {
            throw new IllegalArgumentException("Invalid SMTP parameters");
        }
        if (!checkEmailParameters(sender, recipient, subject, body)) {
            throw new IllegalArgumentException("Invalid email parameters");
        }

        Properties mailServerProperties = setSmtpProperties();

        Session mailSession = Session.getDefaultInstance(mailServerProperties);

        Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(sender));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);
        message.saveChanges();

        try (Transport transport = mailSession.getTransport("smtp")) {
            transport.connect(username, Arrays.toString(password));
            transport.sendMessage(message, message.getAllRecipients());
        }
    }

    public static boolean validate(String username, char[] password, String host, int port,
                                   String from, String to, String subject, String body) {
        if (checkSmtpParameters(username, password, host, port)) {
            return checkEmailParameters(from, to, subject, body);
        }
        return false;
    }

    public static boolean checkSmtpParameters(String username, char[] password, String host,
                                              int port) {
        if (username != null && password != null && host != null && isPort(port)) {
            return !username.isBlank() && password.length > 0 && !host.isBlank();
        }
        return false;
    }

    public static boolean checkEmailParameters(String sender, String recipient, String subject,
                                               String body) {
        if (sender != null && recipient != null && subject != null && body != null) {
            return isEmailAddress(sender) && isEmailAddress(recipient) && !subject.isBlank() &&
                   !body.isBlank();
        }
        return false;
    }

    private Properties setSmtpProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);
        prop.put("mail.smtp.timeout", 5000);
        prop.put("mail.smtp.connectiontimeout", 5000);
        prop.put("mail.smtp.writetimeout", 5000);
        return prop;
    }

    public static boolean isPort(int port) {
        return port > 0 && port < 65535;
    }

    public static boolean isEmailAddress(String email) {
        Matcher m = emailPattern.matcher(email);
        return m.matches();
    }

}
