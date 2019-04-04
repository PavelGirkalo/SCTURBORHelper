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
            Element main_org = doc.selectFirst("div[class*='box-content org main']");
            Element org_info = main_org.selectFirst("div[class='thumb']");
            Element main_info = org_info.selectFirst("a[href*='/orgs/']");
            String main_orgname = main_info.attributes().get("href");
            Element main_img = main_info.selectFirst("img");
            URL path = new URL(base + main_img.attr("src"));
            //File img = new File(path);

            BufferedImage img = ImageIO.read(path);



            //take info about affiliate orgs
            Elements aff_orgs = doc.select("div[class*='box-content org affiliation']");
            String attr = "";

        } catch (IOException e) {
            System.out.println("No info");
        }
    }
}
