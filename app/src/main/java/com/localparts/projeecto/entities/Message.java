package com.localparts.projeecto.entities;

import android.graphics.Bitmap;

public class Message {

    public  static final  int TYPE_MESSAGE =0;
    public static final int TYPE_LOG =1;
    public static final int TYPE_ACTION =2;

    private int mType;
    private String mMessage;
    private Bitmap mImage;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Message() {
    }


    public int getType() {
        return mType;
    }


    public String getMessage() {
        return mMessage;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public static class Builder{

        private final int mType;
        private String mMessage;
        private Bitmap mImage;
        private String userm;


        public Builder(int Type) {
            this.mType = Type;
        }

        public Builder message(String message,String username) {
            this.mMessage = message;
            this.userm = username;
            return this;
        }

        public Builder image(Bitmap image) {
            this.mImage = image;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.mType = mType;
            message.mImage = mImage;
            message.mMessage = mMessage;
            message.user = userm;
            return message;

        }
    }




}