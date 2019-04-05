package model;

import java.util.ArrayList;

public class PlayerList {
    private ArrayList<Player> playerList;

    public PlayerList(ArrayList<Player> playersList) {
        this.playerList = playersList;
    }

    public void add(Player player) {
        playerList.add(player);
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public Player findPlayer(String name) {
        for (Player player : playerList)
            if (player.getUserName().toLowerCase().equals(name.toLowerCase()))
                return player;
            return null;
    }


    @Override
    public String toString() {
        return "PlayerList{" +
                "playerList=" + playerList +
                '}';
    }


}
