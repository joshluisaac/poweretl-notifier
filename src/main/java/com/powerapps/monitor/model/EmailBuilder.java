package com.powerapps.monitor.model;

import com.kollect.etl.notification.entity.Email;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class EmailBuilder {

    private Email email;
    private String from;

    private String title;
    private String to;
    private String body;
    private MultipartFile multipartAtt;
    private File fileAtt;


    public EmailBuilder title(String title) {
        this.title = title;
        return this;
    }


    public EmailBuilder from(String from) {
        this.from = from;
        return this;
    }


    public EmailBuilder to(String to) {
        this.to = to;
        return this;
    }


    public EmailBuilder body(String body) {
        this.body = body;
        return this;
    }

    public EmailBuilder includeAtt(MultipartFile multipartAtt) {
        this.multipartAtt = multipartAtt;
        return this;
    }

    public EmailBuilder includeFileAtt(File fileAtt) {
        this.fileAtt = fileAtt;
        return this;
    }


    public Email create() {
        email = new Email(from, to, title, body, multipartAtt, fileAtt);
        return email;
    }

}
