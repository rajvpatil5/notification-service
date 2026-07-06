package com.notification.processor.sender;

import com.notification.common.event.NotificationCreatedEvent;
import com.notification.processor.models.entity.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationSender implements NotificationSender {
    @Override
    public void send(NotificationCreatedEvent event, Template template, String renderedContent) {
        log.info("Email Sender");
        log.info("To      : {}", event.getRecipient().getEmail());
        log.info("Subject : {}", template.getSubject());
        log.info("Body    : {}", renderedContent);
    }

//    private final JavaMailSender mailSender;
//
//    @Override
//    public void send(NotificationCreatedEvent event,
//                     Template template,
//                     String renderedContent) {
//
//        try {
//
//            MimeMessage message = mailSender.createMimeMessage();
//
//            MimeMessageHelper helper =
//                    new MimeMessageHelper(message, true, "UTF-8");
//
//            helper.setTo(event.getRecipient().getEmail());
//
//            helper.setSubject(template.getSubject());
//
//            helper.setText(renderedContent, true);
//
//            mailSender.send(message);
//
//            log.info(
//                    "Email sent successfully. NotificationId={}, Recipient={}",
//                    event.getNotificationId(),
//                    event.getRecipient().getEmail());
//
//        } catch (MessagingException ex) {
//
//            log.error(
//                    "Unable to prepare email. NotificationId={}",
//                    event.getNotificationId(),
//                    ex);
//
//            throw new RuntimeException(ex);
//
//        } catch (Exception ex) {
//
//            log.error(
//                    "Unable to send email. NotificationId={}",
//                    event.getNotificationId(),
//                    ex);
//
//            throw ex;
//        }
//    }
}