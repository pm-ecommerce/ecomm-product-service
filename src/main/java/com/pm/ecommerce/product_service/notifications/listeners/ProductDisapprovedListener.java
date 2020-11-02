package com.pm.ecommerce.product_service.notifications.listeners;

import com.pm.ecommerce.entities.Notification;
import com.pm.ecommerce.product_service.notifications.config.QueueNotification;
import com.pm.ecommerce.product_service.notifications.config.TemplateParser;
import com.pm.ecommerce.product_service.notifications.event_data.EmailProduct;
import com.pm.ecommerce.product_service.notifications.events.ProductDisapprovedEvent;
import com.pm.ecommerce.product_service.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProductDisapprovedListener implements ApplicationListener<ProductDisapprovedEvent> {
    @Autowired
    QueueNotification queueNotification;

    @Autowired
    NotificationRepository repository;

    @Autowired
    TemplateParser parser;

    @Override
    public void onApplicationEvent(ProductDisapprovedEvent event) {
        Notification notification = new Notification();
        notification.setReceiver(event.getProduct().getVendor().getEmail());
        notification.setSubject("You product has been disapproved.");
        String message = parser.parseTemplate("templates/product-disapproved", new EmailProduct(event.getProduct()));
        notification.setMessage(message);
        queueNotification.queue(notification);
    }
}

