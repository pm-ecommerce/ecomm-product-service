package com.pm.ecommerce.product_service.notifications.config;

import com.pm.ecommerce.entities.Notification;
import com.pm.ecommerce.product_service.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class QueueNotification {
    @Autowired
    KafkaTemplate<String, Long> template;

    @Value("${email.sender:info@pmecommerce.com}")
    String sender;

    @Value("${email.topic:NotificationTopic}")
    String topic;

    @Autowired
    NotificationRepository repository;

    public void queue(Notification notification) {
        notification.setSender(sender);
        notification.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        repository.save(notification);

        template.send(topic, notification.getId());
    }
}
