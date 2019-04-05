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
        FileHelper.readOrgs("./src/main/resources/Orgs.csv");
    }

}
