import helpers.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    int count = 0;
    PlayerList players;
    OrgList orgs;

    //объекты верхней строки первой вкладки
    @FXML
    Button openButton;
    @FXML
    Button clearButton;
    @FXML
    Label countImages;
    @FXML
    Label currentPage;


    //объекты левой области первой вкладки (картинки и кнопки листания)
    @FXML
    ImageView imageView1;
    @FXML
    ImageView imageView2;
    @FXML
    ImageView imageView3;
    @FXML
    ImageView imageView4;
    @FXML
    ImageView imageView5;
    @FXML
    ImageView imageView6;
    @FXML
    Button prevButton;
    @FXML
    Button nextButton;
    //список изображений
    ArrayList<ImageView> imageList = new ArrayList<>();


    //объекты правой области первой вкладки
    @FXML
    Button recognButton;
    @FXML
    TextArea tempList;
    @FXML
    Button viewButton;

    //таблица с игроками и ее столбцы
    @FXML
    TableView<Player> finalTable;
    @FXML
    TableColumn<Player, Integer> numberColumn;
    @FXML
    TableColumn<Player, String> nicknameColumn;
    @FXML
    TableColumn<Player, String> orgsColumn;
    @FXML
    TableColumn<Player, Flag> flagColumn;

    //таблица с организациями и ее столбцы
    @FXML
    TableView<Org> orgsTable;
    @FXML
    TableColumn<Org, String> orgName;
    @FXML
    TableColumn<Org, Integer> orgQuantity;


    //объекты второй страницы
    @FXML
    Button allOrgs;

    //объекты третьей страницы
    @FXML
    ComboBox<String> buyBox;
    @FXML
    ComboBox<String> sellBox;

    public void initialize(URL location, ResourceBundle resources) {
        //создание модели данных для таблицы с игроками на сервере
        players = new PlayerList(null);
        orgs = new OrgList(new ArrayList<>());

        //настройка связи между моделью с данными и таблицей на экране
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        orgsColumn.setCellValueFactory(new PropertyValueFactory<>("orgs"));
        flagColumn.setCellValueFactory(new PropertyValueFactory<>("flag"));
        orgsColumn.setCellFactory(new ToolTipCellFactory<>());

        orgName.setCellValueFactory(new PropertyValueFactory<>("name"));
        orgQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        //заполнение списка торговых локаций на странице торговли
        String loc_path = "./resources/Locations.csv";
        ArrayList<String> locations = FileHelper.readCSV(loc_path);
        for (int i = 0; i < locations.size(); i++) {
            buyBox.getItems().add(locations.get(i));
            sellBox.getItems().add(locations.get(i));
        }

        //составление списка из окон для отображения изображений
        imageList.add(imageView1);
        imageList.add(imageView2);
        imageList.add(imageView3);
        imageList.add(imageView4);
        imageList.add(imageView5);
        imageList.add(imageView6);
        viewCurrentImage(1);
        prevButton.setDisable(true);
    }

    public void openFile() {
        try {
            File orig_file = FileHelper.loadFile();
            if (orig_file.getParent() != "") {
                FileHelper.savePath(orig_file.getParent());
                Image image = FileHelper.extractImage(orig_file);
                imageList.get(count).setImage(image);
            }
            count++;
            viewCurrentImage(count);
            countImages.setText((Integer.toString(count)));
            if (count == 6)
                openButton.setDisable(true);
        } catch (NullPointerException e) {
            System.out.println("File not found");
        }
    }

    public void clearAllImages() {
        if (count == 6)
            openButton.setDisable(false);
        count = 0;
        countImages.setText((Integer.toString(count)));
        viewCurrentImage(1);
        for (ImageView im : imageList) {
            im.setImage(null);
        }
        //files = new ArrayList<>();
        viewCurrentImage(1);
    }

    public void viewCurrentImage(int number) {
        for (ImageView im : imageList) {
            im.setVisible(false);
        }
        imageList.get(number - 1).setVisible(true);
        currentPage.setText(Integer.toString(number));
        prevButton.setDisable(false);
        nextButton.setDisable(false);
        if (number == 1) prevButton.setDisable(true);
        else if (number == 6) nextButton.setDisable(true);
    }

    public void viewNextImage() {
        for (int i = 0; i < imageList.size() - 1; i++) {
            if (imageList.get(i).isVisible()) {
                imageList.get(i).setVisible(false);
                imageList.get(i + 1).setVisible(true);
                currentPage.setText(Integer.toString(i + 2));
                if (i == 0) prevButton.setDisable(false);
                else if (i == 4) nextButton.setDisable(true);
                break;
            }
        }
    }

    public void viewPreviousImage() {
        for (int i = imageList.size() - 1; i > 0; i--) {
            if (imageList.get(i).isVisible()) {
                imageList.get(i).setVisible(false);
                imageList.get(i - 1).setVisible(true);
                currentPage.setText(Integer.toString(i));
                if (i == 5) nextButton.setDisable(false);
                else if (i == 1) prevButton.setDisable(true);
                break;
            }
        }
    }

    public void recognizeImages() {
        //распознавание
        String nicknameList = RecognHelper.recognImages(imageList);
        //вывод распознанного текста в текстовое поле для проверки корректности распознавания
        tempList.setText(tempList.getText() + "\n" + nicknameList);

    }

    public void viewInfo() {

        //запись распознанных имен в модель
        players = ModelHelper.fillModel(players, tempList.getText());
        // запрос на сервер для выяснения списка корп у игроков
        ParserHelper.getInfo(players);
        //заполнение флага свой/чужой с проверкой игроков в черном и белом списках
        players = ModelHelper.checkPlayersFlag(players);

        //вывод модели на экран
        finalTable.setItems(TableHelper.fillTable(players));
        orgs = ModelHelper.fillOrgModel(players);
        orgsTable.setItems(TableHelper.fillOrgTable(orgs));
        orgQuantity.setComparator(orgQuantity.getComparator());
        orgsTable.getSortOrder().add(orgQuantity);

    }


    public void viewInfoAboutPlayer() throws IOException {
        /*
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/infoWindow.fxml"));
        AnchorPane infoWindow = new AnchorPane();
        InfoController infoController = new InfoController();
        infoController.setMainWindow(this);

        Stage stage = new Stage();
        stage.setHeight(300);
        stage.setWidth(300);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(finalTable.getScene().getWindow());
        Scene scene = new Scene(infoWindow);
        stage.setScene(scene);
        stage.show();
        infoController.viewPlayerInfo(finalTable.getSelectionModel().getSelectedItem());*/
    }
}

