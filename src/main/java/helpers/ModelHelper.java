package helpers;

import model.Flag;
import model.Player;
import model.PlayerList;

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

        //Заполнение модели со списка игроков
        PlayerList players = new PlayerList(new ArrayList<>());
        for (String name : names) {
            players.add(new Player(name));
            //проверка наличия ранее добавленных игроков в список игроков
            if(old_players.getPlayerList() != null && old_players.findPlayer(name) != null)
                if(old_players.findPlayer(name).getUserName().equals(name)){
                    players.findPlayer(name).setOrgs(old_players.findPlayer(name).getOrgs());
                    players.findPlayer(name).setFlag(old_players.findPlayer(name).getFlag());
                }
        }
        //нумерация игроков в модели
        for (int i = 0; i < players.getPlayerList().size(); i++)
            players.getPlayerList().get(i).setId(i+1);
        return players;
    }

    public static PlayerList checkPlayersFlag(PlayerList players) {
        PlayerList blackList = FileHelper.readPlayers("./src/main/resources/Enemies.csv");
        PlayerList whiteList = FileHelper.readPlayers("./src/main/resources/Friends.csv");
        for(Player player : players.getPlayerList()) {
            if (blackList.findPlayer(player.getUserName()) != null)
                player.setFlag(Flag.ENEMY);
            if (whiteList.findPlayer(player.getUserName()) != null)
                player.setFlag(Flag.FRIEND);
        }
        return players;
    }

}
