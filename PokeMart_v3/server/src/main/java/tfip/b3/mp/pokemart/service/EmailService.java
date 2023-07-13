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

    public void sendThymeLeafEmail(String recipient, String subject, String text) throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("text", text);

        MimeMessage mimeMail= mailSender.createMimeMessage();
        MimeMessageHelper email;
        email = new MimeMessageHelper(mimeMail, true);
        email.setFrom(defaultSender);
        email.setTo(recipient);
        email.setSubject(subject);
        final String htmlContent = this.templateEngine.process("testplate.html", thymeleafContext);
        System.out.println(htmlContent);
        email.setText(htmlContent, true);
        mailSender.send(mimeMail);

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
