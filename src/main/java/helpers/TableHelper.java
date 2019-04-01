package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.PlayerModel;

import java.util.ArrayList;

public class TableHelper {


    public static ObservableList<PlayerModel> fillTable(ObservableList<PlayerModel> list, ArrayList<PlayerModel> players) {
        list = FXCollections.observableArrayList();
        for (PlayerModel player : players)
            list.add(player);
        return list;
    }
}
