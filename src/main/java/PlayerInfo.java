import model.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PlayerInfo
{
     public static String getPlayerInfo(Player player){

                    String base = "https://robertsspaceindustries.com/citizens/";
                    Document doc;
                    String out;
                    try {
                        doc = Jsoup.connect(base + player.getUserName()).get();
                        Element el = doc.selectFirst("div[class=info]");
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
                                "Язык: " + fluency;
                    } catch (IOException e) {
                        out = "No info";
                    }
                    return out;

                }
            }

