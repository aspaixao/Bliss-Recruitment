package blissapplication.com.blissrecruitment.model;

// Created by Alexandre Paixao on 3/17/2018.

import java.util.List;

public class Question {

    private int id;
    private String question;
    private String image_url;
    private String thumb_url;
    private String published_at;
    private List<Choice> choices;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getQuestion() {return question;}
    public void setQuestion(String question) {this.question = question;}

    public String getImage_url() {return image_url;}
    public void setImage_url(String image_url) {this.image_url = image_url;}

    public String getThumb_url() {return thumb_url;}
    public void setThumb_url(String thumb_url) {this.thumb_url = thumb_url;}

    public String getPublished_at() {return published_at;}
    public void setPublished_at(String published_at) {this.published_at = published_at;}

    public List<Choice> getChoices() {return choices;}
    public void setChoices(List<Choice> choices) {this.choices = choices;}

    public Question() {}
    public Question(int id, String question, String image_url, String thumb_url, String published_at, List<Choice> choices) {
        this.id = id;
        this.question = question;
        this.image_url = image_url;
        this.thumb_url = thumb_url;
        this.published_at = published_at;
        this.choices = choices;
    }

}
