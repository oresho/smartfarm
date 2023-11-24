package com.klusterthon.Smartfarm.service.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
@Configuration
public class JavaMailConfig {
    @Value("${server.mail.host}")
    private String mailHost;

    @Value("${server.mail.port}")
    private int mailPort;

    @Value("${server.mail.username}")
    private String mailUsername;

    @Value("${server.mail.password}")
    private String mailPassword;

    @Bean
    public JavaMailSender customJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Set your SMTP server properties
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        // Configure additional properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Configure retries (optional)
        properties.put("mail.smtp.connectiontimeout", "10000"); // Timeout in milliseconds
        properties.put("mail.smtp.timeout", "10000"); // Timeout in milliseconds
        properties.put("mail.smtp.writetimeout", "5000"); // Timeout in milliseconds

        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }
}
