import helpers.FileHelper;
import model.Org;
import model.OrgList;
import model.Player;

import javax.swing.*;

public class InfoController{

    JLabel nameLabel;

    JLabel orgLabel;

    JLabel imageView;

    private Controller mainWindow;

    public InfoController(){
        nameLabel = new JLabel();
        nameLabel.setLocation(180,10);

        orgLabel = new JLabel();
        orgLabel.setLocation(180,30);

        imageView = new JLabel();
        imageView.setLocation(10,10);

    }

    public void viewPlayerInfo(Player player) {
        OrgList orgs = FileHelper.readOrgs("./src/main/resources/Orgs.csv");
        OrgList new_orgs = new OrgList(player.getOrgs());
        for (Org org : new_orgs.getOrgList()){
            if(orgs.findOrg(org.getName())!= null)
                org.setLogoPath(orgs.findOrg(org.getName()).getLogoPath());
        }



        //Scene secondScene = new Scene(secondaryLayout, 300, 300);

            //AnchorPane root = FXMLLoader.load(getClass().getResource("app/infoWindow.fxml"));
            nameLabel.setText(player.getUserName());
            orgLabel.setText(new_orgs.getOrgList().get(0).getName());
            imageView.setIcon(new ImageIcon(new_orgs.getOrgList().get(0).getLogoPath()));
            //root.getChildren().addAll(imageView,nameLabel,orgLabel);
            //root.getChildren();

            //Scene secondScene = new Scene(root);

            // New window (Stage)
            //Stage newWindow = new Stage();

            //newWindow.setTitle(player.getUserName() + " Info");
            //newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            //newWindow.setX(400);
            //newWindow.setY(300);

            //newWindow.show();

            //почитать про передачу управлния второму контроллеру
            //http://qaru.site/questions/9615004/javafx-22-using-a-second-window-with-fxml


    }




    public void setMainWindow(Controller mainWIndow) {
        this.mainWindow = mainWindow;
    }
}
