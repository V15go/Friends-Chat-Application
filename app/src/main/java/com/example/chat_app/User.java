package com.example.chat_app;

public class User {


    String profile_pic, username, about_one_line, email, password, lastMessage, userid, status, ImageUrl;

    public User(){

    }

    public User(String profile_pic, String username, String  about_one_line,  String email, String password, String lastMessage, String userid, String status) {
        this.profile_pic = profile_pic;
        this.username = username;
        this.about_one_line = about_one_line;

        this.email = email;
        this.password = password;
        this.lastMessage = lastMessage;
        this.userid = userid;
        this.status = status;
    }

    public User(String username, String about_one_line, String ImageUrl, String   email, String password ,String userid) {
        this.username = username;
        this.about_one_line = about_one_line;
        this.ImageUrl = ImageUrl;
        this.email = email;

        this.password = password;
        this.userid = userid;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getAbout_one_line() {
        return about_one_line;
    }

    public void setAbout_one_line(String about_one_line) {
        this.about_one_line = about_one_line;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
