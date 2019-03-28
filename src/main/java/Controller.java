import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.IOException;

public class Controller {
    @FXML
    ImageView imageView;
    @FXML
    Button openButton;

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
