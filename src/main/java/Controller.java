import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import helpers.*;
import javafx.scene.image.PixelReader;

public class Controller implements Initializable {

    int count = 0;

    //объекты верхней строки первой вкладки
    @FXML
    Button openButton;
    @FXML
    Label countImages;

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

    ArrayList<ImageView> imageList = new ArrayList<>();



    @FXML
    ComboBox<String> buyBox;
    @FXML
    ComboBox<String> sellBox;


    public void initialize(URL location, ResourceBundle resources) {
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
        for (ImageView imageView: imageList)
            imageView.setVisible(false);
        imageView1.setVisible(true);

    }

        public void openFile() {
        File orig_file = FileHelper.loadFile();
        if(orig_file.getParent()!="")
            FileHelper.savePath(orig_file.getParent());

        Image image = FileHelper.extractImage(orig_file);
        imageList.get(count).setImage(image);
        count++;
        countImages.setText((Integer.toString(count)));
        if(count==5)
            openButton.setDisable(true);

    }


    public void viewNextImage(){
        for (int i = 0; i< imageList.size()-1; i++) {
            if (imageList.get(i).isVisible()){
                imageList.get(i).setVisible(false);
                imageList.get(i+1).setVisible(true);
                break;
            }
        }
    }

    public void viewPreviousImage(){
        for (int i = imageList.size()-1; i>0; i--) {
            if (imageList.get(i).isVisible()){
                imageList.get(i).setVisible(false);
                imageList.get(i-1).setVisible(true);
                break;
            }
        }


    }








}
