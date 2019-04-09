package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Org;
import model.OrgList;
import model.Player;
import model.PlayerList;

public class TableHelper {


    public static ObservableList<Player> fillTable(PlayerList players) {
        ObservableList<Player> list = FXCollections.observableArrayList();
        for (Player player : players.getPlayerList())
            list.add(player);
        return list;
    }

    public static ObservableList<Org> fillOrgTable(OrgList orgs) {
        ObservableList<Org> orgList = FXCollections.observableArrayList();
        for(Org org : orgs.getOrgList())
                orgList.add(org);
        return orgList;
    }

}
