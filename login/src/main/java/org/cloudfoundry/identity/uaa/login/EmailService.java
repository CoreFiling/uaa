package org.cloudfoundry.identity.uaa.login;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

public class EmailService implements MessageService {
    private final Log logger = LogFactory.getLog(getClass());

    private final JavaMailSender mailSender;
    private final String loginUrl;
    private final Brand brand;
    private final String senderAddressIfSpecified;

    public EmailService(JavaMailSender mailSender, String loginUrl, String senderAddressIfSpecified, Brand brand) {
        this.mailSender = mailSender;
        this.loginUrl = loginUrl;
        this.senderAddressIfSpecified = senderAddressIfSpecified;
        this.brand = brand;
    }

    private Address[] getSenderAddresses() throws AddressException, UnsupportedEncodingException {
        String name = brand.getServiceName();
        String senderAddress = senderAddressIfSpecified;
        if (senderAddress == null) {
          String host = UriComponentsBuilder.fromHttpUrl(loginUrl).build().getHost();
          senderAddress = "admin@" + host;
        }
        return new Address[]{new InternetAddress(senderAddress, name)};
    }

    @Override
    public void sendMessage(String userId, String email, MessageType messageType, String subject, String htmlContent) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.addFrom(getSenderAddresses());
            message.addRecipients(Message.RecipientType.TO, email);
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html");
        } catch (MessagingException e) {
            logger.error("Exception raised while sending message to " + email, e);
        } catch (UnsupportedEncodingException e) {
            logger.error("Exception raised while sending message to " + email, e);
        }

        mailSender.send(message);
    }
}
