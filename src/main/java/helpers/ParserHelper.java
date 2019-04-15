package helpers;

import javafx.scene.control.Tooltip;
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
                if (player.getOrgs().size() == 0 || player.getOrgs().size() !=0 && player.getOrgs().get(0).getName().equals("!!!ПРОВЕРЬТЕ ИМЯ!!!")) {
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
                attr.add(new Org("!!!ПРОВЕРЬТЕ ИМЯ!!!"));
                player.setOrgs(attr);
            }
        }
    }

    public static ArrayList<String> getOrgInfo(Org org) {
        String base = "https://robertsspaceindustries.com";
        Document doc;
        ArrayList<String> outArray = new ArrayList<>();
        String out;
        try {
            doc = Jsoup.connect(base + "/orgs/" + org.getName()).get();
            //получение инфы об организации
            Element el = doc.selectFirst("div[class=inner]");
            //добавление в результат ссылки на логотип корпы
            outArray.add(base + el.select("img").attr("src"));

            //добавление в результат поля c информацией о корпе
            String quantity = el.select("span[class=count]").text();
            String full_name = el.select("h1").text();
            String model = el.select("li[class=model]").text();
            String commitment = el.select("li[class=commitment]").text();
            out = "Имя: " + full_name + "\n" + "Количество: " + quantity + '\n' + "Тип корпорации: " + model + '\n' + "Подход: " + commitment;
            outArray.add(out);

        } catch (IOException e) {
            outArray = new ArrayList<>();
            outArray.add("/app/tur-logo.jpg");
            outArray.add("Нет информации об организации");
        }
        return outArray;
    }

    public static ArrayList<String> getPlayerInfo(Player player){
        String base = "https://robertsspaceindustries.com";
        Document doc;
        ArrayList<String> outArray = new ArrayList<>();
        String out;
        try {
            doc = Jsoup.connect(base + "/citizens/" + player.getUserName()).get();
            //сбор инфы об игроке
            Element el = doc.selectFirst("div[class=profile left-col]");
            //элемент с информацией об игроке
            el = el.selectFirst("div[class=info]");

            //добавление в результат ссылки на логотип игрока
            Element img_el = doc.selectFirst("div[class=thumb]");
            outArray.add(base + img_el.select("img").attr("src"));

            //добавление в результат поля c информацией об игроке
            String comm = el.select("strong[class=value]").get(0).text();
            String handle = el.select("strong[class=value]").get(1).text();
            String title = el.select("span[class=value]").text();
            String enlisted = "";
            String location = "";
            String fluency = "";
            el = doc.selectFirst("div[class=inner]");
            Elements entries = el.select("p[class=entry]");
            for(Element element : entries){
                if(element.select("span[class=label]").get(0).text().equals("Enlisted"))
                    enlisted = element.select("strong[class=value]").get(0).text();
                if(element.select("span[class=label]").get(0).text().equals("Location"))
                    location = element.select("strong[class=value]").get(0).text();
                if(element.select("span[class=label]").get(0).text().equals("Fluency"))
                    fluency = element.select("strong[class=value]").get(0).text();
            }
            out = "Community Moniker: " + comm + '\n' +
                    "Handle: " + handle + '\n' +
                    "Титул/ачивка: " + title + '\n'  +
                    "Зарегистрирован: " + enlisted + '\n' +
                    "Локация: " + location + '\n' +
                    "Язык: " + fluency + '\n';
            outArray.add(out);


            //сбор инфы об основной корпе игрока
            el = doc.selectFirst("div[class*=main-org]");
            //добавление в результат ссылки на логотип корпы игрока
            img_el = el.selectFirst("div[class=thumb]");
            if (img_el != null)
                outArray.add(base + img_el.select("img").attr("src"));
            else
                outArray.add("/app/tur-logo.jpg");

            //добавление в результат поля c информацией о корпе игрока
            el = el.selectFirst("div[class*=inner]");
            entries = el.select("p[class=entry]");
            String full_name = "-";
            String SID = "-";
            String org_rank = "-";
            String rank_num = "-";
            if (outArray.get(2).equals("https://robertsspaceindustries.com/rsi/static/images/organization/public-orgs-thumb-redacted-bg.png")) {
                full_name = "REDACTED";
                SID = "REDACTED";
                org_rank = "REDACTED";
                rank_num = "REDACTED";
            } else if (outArray.get(2).equals("/app/tur-logo.jpg")) {

            } else {
                full_name = entries.get(0).select("a").get(0).text();
                SID = entries.get(1).select("strong[class*=value]").get(0).text();
                org_rank = entries.get(2).select("strong[class*=value]").get(0).text();
                el = el.selectFirst("div[class*=ranking]");
                if (el.select("span[class=active]").size() != 0)
                    rank_num = el.select("span[class=active]").size() + " из 5";
            }

            out = '\n' + "Полное имя организации: " + full_name + '\n' +
                    "Spectrum Identification (SID): " + SID + '\n' +
                    "Ранг игрока в организации: " + org_rank + '\n'  +
                    "Уровень игрока в организации: " + rank_num;

            outArray.add(out);
        } catch (IOException e) {
            outArray = new ArrayList<>();
            outArray.add("/app/tur-logo.jpg");
            outArray.add("Нет информации об игроке");
            outArray.add("/app/tur-logo.jpg");
            outArray.add('\n' + "Нет информации об организации игрока");
        }
        return outArray;

    }



}
