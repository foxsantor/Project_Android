package com.localparts.projeecto.entities;

public class Votes {

    private int idVotes;
    private String username;
    private String ref;
    private int commentid;
    private String state;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Votes() {
        this.state = "no";
    }

    public Votes(int idVotes, String username, String ref, int commentid,String state) {
        this.idVotes = idVotes;
        this.username = username;
        this.ref = ref;
        this.commentid = commentid;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Votes{" +
                "idVotes=" + idVotes +
                ", username='" + username + '\'' +
                ", ref='" + ref + '\'' +
                ", commentid=" + commentid +
                '}';
    }

    public int getIdVotes() {
        return idVotes;
    }

    public String getUsername() {
        return username;
    }

    public String getRef() {
        return ref;
    }

    public int getCommentid() {
        return commentid;
    }
}
