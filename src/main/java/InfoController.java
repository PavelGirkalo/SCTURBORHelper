import helpers.FileHelper;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import model.Org;
import model.OrgList;
import model.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;

public class InfoController{

    TextArea tempList;

    private Controller mainWindow;

    public InfoController(){
        tempList = new TextArea();

    }

    public void viewPlayerInfo(Player player) {
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
            tempList.setText(out);
        } catch (IOException e) {
            out = "No info";
            tempList.setText(out);
        }


            //Scene secondScene = new Scene(secondaryLayout, 300, 300);
            //AnchorPane root = FXMLLoader.load(getClass().getResource("app/infoWindow.fxml"));
            //root.getChildren().addAll(imageView,nameLabel,orgLabel);
            //root.getChildren();

            //Scene secondScene = new Scene(root);

            // New window (Stage)
            //Stage newWindow = new Stage();

            //newWindow.setTitle(player.getUserName() + " Info");
            //newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            //newWindow.setX(400);
            //newWindow.setY(300);

            //newWindow.show();

            //почитать про передачу управлния второму контроллеру
            //http://qaru.site/questions/9615004/javafx-22-using-a-second-window-with-fxml


    }




    public void setMainWindow(Controller mainWIndow) {
        this.mainWindow = mainWindow;
    }
}
