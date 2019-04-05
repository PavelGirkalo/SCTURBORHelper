package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Player;
import model.PlayerList;

public class TableHelper {


    public static ObservableList<Player> fillTable(PlayerList players) {
        ObservableList<Player> list = FXCollections.observableArrayList();
        for (Player player : players.getPlayerList())
            list.add(player);
        return list;
    }

}
