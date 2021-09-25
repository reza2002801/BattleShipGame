package models;

import com.google.gson.Gson;
import events.EventHandler;
import utils.Event;
//import server.game.GameState;
//import server.model.User;
//import server.shared.Event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandlerConector extends Thread{
    public WaitingRoom waitingRoom;
    public Socket socket;
    private User user;
    private final Character mainEvent;
    private final String reqEvent;
    private boolean isRunning;
    private final int playerType;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private volatile Event recentEvent;
    private final Game game;
    public ClientHandlerConector(Socket socket, int playerType, Game game, User user){
        isRunning = false;
        this.user = user;
        this.game = game;
        this.mainEvent = '2';
        this.reqEvent = "CHANGE";
        this.playerType = playerType;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }catch (Exception e){
            System.out.println("Something went wrong!");
        }
    }

    @Override
    public synchronized void start() {
        isRunning = true;
        super.start();
    }

    public void run(){

        while (isRunning) {
//            System.out.println("stil");
            String str = null;
            try {
                str = (dataInputStream.readUTF());
                System.out.println(str);
            } catch (IOException ignored) {
            }
            if (str != null ) {
                if (str.charAt(0) == (mainEvent)) {
                    try {


                        dataOutputStream.writeUTF(game.timeBack());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(str.equals("READY")){
                    try {
                        String fd=dataInputStream.readUTF();
                        if(game.player1.equals(fd)){
                            game.timer.player1ready=true;
                            if(game.timer.player2ready==false){
                                dataOutputStream.writeUTF("Waiting for"+game.player2);
                            }else {
                                dataOutputStream.writeUTF("Game Start");
                            }

                        }else if(game.player2.equals(fd)){
                            game.timer.player2ready=true;
                            if(game.timer.player1ready==false){
                                dataOutputStream.writeUTF("Waiting for"+game.player1);
                            }
                            else {
                                dataOutputStream.writeUTF("Game Start");
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                else if (str.equals(reqEvent)){
                    game.changeBoard(playerType);
                    game.timer.time+=10;
                    try {
                        dataOutputStream.writeUTF(game.sendBack(playerType));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(str.equals("Exit")){
                    System.out.println("hear");

                    isRunning=false;
                    break;
                }
                else {
                    recentEvent = (makeEvent(str));

                    try {
                        game.changeMatrix(playerType, recentEvent.getClickedPosition());
                        dataOutputStream.writeUTF(game.sendBack(playerType));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("every thing");

        waitingRoom.isFinished=true;


//        try {
//            new EventHandler(socket).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void sendToClient(String str) throws IOException {
        dataOutputStream.writeUTF(str);
    }

    private Event makeEvent(String string){
        Gson gson = new Gson();
        return gson.fromJson(string,Event.class);

    }

    public int getPlayerType() {
        return playerType;
    }
}
