import model.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerInfo
{
     public static ArrayList<String> getPlayerInfo(Player player){

                    String base = "https://robertsspaceindustries.com";
                    Document doc;
                    ArrayList<String> outArray = new ArrayList<>();
                    String out;
                    try {
                        doc = Jsoup.connect(base + "/citizens/" + player.getUserName()).get();
                        //элемент с профилем игрока
                        Element el = doc.selectFirst("div[class=profile left-col]");

                        //элемент с информацией об игроке
                        el = el.selectFirst("div[class=info]");

                        //добавление в результат ссылки на логотип игрока
                        Element img_el = doc.selectFirst("div[class=thumb]");
                        outArray.add(base + img_el.select("img").attr("src"));


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

                        //добавление в результат поля информацией об игроке
                        out = "Community Moniker: " + comm + '\n' +
                                "Handle: " + handle + '\n' +
                                "Титул/ачивка: " + title + '\n'  +
                                "Зарегистрирован: " + enlisted + '\n' +
                                "Локация: " + location + '\n' +
                                "Язык: " + fluency + '\n';
                        outArray.add(out);

                        //сбор инфы об основной корпе игрока
                        el = doc.selectFirst("div[class*=main-org]");
                        //лого
                        img_el = el.selectFirst("div[class=thumb]");
                        outArray.add(base + img_el.select("img").attr("src"));

                        //инфа об орге
                        el = el.selectFirst("div[class*=inner]");
                        entries = el.select("p[class=entry]");
                        String full_name = entries.get(0).select("a").get(0).text();
                        String SID = entries.get(1).select("strong[class*=value]").get(0).text();
                        String org_rank = entries.get(2).select("strong[class*=value]").get(0).text();
                        el = el.selectFirst("div[class*=ranking]");
                        String rank_num = "-";
                        if (el.select("span[class=active]").size() != 0)
                            rank_num = el.select("span[class=active]").size() + " из 5";

                        out = '\n' + "Полное имя организации: " + full_name + '\n' +
                              "Spectrum Identification (SID): " + SID + '\n' +
                                "Ранг игрока в организации: " + org_rank + '\n'  +
                                "Уровень игрока в организации: " + rank_num;

                        outArray.add(out);
                    } catch (IOException e) {
                        outArray.add("/app/tur-logo.jpg");
                        outArray.add("No info");
                    }
                    return outArray;

                }
            }

