import helpers.FileHelper;
import helpers.ModelHelper;
import helpers.RecognHelper;
import helpers.TableHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Flag;
import model.PlayerModel;
import model.PlayersList;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    int count = 0;
    ArrayList<File> files = new ArrayList<>();
    PlayersList players;
    ObservableList<PlayerModel> list = FXCollections.observableArrayList();


    //объекты верхней строки первой вкладки
    @FXML
    Button openButton;
    @FXML
    Button clearButton;
    @FXML
    Label countImages;
    @FXML
    Label currentPage;


    @FXML
    Button recognButton;

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
    Button prevButton;
    @FXML
    Button nextButton;
    //список изображений
    ArrayList<ImageView> imageList = new ArrayList<>();


    //объекты правой области первой вкладки
    @FXML
    TextArea tempList;
    @FXML
    Button viewButton;
    @FXML
    TableView<PlayerModel> finalTable;
    @FXML
    TableColumn<PlayerModel,Integer> numberColumn;
    @FXML
    TableColumn<PlayerModel,String> nicknameColumn;
    @FXML
    TableColumn<PlayerModel,String> orgsColumn;
    @FXML
    TableColumn<PlayerModel,Flag> flagColumn;



    //объекты третьей страницы
    @FXML
    ComboBox<String> buyBox;
    @FXML
    ComboBox<String> sellBox;

    public void initialize(URL location, ResourceBundle resources) {
        //создание модели данных для таблицы с игроками на сервере
        players = new PlayersList(null);
        finalTable.setItems(list);

        //заполнение списка торговых локаций на странице торговли
        ArrayList<String> locations = FileHelper.readLocations();
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
        viewCurrentImage(1);
        prevButton.setDisable(true);
    }

    public void openFile() {
        try {
            File orig_file = FileHelper.loadFile();
            if(orig_file.getParent() != "") {
                FileHelper.savePath(orig_file.getParent());
                Image image = FileHelper.extractImage(orig_file);
                imageList.get(count).setImage(image);
            }
            count++;
            viewCurrentImage(count);
            countImages.setText((Integer.toString(count)));
            if(count==5)
                openButton.setDisable(true);
        } catch (NullPointerException e){
            System.out.println("File not found");
        }
    }

    public void clearAllImages() {
        if(count==5)
            openButton.setDisable(false);
        count = 0;
        countImages.setText((Integer.toString(count)));
        viewCurrentImage(1);
        for (ImageView im : imageList){
            im.setImage(null);
        }
        files = new ArrayList<>();
        viewCurrentImage(1);
    }

    public void viewCurrentImage(int number){
        for (ImageView im:imageList){
            im.setVisible(false);
        }
        imageList.get(number-1).setVisible(true);
        currentPage.setText(Integer.toString(number));
        prevButton.setDisable(false);
        nextButton.setDisable(false);
        if(number == 1) prevButton.setDisable(true);
        else if(number == 5) nextButton.setDisable(true);
    }

    public void viewNextImage(){
        for (int i = 0; i< imageList.size()-1; i++) {
            if (imageList.get(i).isVisible()){
                imageList.get(i).setVisible(false);
                imageList.get(i+1).setVisible(true);
                currentPage.setText(Integer.toString(i+2));
                if(i==0) prevButton.setDisable(false);
                else if(i==3) nextButton.setDisable(true);
                break;
            }
        }
    }

    public void viewPreviousImage(){
        for (int i = imageList.size()-1; i>0; i--) {
            if (imageList.get(i).isVisible()){
                imageList.get(i).setVisible(false);
                imageList.get(i-1).setVisible(true);
                currentPage.setText(Integer.toString(i));
                if(i==4) nextButton.setDisable(false);
                else if(i==1) prevButton.setDisable(true);
                break;
            }
        }
    }

    public void recognizeImages() {
        //распознавание
        String nicknameList = RecognHelper.recognImages(imageList);
        tempList.setText(tempList.getText() + nicknameList);

    }

    public void viewInfo(){
        //запись распознанных имен в модель
        players = ModelHelper.fillModel(players,tempList.getText());
        // запрос на сервер для выяснения списка корп у игроков
        TableHelper.getInfo(players);
        //заполнение флага свой/чужой с проверкой игроков в черном и белом списках

        //вывод модели на экран
        ObservableList<PlayerModel> new_list = TableHelper.fillTable(list,players);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        orgsColumn.setCellValueFactory(new PropertyValueFactory<>("orgs"));
        flagColumn.setCellValueFactory(new PropertyValueFactory<>("flag"));
        finalTable.setItems(new_list);

    }











}
