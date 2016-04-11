package de.knuff0r.bsb.app;

/**
 * Created by sebastian on 11.12.15.
 */


import de.knuff0r.bsb.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Component
public class SmtpMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine engine;

    @Value("${app.url}")
    private String url;

    public void send(User user, String subject, String template, HttpServletRequest request,
                     HttpServletResponse response, ServletContext context, Locale locale) throws MessagingException {

        final WebContext ctx = new WebContext(request,response,context,locale);
        ctx.setVariable("key", user.getActivateKey());
        ctx.setVariable("url", url);


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;



        helper = new MimeMessageHelper(message, true, "UTF-8"); // true indicates
        // multipart message
        helper.setSubject(subject);
        helper.setFrom("no-reply@knuff0r.xyz");
        helper.setTo(user.getEmail());
        // continue using helper object for more functionalities like adding attachments, etc.

        final String htmlContent = this.engine.process(template, ctx);
        helper.setText(htmlContent, true);

        javaMailSender.send(message);


    }

}

