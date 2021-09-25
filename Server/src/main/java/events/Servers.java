package events;

import config.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class Servers {
//    LinkedList<Lobby> lobbies;

    public void run() throws IOException{

        try {
            ServerSocket serverSocket=serverSocket = new ServerSocket(9000);
//            Config config2=Config.getConfig("ports");
//            if(!config2.getProperty("port1").equals("")){
//                serverSocket = new ServerSocket(Integer.valueOf(config2.getProperty("port1")));
//            }else {
//                serverSocket = new ServerSocket(8000);
//            }

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("someone connected");
                EventHandler eventHandler = new EventHandler(socket);
                eventHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Servers(){
        System.out.println("---Server Is Running---");
//        lobbies = new LinkedList<>();

    }
}
