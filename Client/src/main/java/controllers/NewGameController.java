package controllers;

import javafx.event.ActionEvent;
import utils.ChangeScene;
import utils.CurrentClient;
import utils.ServerCommunicator;

import java.io.IOException;

public class NewGameController {
    ServerCommunicator serverCommunicator;
    public void initialize() throws IOException {
        this.serverCommunicator= CurrentClient.getServerCommunicator();
        serverCommunicator.send(CurrentClient.AuthenticationCode);
        serverCommunicator.send("WAIT");
    }
    public void go(ActionEvent event) throws Exception {
        System.out.println("huraaaa");
        serverCommunicator.send(CurrentClient.AuthenticationCode);
        serverCommunicator.send("GO");
        String str = serverCommunicator.get();
        System.out.println(str);
        if (str.equals("YES")) {
            new ChangeScene().change_scene(event,"../GameUserInterface.fxml");
        }
        System.out.println(str);

    }
}
