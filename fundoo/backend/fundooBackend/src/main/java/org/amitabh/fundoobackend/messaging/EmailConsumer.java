package org.amitabh.fundoobackend.messaging;

import org.amitabh.fundoobackend.dto.EmailMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class EmailConsumer {

    private final JavaMailSender mailSender;
    private final ObjectMapper objectMapper;

    public EmailConsumer(JavaMailSender mailSender, ObjectMapper objectMapper) {
        this.mailSender = mailSender;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "otp.email.queue")
    public void consume(String json) {
        try {
            EmailMessage message = objectMapper.readValue(json, EmailMessage.class);
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(message.getTo());
            mail.setSubject(message.getSubject());
            mail.setText(message.getBody());
            mailSender.send(mail);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process email message", e);
        }
    }
}
