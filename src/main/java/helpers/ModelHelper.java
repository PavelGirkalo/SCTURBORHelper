package helpers;

import javafx.scene.control.TableView;
import model.Flag;
import model.PlayerModel;
import model.PlayersList;

import java.util.ArrayList;

public class ModelHelper {
    public static PlayersList fillModel(PlayersList old_players, String list) {
        //считывание списка игроков из текстовой области
        String[] new_names = list.split("\\n");
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < new_names.length; i++) {
            String name = new_names[i];
            names.add(name);
        }

        //Заполнение модели со списка игроков
        PlayersList players = new PlayersList(new ArrayList<>());
        for (String name : names) {
            players.add(new PlayerModel(name));
            //проверка наличия ранее добавленных игроков в список игроков
            if(old_players.getPlayersList() != null && old_players.findPlayer(name) != null)
                if(old_players.findPlayer(name).getUserName().equals(name)){
                    players.findPlayer(name).setOrgs(old_players.findPlayer(name).getOrgs());
                    players.findPlayer(name).setFlag(old_players.findPlayer(name).getFlag());
                }
        }
        //заполнение имен игроков из распознанной картинки
        for (int i = 0; i < players.getPlayersList().size(); i++) {
            players.getPlayersList().get(i).setId(i+1);
        }

        return players;
    }

    public static ArrayList<PlayerModel> updateModel(ArrayList<PlayerModel> old_players, TableView finalTable){
        ArrayList<PlayerModel> players = old_players;
        return players;
    }

    public static PlayersList checkPlayersFlag(PlayersList players) {
        PlayersList blackList = FileHelper.readPlayers("./src/main/resources/Enemies.csv");
        PlayersList whiteList = FileHelper.readPlayers("./src/main/resources/Friends.csv");
        for(PlayerModel player : players.getPlayersList()) {
            if (blackList.findPlayer(player.getUserName()) != null)
                player.setFlag(Flag.ENEMY);
            if (whiteList.findPlayer(player.getUserName()) != null)
                player.setFlag(Flag.FRIEND);
        }
        return players;
    }


}
