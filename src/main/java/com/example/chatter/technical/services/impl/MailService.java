package com.example.chatter.technical.services.impl;

import com.example.chatter.technical.services.contracts.IMailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service

public class MailService implements IMailService {

    private final SendGrid sendGrid;

    @Value("${sendgrid.template.id}")
    private String templateId;

    @Value("${sendgrid.from.email}")
    private String from;

    public MailService(SendGrid sendGrid) {

        this.sendGrid = sendGrid;
    }

    @Async
    public void sendOtpEmail(String to, String otp) throws IOException {
        Mail mail = new Mail();

        Email emailFrom = new Email(from);
        Email emailTo = new Email(to);

        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("otp", otp);
        personalization.addTo(emailTo);

        mail.setFrom(emailFrom);
        mail.setTemplateId(templateId);
        mail.addPersonalization(personalization);
        mail.setSubject("Chatter: YOUR OTP CODE");


        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sendGrid.api(request);

    }
}
