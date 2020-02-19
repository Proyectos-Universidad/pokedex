package com.cenfotec.pokedex.models;

public class Team {

    private int number;
    private String name;
    private String userEmail;
    private String userName;

    public Team(int number, String name, String userEmail, String userName) {
        this.number = number;
        this.name = name;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
