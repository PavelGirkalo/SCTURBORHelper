package model;

import java.util.ArrayList;

public class PlayersList{
    private ArrayList<PlayerModel> playersList;

    public PlayersList(ArrayList<PlayerModel> playersList) {
        this.playersList = playersList;
    }

    public void add(PlayerModel player) {
        playersList.add(player);
    }

    public ArrayList<PlayerModel> getPlayersList() {
        return playersList;
    }

    public PlayerModel findPlayer(String name) {
        for (PlayerModel player : playersList)
            if (player.getUserName().equals(name))
                return player;
            return null;
    }


    @Override
    public String toString() {
        return "PlayersList{" +
                "playersList=" + playersList +
                '}';
    }

    public void modifyWrongPlayers(String[] names) {
        for (int i = 0; i < playersList.size(); i++) {
            if (!playersList.get(i).getUserName().equals(names[i])){
                playersList.get(i).setUserName(names[i]);
                ArrayList<String> orgs = new ArrayList<>();
                orgs.add("+++++++");
                playersList.get(i).setOrgs(orgs.toString());
            }
        }

    }
}
