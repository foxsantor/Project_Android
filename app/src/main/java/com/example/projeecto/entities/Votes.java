package com.example.projeecto.entities;

public class Votes {

    private int idVotes;
    private String username;
    private String ref;
    private int commentid;

    public Votes() {
    }

    public Votes(int idVotes, String username, String ref, int commentid) {
        this.idVotes = idVotes;
        this.username = username;
        this.ref = ref;
        this.commentid = commentid;
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
