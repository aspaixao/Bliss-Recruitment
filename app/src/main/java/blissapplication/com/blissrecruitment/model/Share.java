package blissapplication.com.blissrecruitment.model;

// Created by Alexandre Paixao on 3/17/2018.

public class Share {

    private String destination_email;
    private String content_url;

    public Share() {}

    public String getDestination_email() {
        return destination_email;
    }

    public void setDestination_email(String destination_email) {
        this.destination_email = destination_email;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }
}
