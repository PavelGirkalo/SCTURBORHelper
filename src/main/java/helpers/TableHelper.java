package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.PlayerModel;
import model.PlayersList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class TableHelper {


    public static ObservableList<PlayerModel> fillTable(ObservableList<PlayerModel> list, PlayersList players) {
        list = FXCollections.observableArrayList();
        for (PlayerModel player : players.getPlayersList())
            list.add(player);
        return list;
    }

    public static void getInfo(PlayersList players) {
        //заполнение списка имен
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < players.getPlayersList().size(); i++) {
            String name = players.getPlayersList().get(i).getUserName();
            names.add(name);
        }

        //запрос на сервер
        String prefix = "https://robertsspaceindustries.com/citizens/";
        String full_name;
        for (PlayerModel player : players.getPlayersList()) {
            full_name = prefix + player.getUserName() + "/organizations";
            Document doc;
            try {
                //запрос на сервер только для контактов без информации о корпах или для контактов с некорректным именем (у таких в поле корпы пометка +++)
                if (player.getOrgs().equals("") || player.getOrgs().equals("+++++++")) {
                    doc = Jsoup.connect(full_name).get();
                    Elements orgs = doc.select("a[href][class*='value']");
                    String attr = "";
                    for (Element org : orgs) {
                        if (!org.attributes().get("href").equals("#")) {
                            if (!org.attributes().get("href").equals(""))
                                attr = attr + org.attributes().get("href").substring(6) + "  ";
                        }
                        else
                            attr = "!!!REDACTED!!!";
                    }
                    player.setOrgs(attr);
                }
            } catch (IOException e) {
                String attr = "+++++++";
                player.setOrgs(attr);
            }
        }
    }
}
