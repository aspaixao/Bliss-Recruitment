package blissapplication.com.blissrecruitment.model;

// Created by Alexandre Paixao on 3/17/2018.

public class Choice {

    private String choice;
    private int votes;

    public String getChoice() {return choice;}
    public void setChoice(String choice) { this.choice = choice;}

    public int getVotes() {return votes;}
    public void setVotes(int votes) {this.votes = votes;}

    public void Choice() {}
    public void Choice(String choice, int votes) {
        this.choice = choice;
        this.votes = votes;
    }

    public int addVote() { return votes++;}



}
