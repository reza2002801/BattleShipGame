package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.ChangeScene;
import utils.CurrentClient;
import utils.ServerCommunicator;

import java.io.IOException;

public class UserProfileController {
    ServerCommunicator serverCommunicator;
    @FXML
    private Label username;

    @FXML
    private Label winsNum;

    @FXML
    private Label lossNum;

    @FXML
    private Label points;


    public void initialize() throws IOException {
        serverCommunicator = CurrentClient.getServerCommunicator();
        serverCommunicator.send(CurrentClient.getAuthenticationCode());
        serverCommunicator.send("PROFILE");
        username.setText(serverCommunicator.get());
        winsNum.setText(serverCommunicator.get());
        lossNum.setText(serverCommunicator.get());
        points.setText(serverCommunicator.get());
    }

    public void back(ActionEvent event) throws Exception {
        new ChangeScene().change_scene(event,"../Menu.fxml");
    }
}
