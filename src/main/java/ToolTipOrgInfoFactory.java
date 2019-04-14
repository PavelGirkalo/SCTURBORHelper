import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class ToolTipOrgInfoFactory <S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>>
    {
        @Override
        public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>(){
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                //Здесь необходимо установить текст ячейки
                //И заодно текст всплывающей подсказки
                if (item==null){
                    setTooltip(null);
                    setText(null);
                } else {
                    String base = "https://robertsspaceindustries.com/orgs/";
                    Document doc;
                    String out;
                    try {
                        doc = Jsoup.connect(base + item.toString()).get();
                        //take info about main org
                        ArrayList<String> elements = new ArrayList<>();
                        Element el = doc.selectFirst("div[class=inner]");
                        String logo_path = el.select("img").attr("src");
                        String quantity = el.select("span[class=count]").text();
                        String full_name = el.select("h1").text();
                        String model = el.select("li[class=model]").text();
                        String commitment = el.select("li[class=commitment]").text();
                        out = "Имя: " + full_name + "\n" + "Количество: " + quantity + '\n' + "Тип корпорации: " + model+ '\n' + "Подход: " + commitment;

                    } catch (IOException e) {
                        out = "No info";
                    }
                    setTooltip(new Tooltip(out));
                    setText(item.toString());
                }
            }
        };
    }
}
