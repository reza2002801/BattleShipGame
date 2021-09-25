package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class ResultComponentController {
    @FXML
    private Label online;

    @FXML
    private Label name;

    @FXML
    private Label points;


    public void setName(String str){
        name.setText(str);
    }

    public void setOffline(boolean bool){
        if (bool){
            online.setText("Online");
        } else {
            online.setText("Offline");
        }
    }
}
