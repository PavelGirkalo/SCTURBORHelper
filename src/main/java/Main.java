import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/app/sample.fxml"));
        primaryStage.setTitle("Star Citizen TURBOR Helper");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/app/tur-logo.jpg")));
        primaryStage.setScene(new Scene(root, 1400, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
