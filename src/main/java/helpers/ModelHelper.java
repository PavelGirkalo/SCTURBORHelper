package helpers;

import javafx.scene.control.TableView;
import model.PlayerModel;

import java.util.ArrayList;

public class ModelHelper {
    public static ArrayList<PlayerModel> fillModel(ArrayList<PlayerModel> old_players, String[] names) {
        ArrayList<PlayerModel> players = old_players;

        //заполнение имен игроков из распознанной картинки
        for (int i = 0; i < names.length; i++) {
            PlayerModel player = new PlayerModel(names[i]);
            players.add(player);
        }

        //заполнение порядкового номера игрока в таблице
        for(int i = 1; i<= players.size();i++)
            players.get(i-1).setId(i);

        return players;
    }

    public static ArrayList<PlayerModel> updateModel(ArrayList<PlayerModel> old_players, TableView finalTable){
        ArrayList<PlayerModel> players = old_players;
        return players;
    }


}
