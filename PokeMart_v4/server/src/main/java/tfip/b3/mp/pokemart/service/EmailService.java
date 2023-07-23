package tfip.b3.mp.pokemart.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import tfip.b3.mp.pokemart.model.OrderDAO;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    String defaultSender;

    public void sendSimpleMail(String recipient, String subject, String text){
        SimpleMailMessage email = new SimpleMailMessage(); 
        email.setFrom(defaultSender);
        email.setTo(recipient); 
        email.setSubject(subject); 
        email.setText(text);
        mailSender.send(email);
    }

    public void sendAttachedMail(String recipient, String subject, String text, String filename,File file ) throws MessagingException{
        MimeMessage mimeMail= mailSender.createMimeMessage();
        MimeMessageHelper email = new MimeMessageHelper(mimeMail, true);
        email.setFrom(defaultSender);
        email.setTo(recipient);
        email.setText(text);
        email.setSubject(subject);
        email.addAttachment(filename,file);
        mailSender.send(mimeMail);
    }

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendOrderEmail(String recipient, String subject, OrderDAO processedOrder) throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("customerName", processedOrder.getCustomerName());
        thymeleafContext.setVariable("orderID", processedOrder.getOrderID());
        thymeleafContext.setVariable("orderDate", processedOrder.getOrderDate());
        thymeleafContext.setVariable("customerPhone", processedOrder.getCustomerPhone());
        thymeleafContext.setVariable("customerEmail", processedOrder.getCustomerEmail());
        thymeleafContext.setVariable("shippingAddress", processedOrder.getShippingAddress());
        thymeleafContext.setVariable("shippingType", processedOrder.getShippingType());
        thymeleafContext.setVariable("shippingCost", processedOrder.getShippingCost());
        thymeleafContext.setVariable("subtotal", processedOrder.getSubtotal());
        thymeleafContext.setVariable("total", processedOrder.getTotal());
        thymeleafContext.setVariable("paymentID", processedOrder.getPaymentID());
        thymeleafContext.setVariable("items", processedOrder.getItems());
        
        MimeMessage mimeMail= mailSender.createMimeMessage();
        MimeMessageHelper email;
        email = new MimeMessageHelper(mimeMail, true);
        email.setFrom(defaultSender);
        email.setTo(recipient);
        email.setSubject(subject);
        final String htmlContent = this.templateEngine.process("ordermail.html", thymeleafContext);
        System.out.println(htmlContent);
        email.setText(htmlContent, true);
        mailSender.send(mimeMail);

    }   

    public void sendOTPEmail(String recipient, String OTP){
        String text = "You have requested to register your Telegram Account. The OTP to verify your account is \n\n";
        text += OTP + "\n";
        text += """
                \n This OTP will expire in 5 minutes.
                If you are not the intended recipient or receive this email in error, please ignore it and take no further actions. Thank you.
                """;
        sendSimpleMail(recipient, "Verify Your Telegram Signup with Pokemart",text);
    }

}


    // public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
    //     MimeMessage message = mailSender.createMimeMessage();
    //     MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    //     helper.setTo(to);
    //     helper.setSubject(subject);
    //     helper.setText(htmlBody, true);
    //     mailSender.send(message);
    // }   
