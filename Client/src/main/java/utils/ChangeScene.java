package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ChangeScene {
    private static Logger logger = LogManager.getLogger(ChangeScene.class);
    public ChangeScene(){

    }


    public void change_scene(ActionEvent event,String xmlfilename) throws Exception{
        logger.debug("in change_scene from ChangeScene class on values"+event+"and"+xmlfilename);
        Parent sighnin= FXMLLoader.load(getClass().getResource(xmlfilename));
        Scene sign=new Scene(sighnin);
        Stage w=(Stage) ((Node)event.getSource()).getScene().getWindow();
        w.setScene(sign);
        w.show();
    }
    String url;
    GridPane gridPane;

    public void ChangeScene1(String url, GridPane gridPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource(url));
        Stage window = (Stage) gridPane.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    public void ChangeScene1(String url, Label gridPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource(url));
        Stage window = (Stage) gridPane.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    public void ChangeScene1(String url, Button gridPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource(url));
        Stage window = (Stage) gridPane.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
