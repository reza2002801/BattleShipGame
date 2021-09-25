package controllers;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import models.User;
import utils.ChangeScene;
import utils.CurrentClient;
import utils.ServerCommunicator;

import java.io.IOException;
import java.util.LinkedList;

public class ResultBoardController {
    ServerCommunicator ServerCommunicator;
    LinkedList<User> users;
    @FXML
    private Label username;

    @FXML
    private GridPane grid;

    public void initialize() throws IOException {
        ServerCommunicator = CurrentClient.getServerCommunicator();
        ServerCommunicator.send(CurrentClient.AuthenticationCode);
        ServerCommunicator.send("LEADERBOARD");
        int size = Integer.parseInt(ServerCommunicator.get());
        Gson gson = new Gson();
        users = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            String str = ServerCommunicator.get();
            users.add(gson.fromJson(str,User.class));
        }
        loadData();
    }

    private void loadData() throws IOException {
        double t=0;
        for (int i = 0; i < users.size(); i++) {
            User us = users.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ResultComponent.fxml"));
            t+=110;
            grid.setPrefHeight(t);
            try {
                grid.add((Node)loader.load(),0,i+1);
                grid.setVgap(5);
                ResultComponentController childController=loader.getController();
                childController.setOffline(us.Online());
                childController.setName(us.getUsername() + " - " + us.getScores());
//                childController.updateMiniProfile(currentUser.getBlackList().get(i));
//                childController.setParentController(this);
//                gp.setLayoutX(+150);
                grid.setLayoutY(-10);

            }catch (Exception e){
                e.printStackTrace();
            }

//            User us = users.get(i);
////            FXMLLoader fxmlLoader = new FXMLLoader();
////            FXMLLoader fxmlLoader = new FXMLLoader();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ResultComponent.fxml"));
////            fxmlLoader.setLocation(getClass().getResource("../ResultComponent.fxml"));
//
//            ResultComponentController lc = loader.getController();
//            lc.setOffline(us.Online());
//            lc.setName(us.getUsername() + " - " + us.getWin());
//            grid.add((Node)loader.load(), 0, i+1 );
//            GridPane.setMargin(cell, new Insets(3));
        }
    }

    public void back(ActionEvent event) throws Exception {
        new ChangeScene().change_scene(event,"../Menu.fxml");
    }
}
