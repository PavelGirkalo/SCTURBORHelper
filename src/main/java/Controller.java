import helpers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Org;
import model.OrgList;
import model.Player;
import model.PlayerList;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    int count = 0;
    PlayerList players;
    OrgList orgs;


    @FXML
    Tab Tab1;
    @FXML
    Tab Tab11;
    @FXML
    Tab Tab2;
    @FXML
    Tab Tab3;


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
    TableColumn<Player, Character> flagColumn;

    //таблица с организациями и ее столбцы
    @FXML
    TableView<Org> orgsTable;
    @FXML
    TableColumn<Org, String> orgName;
    @FXML
    TableColumn<Org, Integer> orgQuantity;
    @FXML
    Label keyField;



    //объекты второй страницы
    @FXML
    Button openButton1;
    @FXML
    Button clearButton1;
    @FXML
    Button recognButton1;
    @FXML
    ImageView imageView11;
    @FXML
    TextArea tempList1;


    //объекты третьей страницы

    @FXML
    Button allFriends;
    @FXML
    Button allEnemies;
    @FXML
    Label quaLabel;
    @FXML
    TableView<Player> playersTable;
    @FXML
    TableColumn<Player, String> nameColumn;
    @FXML
    TableColumn<Player, String> flColumn;




    //объекты четвертой страницы
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
        //nicknameColumn.setCellFactory(new ToolTipPlayerInfoFactory<>());
        orgsColumn.setCellValueFactory(new PropertyValueFactory<>("orgs"));
        //orgsColumn.setCellFactory(new ToolTipCellFactory<>());
        flagColumn.setCellValueFactory(new PropertyValueFactory<>("flag"));

        orgName.setCellValueFactory(new PropertyValueFactory<>("name"));
        //orgName.setCellFactory(new ToolTipOrgInfoFactory<>());
        orgQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        //настройка таблицы на второй странице
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));




        //заполнение списка торговых локаций на странице торговли

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

    public void openQuestFile() {
        try {
            File orig_file = FileHelper.loadFile();
            if (orig_file.getParent() != "") {
                Image image = FileHelper.extractQuest(orig_file);
                imageView11.setImage(image);
            }
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
        //flagColumn.setComparator(flagColumn.getComparator().reversed());
        flagColumn.setComparator(flagColumn.getComparator());
        finalTable.getSortOrder().add(flagColumn);



        orgs = ModelHelper.fillOrgModel(players);
        orgsTable.setItems(TableHelper.fillOrgTable(orgs));
        orgQuantity.setComparator(orgQuantity.getComparator());
        orgsTable.getSortOrder().add(orgQuantity);

    }


    public void viewInfoAboutPlayer() {
        AnchorPane root = new AnchorPane();

        //размещение картинки с логотипом игрока
        ImageView player_logo = new ImageView();
        player_logo.setX(5);
        player_logo.setY(5);

        //размещение картинки с логотипом организации
        ImageView org_logo = new ImageView();
        org_logo.setX(5);
        org_logo.setY(175);

        //размещение поля с информацией об игроке
        TextArea player_info = new TextArea();
        player_info.setPrefRowCount(6);
        player_info.setLayoutX(175);
        player_info.setLayoutY(5);
        player_info.setPrefWidth(300);
        player_info.setPrefHeight(165);

        //размещение поля с информацией об игроке
        TextArea org_info = new TextArea();
        org_info.setPrefRowCount(5);
        org_info.setLayoutX(175);
        org_info.setLayoutY(175);
        org_info.setPrefWidth(300);
        org_info.setPrefHeight(165);


        ArrayList<String> result;
        try {
            result = ParserHelper.getPlayerInfo(finalTable.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            result = new ArrayList<>();
            result.add("https://robertsspaceindustries.com/rsi/static/images/organization/public-orgs-thumb-redacted-bg.png");
            result.add("Таблица пуста");
            result.add("https://robertsspaceindustries.com/rsi/static/images/organization/public-orgs-thumb-redacted-bg.png");
            result.add("");
        }

        //заполнение изображений и полей с информацией
        player_logo.setImage(new Image(result.get(0),165,165,false,false));
        player_info.setText(result.get(1));
        player_info.setEditable(false);
        org_logo.setImage(new Image(result.get(2),165,165,false,false));
        org_info.setText(result.get(3));
        org_info.setEditable(false);


        //добавление элементов на сцену
        root.getChildren().add(player_info);
        root.getChildren().add(org_logo);
        root.getChildren().add(org_info);
        root.getChildren().add(player_logo);

        Stage stage = new Stage();
        stage.setTitle("Информация об игроке");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/app/tur-logo.jpg")));
        stage.setWidth(500);
        stage.setHeight(385);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(finalTable.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void viewInfoAboutOrg() {
        AnchorPane root = new AnchorPane();
        ImageView org_logo = new ImageView();
        org_logo.setX(5);
        org_logo.setY(5);

        //размещение поля с информацией об игроке
        TextArea org_info = new TextArea();
        org_info.setPrefRowCount(5);
        org_info.setLayoutX(175);
        org_info.setLayoutY(5);
        org_info.setPrefWidth(300);
        org_info.setPrefHeight(165);

        TextArea players_info = new TextArea();
        players_info.setPrefRowCount(5);
        players_info.setLayoutX(5);
        players_info.setLayoutY(180);
        players_info.setPrefWidth(300);
        players_info.setPrefHeight(165);


        ArrayList<String> result;
        try {
            result = ParserHelper.getOrgInfo(orgsTable.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            result = new ArrayList<>();
            result.add("https://robertsspaceindustries.com/rsi/static/images/organization/public-orgs-thumb-redacted-bg.png");
            result.add("Нет информации об организации");

        }
        org_logo.setImage(new Image(result.get(0),165,165,false,false));
        org_info.setText(result.get(1));
        org_info.setEditable(false);

        String pl = "";
        for(Player player:players.getPlayerList()){
            for(Org org:player.getOrgs()){
                if(org.getName().equals(orgsTable.getSelectionModel().getSelectedItem().getName())){
                    pl+=player.getUserName();
                    pl+="\n";
                }

            }

        }
        players_info.setText(pl);


        root.getChildren().add(org_logo);
        root.getChildren().add(org_info);
        root.getChildren().add(players_info);


        Stage stage = new Stage();
        stage.setTitle("Информация об организации");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/app/tur-logo.jpg")));
        stage.setWidth(500);
        stage.setHeight(420);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(orgsTable.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void showFriends(ActionEvent actionEvent) {
        PlayerList whiteList = FileHelper.readPlayers("./resources/Friends.csv");
        ObservableList<Player> list = FXCollections.observableArrayList();
        for (Player player : whiteList.getPlayerList())
            list.add(player);
        playersTable.setItems(list);
        quaLabel.setText("Количество: " + list.size());
    }

    public void showEnemies(ActionEvent actionEvent) {
        PlayerList whiteList = FileHelper.readPlayers("./resources/Enemies.csv");
        ObservableList<Player> list = FXCollections.observableArrayList();
        for (Player player : whiteList.getPlayerList())
            list.add(player);
        playersTable.setItems(list);
        quaLabel.setText("Количество: " + list.size());
    }


    public void activateAdmin() {
        Tab2.setDisable(false);
        Tab3.setDisable(false);
        /*String loc_path = "./resources/Locations.csv";
        ArrayList<String> locations = FileHelper.readCSV(loc_path);
        for (int i = 0; i < locations.size(); i++) {
            buyBox.getItems().add(locations.get(i));
            sellBox.getItems().add(locations.get(i));
        }*/
    }
    public void deactivateAdmin() {
        Tab2.setDisable(true);
        Tab3.setDisable(true);
    }

    public void clearImage(ActionEvent actionEvent) {
        imageView11.setImage(null);
    }

    public void recognizeQuest(ActionEvent actionEvent) {
        //File orig_file = new File("C:\\12345\\_Miss\\ScreenShot0018.jpg");
        //Image image = FileHelper.extractQuest(orig_file);

        ITesseract instance = new Tesseract();
        instance.setDatapath("resources/tessdata");

        //считывание из файлов изображений
        BufferedImage buff_image = SwingFXUtils.fromFXImage(imageView11.getImage(),null);

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
        try {
            ImageIO.write(crop_image, "jpg", file);
        } catch(IOException e){}
        //распознавание текста
        String result = "";

        try {
            result = instance.doOCR(file);
        } catch (TesseractException e1) {
            //e1.printStackTrace();
        }
        file.delete();

        tempList1.setText(result);

    }
}

