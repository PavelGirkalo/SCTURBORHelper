package helpers;

import javafx.scene.image.Image;
import model.Org;
import model.OrgList;
import model.Player;
import model.PlayerList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ParserHelper {



    public static void getInfo(PlayerList players) {

        //запрос на сервер
        String prefix = "https://robertsspaceindustries.com/citizens/";
        String full_name;
        for (Player player : players.getPlayerList()) {
            full_name = prefix + player.getUserName() + "/organizations";
            Document doc;
            try {
                //запрос на сервер только для контактов без информации о корпах или для контактов с некорректным именем (у таких в поле корпы пометка +++)
                if (player.getOrgs().size() == 0 || player.getOrgs().size() !=0 && player.getOrgs().get(0).getName().equals("+++++ПРОВЕРЬТЕ ИМЯ ИГРОКА+++++")) {
                    doc = Jsoup.connect(full_name).get();
                    Elements orgs = doc.select("a[href][class*='value']");
                    ArrayList<Org> attr = new ArrayList<>();
                    for (Element org : orgs) {
                        if (!org.attributes().get("href").equals("#")) {
                            if (!org.attributes().get("href").equals(""))
                                attr.add(new Org(org.attributes().get("href").substring(6)));
                            else
                                attr.add(new Org("-"));
                        }
                        else
                            attr.add(new Org("!!!СКРЫТА!!!"));
                    }
                    if (attr.size()== 0)
                        attr.add(new Org("нет"));
                    player.setOrgs(attr);

                }
            } catch (IOException e) {
                ArrayList<Org> attr = new ArrayList<>();
                attr.add(new Org("+++++ПРОВЕРЬТЕ ИМЯ ИГРОКА+++++"));
                player.setOrgs(attr);
            }
        }
    }

    public static ArrayList<Org> takeOrgInfo(Document doc){
        String base = "https://robertsspaceindustries.com";
        ArrayList<Org> new_orgs = new ArrayList<>();
        Elements orgs = doc.select("div[class*='box-content org']");
        for(Element org : orgs) {
            if (org.select("div[class='thumb']").get(0).selectFirst("a")!=null) {

                    Element main_info = org.selectFirst("div[class='thumb']").selectFirst("a[href*='/orgs/']");
                    //имя корпы
                    String main_orgname = main_info.attributes().get("href").substring(6);
                    //ссылка на лого корпы
                    String main_img = base + main_info.selectFirst("img").attr("src");
                    //Image image = new Image(main_img);
                    new_orgs.add(new Org(main_orgname, main_img,1));

            }
        }
        return new_orgs;
    }



}
