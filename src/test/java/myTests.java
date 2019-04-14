import helpers.FileHelper;
import model.Org;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class myTests {
    @Test
    public void siteTest(){
        String base = "https://robertsspaceindustries.com";
        String prefix = "https://robertsspaceindustries.com/citizens/";
        String full_name;
        full_name = prefix + "cords1" + "/organizations";
        Document doc;
        try {
            doc = Jsoup.connect(full_name).get();
            //take info about main org
            ArrayList<Org> new_orgs = new ArrayList<>();
            Elements orgs = doc.select("div[class*='box-content org']");
            for(Element org : orgs) {
                Element main_info = org.selectFirst("div[class='thumb']").selectFirst("a[href*='/orgs/']");
                //имя корпы
                String main_orgname = main_info.attributes().get("href").substring(6);
                //ссылка на лого корпы
                String main_img = base + main_info.selectFirst("img").attr("src");
                new_orgs.add(new Org(main_orgname, main_img));
            }
        } catch (IOException e) {
            System.out.println("No info");
        }
    }

    @Test
    public void orgsTest(){
        String base = "https://robertsspaceindustries.com/orgs/";
        Document doc;
        try {
            doc = Jsoup.connect(base + "TURBOR").get();
            //take info about main org
            ArrayList<String> elements = new ArrayList<>();
            Element el = doc.selectFirst("div[class=inner]");
            String logo_path = el.select("img").attr("src");
            String quantity = el.select("span[class=count]").text();
            String full_name = el.select("h1").text();
            String model = el.select("li[class=model]").text();
            String commitment = el.select("li[class=commitment]").text();
            System.out.println(logo_path + '\n' + quantity+ '\n'  + full_name+ '\n'  + model+ '\n'  + commitment);


        } catch (IOException e) {
            System.out.println("No info");
        }
    }

    @Test
    public void playerTest(){
        String base = "https://robertsspaceindustries.com/citizens/";
        Document doc;
        try {
            doc = Jsoup.connect(base + "TURBOR").get();
            //take info about main org
            Element el = doc.selectFirst("div[class=info]");
            //String logo_path = el.select("img").attr("src");
            String comm = el.select("strong[class=value]").get(0).text();
            String handle = el.select("strong[class=value]").get(1).text();
            String title = el.select("span[class=value]").text();

            el = doc.selectFirst("div[class=inner]");
            String enlisted = el.select("strong[class=value]").get(0).text();
            //String location = el.select("strong[class=value]").get(1).text();
            String fluency = el.select("strong[class=value]").get(2).text();

            System.out.println("Community Moniker: " + comm + '\n' +
                                "Handle: " + handle + '\n' +
                                "Титул/ачивка: " + title + '\n'  +
                                "Зарегистрирован: " + enlisted + '\n' +
                                "Язык: " + fluency);


        } catch (IOException e) {
            System.out.println("No info");
        }
    }

}
