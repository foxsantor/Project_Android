package com.example.projeecto.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Comment {


    private int idComment;
    private int idDeal;
    private int vote;
    private String text;
    private String username;
    private Date created;
    private Votes checker;
    public ArrayList<Votes> votes;
    private String userEmail;
    private int state;
    public Votes getChecker() {
        return checker;
    }

    public void setChecker(Votes checker) {
        this.checker = checker;
    }

    public Comment(int idComment, int vote) {
        this.idComment = idComment;
        this.vote = vote;
    }

    public Comment() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getState() {
        return state;
    }

    public Comment(int idComment, int idDeal, int vote, String text, String username, Date created, ArrayList<Votes> votes, String userEmail, int state) {
        this.idComment= idComment;
        this.idDeal = idDeal;
        this.vote = vote;
        this.text = text;
        this.username = username;
        this.created = created;
        this.votes =votes;
        this.userEmail = userEmail;
        this.state = state;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public int getIdDeal() {
        return idDeal;
    }

    public void setIdDeal(int idDeal) {
        this.idDeal = idDeal;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
