package com.example.whatsapp.Models;

public class Users {

    String profilePic, firstName, lastName, email, passWord, userId, lastMessage;

    public Users() {}

    public Users(String profilePic, String firstName, String lastName, String email, String passWord, String userId, String lastMessage) {
        this.profilePic = profilePic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passWord = passWord;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}
