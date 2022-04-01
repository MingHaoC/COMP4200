package com.example.comp4200.model;

public class Tweet {

    String content;
    String display_name;
    String date;
    int userID;

    public Tweet(int userID, String content){

        this.userID = userID;
        this.content = content;

    }

    public String getContent(){
        return this.content;
    }

    public int getUserId(){
        return this.userID;
    }

    public String getDisplay_name(){
        return this.display_name;
    }

    public String getDate(){
        return date;
    }


}
