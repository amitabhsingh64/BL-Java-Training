package org.amitabh.fundoobackend.messaging;

import org.amitabh.fundoobackend.config.RabbitMQConfig;
import org.amitabh.fundoobackend.dto.EmailMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public EmailProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            String json = objectMapper.writeValueAsString(new EmailMessage(to, subject, body));
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, json);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish email message", e);
        }
    }
}
