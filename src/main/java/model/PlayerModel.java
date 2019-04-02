package model;

public class PlayerModel {
    private int id;
    private String userName;
    private String orgs;
    private Flag flag;

    public PlayerModel(int id, String userName, String orgs, Flag flag) {
        this.id = id;
        this.userName = userName;
        this.orgs = orgs;
        this.flag = flag;
    }

    public PlayerModel(int id, String userName, String orgs) {
        this.id = id;
        this.userName = userName;
        this.orgs = orgs;
    }

    public PlayerModel(String userName, String orgs) {
        this.userName = userName;
        this.orgs = orgs;
    }

    public PlayerModel(String userName) {
        this.userName = userName;
        this.orgs = "";
        this.flag = Flag.NEUTRAL;
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

    public String getOrgs() {
        return orgs;
    }

    public void setOrgs(String orgs) {
        this.orgs = orgs;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "PlayerModel{" +
                "userName='" + userName + '\'' +
                ", orgs='" + orgs + '\'' +
                ", flag=" + flag +
                '}';
    }
}

