package controllers;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import models.Event;
import models.MainResponse;
import models.Response;
import models.User;
import utils.ChangeScene;
import utils.CurrentClient;
import utils.ServerCommunicator;

import java.io.IOException;

public class GameUserInterFaceController {
    public Label whichTurn;
    public Label time;
    public Button change;
    int playerNum;
    int timer;
    int turn;
    ServerCommunicator serverCommunicator;
    @FXML
    private GridPane myBoard;
    @FXML
    public Label UserName;
    @FXML
    public Label res;

    @FXML
    private GridPane oppBoard;
    String player1;
    String player2;

    int[][] myShips;
    int[][] lmyBoard;
    int[][] loppBoard;
    public void initialize() throws IOException {
//        UserName.setText(CurrentClient)
        this.serverCommunicator = CurrentClient.getServerCommunicator();
        serverCommunicator.send(CurrentClient.AuthenticationCode);
        serverCommunicator.send("GAME");
        playerNum = Integer.parseInt(serverCommunicator.get());
        Gson gson = new Gson();
        String str = serverCommunicator.get();
        Response responses = gson.fromJson(str, Response.class);
        loppBoard = responses.getOppMatrix();
        lmyBoard = responses.getMyMatrix();
        myShips = responses.getMyShips();
        player1=responses.getPlayer1();
        player2=responses.getPlayer2();
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    serverCommunicator.send("2");
                    MainResponse response = gson.fromJson(serverCommunicator.get(), MainResponse.class);
                    timer = response.getTime();
                    turn = response.getTurn();

                    if (response.getIsFinished()){
                        Platform.runLater(() ->{
                            try {

//                                serverCommunicator.send("Exit");
                                new ChangeScene().ChangeScene1("../Menu.fxml",myBoard);
                                serverCommunicator.send("Exit");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
//                            try {
//                                Thread.sleep(400 );
//                                serverCommunicator.send(CurrentClient.AuthenticationCode);
//                                serverCommunicator.send("Exit");
//                            } catch (InterruptedException | IOException e) {
//                                e.printStackTrace();
//                            }
//                            System.out.println('s');

                        });
                        break;
                    }
                    loadData();
                    Thread.sleep(400 );
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }
    public void loadData() throws IOException {
        Platform.runLater(() -> {
            if(CurrentClient.Username.equals(player1)){
                UserName.setText(player2);
            }else if(CurrentClient.Username.equals(player2)) {
                UserName.setText(player1);
            }
            if (true) {
                if (turn >= 2){
                    change.setVisible(false);
                }
                myBoard.getChildren().clear();
                oppBoard.getChildren().clear();
                time.setText(String.valueOf(timer));
                if (turn == playerNum) {
                    whichTurn.setText("Your Turn");
                } else {
                    whichTurn.setText("Wait...");
                }
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../cell.fxml"));
//                        fxmlLoader.setLocation(getClass().getResource("../cell.fxml"));
                        AnchorPane cell = null;
                        try {
                            cell = loader.load();
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../cell.fxml"));
//                        fxmlLoader.setLocation(getClass().getResource("../cell.fxml"));
                        AnchorPane cell = null;
                        try {
                            cell = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        cell.setMaxHeight(30);
                        cell.setMaxWidth(30);
                        cell.setLayoutX(20);
                        int finalI = i;
                        int finalJ = j;
                        cell.setOnMouseClicked(e -> {
                            try {
                                // Inja bayad type ro havaset bashe

                                click(2, finalI, finalJ);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        });
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
//    public int[][] showBehindShips(int[][] a,int[] b){
//        for (int i = 0; i < a.length; i++) {
//            for (int j = 0; j < a[i].length; j++) {
//                if
//            }
//        }

//    }
//    javafx.fxml.FXMLLoader$ValueElement processValue

    private synchronized void click(int type, int x, int y) throws IOException {
        if (turn == playerNum) {
            int[] xoy = {x, y};
            models.Event event = new Event(type, xoy,CurrentClient.getAuthenticationCode());
            serverCommunicator.send(event.toJson());
            String str = serverCommunicator.get();
            Gson gson = new Gson();
            Response response = gson.fromJson(str, Response.class);
            loppBoard = response.getOppMatrix();
            lmyBoard = response.getMyMatrix();
        }
    }

    public void change() throws IOException {
        serverCommunicator.send("CHANGE");
        String str = serverCommunicator.get();
        Gson gson = new Gson();
        Response response = gson.fromJson(str, Response.class);
        loppBoard = response.getOppMatrix();
        lmyBoard = response.getMyMatrix();
        myShips = response.getMyShips();
    }
    public void ready() throws IOException {
        change.setVisible(false);
        serverCommunicator.send("READY");
        serverCommunicator.send(CurrentClient.Username);
        String msg=serverCommunicator.get();
        res.setText(msg);
//        String str = serverCommunicator.get();
//        Gson gson = new Gson();
//        Response response = gson.fromJson(str, Response.class);
//        loppBoard = response.getOppMatrix();
//        lmyBoard = response.getMyMatrix();
//        myShips = response.getMyShips();
    }

}
