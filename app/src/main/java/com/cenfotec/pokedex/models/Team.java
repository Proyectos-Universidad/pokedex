package com.cenfotec.pokedex.models;

import java.util.ArrayList;

public class Team {

    private String name;
    private ArrayList<User> members;

    public Team(String name, ArrayList<User> members) {
        this.name = name;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }
}
