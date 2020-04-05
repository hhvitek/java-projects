import email.Email;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    private View          view;
    private WorkerLibrary worker;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Email email = new Email();

    public void setView(View view) {
        this.view = view;
    }

    public void setWorker(WorkerLibrary worker) {
        this.worker = worker;
    }

    public void worker_startWorker(int cycleNo) {

        executorService.execute(
                () -> worker.startWorker(cycleNo)
        );

    }

    public void worker_stopWorker() {
        worker.stop();
    }

    public boolean email_validate(
            String username, char[] password, String host, String port,
            String from, String to, String subject, String body
    ) {

        int portAsInt;
        try {
            portAsInt = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            return false;
        }

        return Email.validate(username, password, host, portAsInt, from, to, subject, body);
    }

    public void email_sendEmail(String username, char[] password, String host, String port,
                                String from, String to, String subject, String body) {
        if (email_validate(username, password, host, port, from, to, subject, body)) {
            email.setSmptUserName(username);
            email.setSmtpPassword(password);
            email.setSmtpHost(host);
            email.setSmtpPort(Integer.parseInt(port));

            try {
                email.sendEmail(from, to, subject, body);
            } catch (MessagingException e) {
                view.setStatusInfo(null,
                                   "Failed to send the email",
                                   Status.Error.toString()
                );
            } catch (IllegalArgumentException e) {
                view.setStatusInfo(null,
                                   "Failed to send the email. Wrong parameters",
                                   Status.Error.toString()
                );
            }
        } else {
            view.setStatusInfo(null,
                               "Failed to validate parameters",
                               Status.Error.toString());
        }
    }
}
