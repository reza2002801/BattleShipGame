package controllers;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import models.WatchResponse;
import utils.ChangeScene;
import utils.CurrentClient;
import utils.ServerCommunicator;

import java.io.IOException;

public class WatchGameController {
    private Thread worker;
    public Label whichTurn;
    public Label time;
    int playerNum;
    int timer;
    int turn;
    ServerCommunicator serverCommunicator;
    @FXML
    private GridPane myBoard;

    @FXML
    private GridPane oppBoard;
    public Label player1;
    public Label player2;

    int[][] myShips;
    int[][] lmyBoard;
    int[][] loppBoard;
    int[][] oppShips;
    String player11;
    String player22;
    public void initialize() throws IOException {
        this.serverCommunicator = CurrentClient.getServerCommunicator();
        serverCommunicator.send(CurrentClient.AuthenticationCode);
        serverCommunicator.send("WATCH");
        serverCommunicator.send(CurrentClient.getSpectateName());

        Gson gson = new Gson();
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String string = serverCommunicator.get();
                    WatchResponse response = gson.fromJson(serverCommunicator.get(), WatchResponse.class);
//                    player1.setText(response.player1);
//                    player2.setText(response.player2);
                    player11=response.player1;
                    player22=response.player2;
                    if(response.isfinished){
                        try {
                            new ChangeScene().ChangeScene1("../Menu.fxml",myBoard);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
//                    if (response)

                    timer = response.getTime();
                    myShips = response.getMyShips();
                    oppShips = response.getOppShips();
                    lmyBoard = response.getMyMatrix();
                    loppBoard = response.getOppMatrix();
                    turn = response.turn;
                    loadData();
                    Thread.sleep(500 );

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

    public void loadData() throws IOException {
        Platform.runLater(() -> {
            if (true) {
                player1.setText(player11);
                player2.setText(player22);
                if(turn==1){
                    whichTurn.setText(player11);
                }else if(turn==2){
                    whichTurn.setText(player22);
                }
                myBoard.getChildren().clear();
                oppBoard.getChildren().clear();
                time.setText(String.valueOf(timer));
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("../cell.fxml"));
                        AnchorPane cell = null;
                        try {
                            cell = fxmlLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        cell.setMaxHeight(30);
                        cell.setMaxWidth(30);
                        cell.setLayoutX(20);
                        myBoard.add(cell, i, j);
                        if (myShips[i][j] == 1) {
                            cell.setStyle("-fx-background-color: #0f0807;");
                        }
                        if (lmyBoard[i][j] == 1) {
                            cell.setStyle("-fx-background-color: #fc960f;");
                        }
                        if (lmyBoard[i][j] == 2) {
                            cell.setStyle("-fx-background-color: #fc523f;");
                        }
                        GridPane.setMargin(cell, new Insets(3));
                    }
                }
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("../cell.fxml"));
                        AnchorPane cell = null;
                        try {
                            cell = fxmlLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        cell.setMaxHeight(30);
                        cell.setMaxWidth(30);
                        cell.setLayoutX(20);
                        if (oppShips[i][j] == 1) {
                            cell.setStyle("-fx-background-color: #0f0807;");
                        }
                        if (loppBoard[i][j] == 2) {
                            cell.setStyle("-fx-background-color: #fc523f;");
                        }
                        if (loppBoard[i][j] == 1) {
                            cell.setStyle("-fx-background-color: #fc960f;");
                        }
                        oppBoard.add(cell, i, j);
                        GridPane.setMargin(cell, new Insets(3));
                    }
                }
            }
        });
    }

    public void back(ActionEvent event) throws Exception {
        new ChangeScene().change_scene(event,"../Menu.fxml");
    }
}
