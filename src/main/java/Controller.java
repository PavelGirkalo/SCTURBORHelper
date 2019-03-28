import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    ImageView imageView;
    @FXML
    Button openButton;
    @FXML
    ComboBox<String> buyBox;
    @FXML
    ComboBox<String> sellBox;

    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<String> locations = readLocations();
        for (int i = 0; i < locations.size(); i++) {
            buyBox.getItems().add(locations.get(i));
            sellBox.getItems().add(locations.get(i));
        }

    }

    private ArrayList<String> readLocations() {
        try {
            BufferedReader CSVFile = new BufferedReader(new FileReader("./src/main/resources/locations.csv"));
            ArrayList<String> rows = new ArrayList<String>();
            String dataRow;
            int i = 0;
            while ((dataRow = CSVFile.readLine()) != null) {
                i++;
                rows.add(dataRow.substring(0,dataRow.length()-1));
            }
            return rows;
        } catch (IOException ex) {
            System.out.println("");
            return null;
        }
    }

        public void openFile() {
        try {
            JFileChooser chooser = new JFileChooser("./src/main/");
            int r = chooser.showOpenDialog(chooser.getParent());
            if (r != JFileChooser.APPROVE_OPTION) return;
            Image image = new Image(chooser.getSelectedFile().toURI().toURL().toString());
            imageView.setImage(image);
        } catch(IOException e) {
            System.out.println("File not load");
        }
    }





}
