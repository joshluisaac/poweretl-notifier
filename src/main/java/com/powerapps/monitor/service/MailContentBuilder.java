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

    public String buildAdhocEmail(String title, String body) {
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("body", body);
        return templateEngine.process("fragments/template_adhoc_mail_template", context);
    }
}
