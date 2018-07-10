package com.powerapps.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildEmail(String intro, String message) {
        Context context = new Context();
        context.setVariable("title", "Hi there,");
        context.setVariable("intro", intro);
        context.setVariable("message", message);
        context.setVariable("salutation", "Regards,");
        context.setVariable("signOff", "PowerApps Auto Update");
        context.setVariable("footer", null);
        return templateEngine.process("fragments/template_email", context);
    }
}
