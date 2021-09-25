package controllers;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import models.SpectateResponse;
import utils.ChangeScene;
import utils.CurrentClient;
import utils.ServerCommunicator;

import java.io.IOException;
import java.util.LinkedList;

public class WatchingGameController {
    ServerCommunicator serverCommunicator;
    LinkedList<SpectateResponse> sr;
    @FXML
    private GridPane grid;
    @FXML
    private AnchorPane component;
    @FXML
    private Label name;
    @FXML
    private Label destroyed;
    @FXML
    private Label turns;

    public void initialize() throws IOException {
        serverCommunicator = CurrentClient.getServerCommunicator();
        serverCommunicator.send(CurrentClient.AuthenticationCode);
        serverCommunicator.send("SPECTATE");
        String str = serverCommunicator.get();
        Gson gson = new Gson();
        sr = new LinkedList<>();
        for (int i = 0; i < Integer.parseInt(str); i++) {
            String data = serverCommunicator.get();
            sr.add(gson.fromJson(data,SpectateResponse.class));
        }
        loadData();
    }

    public void loadData() throws IOException {
        grid.getChildren().clear();
        for (int i = 0; i < sr.size(); i++) {
            SpectateResponse spectateResponse = sr.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../gameComponent.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            GameComponentController itemController = fxmlLoader.getController();
            itemController.player1=spectateResponse.getPlayer1();
            itemController.player2=spectateResponse.getPlayer2();
            itemController.setName(spectateResponse.getPlayer1() + " - " + spectateResponse.getPlayer2());
            itemController.setDestroyed(spectateResponse.getDestroyed());
            itemController.setTurns(spectateResponse.getTurns());
            int finalI = i;
            anchorPane.setOnMouseClicked(e -> {
                try {
                    click(sr.get(finalI));
                } catch (Exception ioException) {
                    ioException.printStackTrace();
                }
            });
            grid.add(anchorPane, 1, i);
            grid.setLayoutX(-20);
            GridPane.setMargin(anchorPane, new Insets(10));
        }
    }

    private void click(SpectateResponse spectateComponent) throws Exception {
        CurrentClient.setSpectateName(spectateComponent.getPlayer1());
//        new ChangeScene().change_scene(event,"../WatchingGame.fxml");
        new ChangeScene().ChangeScene1("../WatchGame.fxml", grid);
    }
}
