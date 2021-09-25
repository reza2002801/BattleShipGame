package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import utils.ChangeScene;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button ap;
    @FXML
    void Resultboard(ActionEvent event) throws Exception {
        new ChangeScene().change_scene(event,"../ResultBoard.fxml");
    }

    @FXML
    void profile(ActionEvent event) throws Exception {
//        new ChangeScene().change_scene(event,"../UserProfile.fxml");
        new ChangeScene().ChangeScene1("../UserProfile.fxml",ap);
    }

    @FXML
    void watchGame(ActionEvent event) throws Exception {
        new ChangeScene().change_scene(event,"../WatchingGame.fxml");
    }

    @FXML
    void newGame(ActionEvent event) throws Exception {
        new ChangeScene().change_scene(event,"../NewGame.fxml");
    }
}
