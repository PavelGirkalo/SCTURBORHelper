import helpers.FileHelper;
import helpers.RecognHelper;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import model.Org;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;



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

    @Test
    public void recognTest() throws IOException {
        File orig_file = new File("C:\\12345\\_Miss\\ScreenShot0018.jpg");
        Image image = FileHelper.extractQuest(orig_file);

        ITesseract instance = new Tesseract();
        instance.setDatapath("resources/tessdata");

        //считывание из файлов изображений
        BufferedImage buff_image = SwingFXUtils.fromFXImage(image,null);


        BufferedImage crop_image = buff_image;

            //негатив
            short[] negative = new short[256 * 1];
            for (int i = 0; i < 256; i++) negative[i] = (short) (255 - i);
            ShortLookupTable table = new ShortLookupTable(0, negative);
            LookupOp op2 = new LookupOp(table, null);
            crop_image = op2.filter(crop_image, crop_image);

            //ч-б преобразование
            ColorConvertOp grayOp = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
            crop_image = grayOp.filter(crop_image, crop_image);

            //осветление
            float a = 1.3f;
            RescaleOp op1 = new RescaleOp(a, 0, null);
            crop_image = op1.filter(crop_image, crop_image);


            File file = new File("resources/temp.jpg");
            ImageIO.write(crop_image, "jpg", file);

        //распознавание текста
        String result = "";

            try {
                result = instance.doOCR(file);
            } catch (TesseractException e1) {
                //e1.printStackTrace();
            }
            file.delete();

        System.out.println("Result: \n" + result);
/*
        // Instantiates a client
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        // The text to translate
        String text = result;

        // Translates some text into Russian
        Translation translation =
                translate.translate(
                        text,
                        TranslateOption.sourceLanguage("en"),
                        TranslateOption.targetLanguage("ru"));


        System.out.printf("Text: %s%n", text);
        System.out.printf("Translation: %s%n", translation.getTranslatedText());
*/



    }

}
