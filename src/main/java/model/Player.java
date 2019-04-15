package model;

import java.util.ArrayList;

public class Player {
    private int id;
    private String userName;
    private ArrayList<Org> orgs;
    private char flag;

    public Player(int id, String userName, ArrayList<Org> orgs, char flag) {
        this.id = id;
        this.userName = userName;
        this.orgs = orgs;
        this.flag = flag;
    }

    public Player(int id, String userName, ArrayList<Org> orgs) {
        this.id = id;
        this.userName = userName;
        this.orgs = orgs;
    }

    public Player(String userName, ArrayList<Org> orgs) {
        this.userName = userName;
        this.orgs = orgs;
    }

    public Player(String userName) {
        this.userName = userName;
        this.orgs = new ArrayList<>();
        this.flag = '➖';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Org> getOrgs() {
        return orgs;
    }

    public void setOrgs(ArrayList<Org> orgs) {
        this.orgs = orgs;
    }

    public char getFlag() {
        return flag;
    }

    public void setFlag(char flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Player{" +
                "userName='" + userName + '\'' +
                ", orgs=" + orgs +
                ", flag=" + flag +
                '}';
    }
}

