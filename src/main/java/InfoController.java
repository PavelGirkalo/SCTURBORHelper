import helpers.FileHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Org;
import model.OrgList;
import model.Player;

import java.io.IOException;

public class InfoController{
    @FXML
    Label nameLabel;

    @FXML
    Label orgLabel;

    @FXML
    ImageView imageView;

    public InfoController(){
        //nameLabel = new Label();
        //orgLabel = new Label();
        //imageView = new ImageView(new Image(""));

    }

    public void viewPlayerInfo(Player player) {
        OrgList orgs = FileHelper.readOrgs("./src/main/resources/Orgs.csv");
        OrgList new_orgs = new OrgList(player.getOrgs());
        for (Org org : new_orgs.getOrgList()){
            if(orgs.findOrg(org.getName())!= null)
                org.setLogo_path(orgs.findOrg(org.getName()).getLogo_path());
        }



        //Scene secondScene = new Scene(secondaryLayout, 300, 300);
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("app/infoWindow.fxml"));
            nameLabel = new Label(player.getUserName());
            orgLabel = new Label(new_orgs.getOrgList().get(0).getName());
            imageView = new ImageView(new Image(new_orgs.getOrgList().get(0).getLogo_path()));
            //root.getChildren().addAll(imageView,nameLabel,orgLabel);
            //root.getChildren();

            Scene secondScene = new Scene(root);

            // New window (Stage)
            Stage newWindow = new Stage();

            newWindow.setTitle(player.getUserName() + " Info");
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            //newWindow.setX(400);
            //newWindow.setY(300);

            newWindow.show();

            //почитать про передачу управлния второму контроллеру
            //http://qaru.site/questions/9615004/javafx-22-using-a-second-window-with-fxml

        } catch(IOException e){}
    }


    public Label getNameLabel() {
        return nameLabel;
    }

    public Label getOrgLabel() {
        return orgLabel;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
