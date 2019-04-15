package helpers;

import model.*;

import java.util.ArrayList;

public class ModelHelper {
    public static PlayerList fillModel(PlayerList old_players, String list) {

        //считывание списка игроков из текстовой области
        String[] new_names = list.split("\\n");
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < new_names.length; i++) {
            String name = new_names[i];
            names.add(name);
        }

        //Заполнение модели со списком игроков
        PlayerList players = new PlayerList(new ArrayList<>());
        for (String name : names) {
            if(players.findPlayer(name) == null) {
                players.add(new Player(name));
                //проверка наличия ранее добавленных игроков в список игроков
                if (old_players.getPlayerList() != null && old_players.findPlayer(name) != null)
                    if (old_players.findPlayer(name).getUserName().equals(name)) {
                        players.findPlayer(name).setOrgs(old_players.findPlayer(name).getOrgs());
                        players.findPlayer(name).setFlag(old_players.findPlayer(name).getFlag());
                    }
            }
        }
        //нумерация игроков в модели
        for (int i = 0; i < players.getPlayerList().size(); i++)
            players.getPlayerList().get(i).setId(i+1);
        return players;
    }

    public static PlayerList checkPlayersFlag(PlayerList players) {
        PlayerList blackList = FileHelper.readPlayers("./resources/Enemies.csv");
        PlayerList whiteList = FileHelper.readPlayers("./resources/Friends.csv");
        for(Player player : players.getPlayerList()) {
            if (blackList.findPlayer(player.getUserName()) != null)
                player.setFlag('✘');
            if (whiteList.findPlayer(player.getUserName()) != null)
                player.setFlag('❤');
        }
        return players;
    }

    public static OrgList fillOrgModel(PlayerList players) {
        OrgList orgs = new OrgList(new ArrayList<>());
        for (Player player: players.getPlayerList()){
            for (Org org : player.getOrgs()){
                if(orgs.findOrg(org.getName())== null)
                    orgs.add(new Org(org.getName(),1));
                else
                    orgs.findOrg(org.getName()).setQuantity(orgs.findOrg(org.getName()).getQuantity()+1);
            }
        }
        return orgs;
    }
}
