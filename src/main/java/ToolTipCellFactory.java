import helpers.TableHelper;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import model.PlayerModel;
import model.PlayersList;

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
                    PlayersList list = new PlayersList(new ArrayList<>());
                    list.add(new PlayerModel(item.toString()));
                    TableHelper.getInfo(list);
                    setTooltip(new Tooltip(list.getPlayersList().get(0).toString()));
                    setText(item.toString());
                }
            }
        };
    }
}