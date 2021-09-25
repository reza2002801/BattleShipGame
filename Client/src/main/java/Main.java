import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.CurrentClient;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //SettingPage.fxml LoginPage.fxml UserMiniProfileComponent TimeLinePage
//        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 1000, 700));
//        primaryStage.show();
        Platform.runLater(()->{
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setTitle("Battle Ship");
            primaryStage.setScene(new Scene(root, 954, 703));
            primaryStage.show();

        });
    }


    public static void main(String[] args) throws IOException {
        CurrentClient manager = new CurrentClient();
        launch(args);
    }
}
