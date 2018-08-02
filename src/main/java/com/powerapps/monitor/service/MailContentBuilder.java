package com.powerapps.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * This class is used to prepare the email template using thymeleaf.
 *
 * @author hashim
 */
@Service
public class MailContentBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildEmail(String body, String templateName) {
        Context context = new Context();
        context.setVariable("body", body);
        return templateEngine.process(
                templateName, context);
    }
}
