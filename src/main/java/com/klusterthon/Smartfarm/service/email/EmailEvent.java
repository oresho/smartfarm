package com.klusterthon.Smartfarm.service.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class EmailEvent extends ApplicationEvent {
    private String email;
    private String subject;
    private String from;
    private String htmlContent;
    public EmailEvent(String email, String subject, String from, String htmlContent) {
        super(email);
        this.email = email;
        this.subject = subject;
        this.from = from;
        this.htmlContent = htmlContent;
    }
}
