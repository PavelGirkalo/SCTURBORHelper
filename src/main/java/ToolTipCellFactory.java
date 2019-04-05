import helpers.ParserHelper;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import model.OrgList;
import model.Player;
import model.PlayerList;

import java.util.ArrayList;

public class ToolTipCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
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
                }else {
                    PlayerList list = new PlayerList(new ArrayList<>());
                    OrgList orgs = new OrgList(new ArrayList<>());
                    list.add(new Player(item.toString()));
                    ParserHelper.getInfo(list,orgs);
                    setTooltip(new Tooltip(list.getPlayerList().get(0).toString()));
                    setText(item.toString());
                }
            }
        };
    }
}