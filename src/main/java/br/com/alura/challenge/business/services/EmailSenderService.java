package br.com.alura.challenge.business.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Service
public class EmailSenderService {

    private final TemplateEngine templateEngine;
    private final String sendGridApiKey;

    public EmailSenderService(TemplateEngine templateEngine, @Value("${sendgrid.api.key}") String sendGridApiKey) {
        this.templateEngine = templateEngine;
        this.sendGridApiKey = sendGridApiKey;
    }

    public void sendEmail(String recipientEmail, String subject, String templateName, Context thymeleafContext) throws IOException {
        Email from = new Email("dev.brunamassi@gmail.com");
        Email to = new Email(recipientEmail);
        String htmlContent = templateEngine.process(templateName, thymeleafContext);
        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("SendGrid Response: " + response.getStatusCode() + " " + response.getBody());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
